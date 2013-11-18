/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: AsynLoadImageUtil.java 
 * @包名： com.wuxi.app.util 
 * @描述: 异步加载图片工具类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-26 上午11:09:52
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @类名： AsynLoadImageUtil
 * @描述： 异步加载图片工具类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-26 上午11:09:52
 * @修改时间：
 * @修改描述：
 */
public class AsynLoadImageUtil {

	private static final String TAG = "AsynLoadImageUtil";

	// 缓存下载过的图片的Map
	private static Map<String, SoftReference<Bitmap>> caches;

	// 任务队列
	private List<Task> taskQueue;

	private boolean isRunning = false;

	/**
	 * @方法： AsynLoadImageUtil
	 * @描述： 构造方法
	 */
	public AsynLoadImageUtil() {
		// 初始化变量
		caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<AsynLoadImageUtil.Task>();
		// 启动图片下载线程
		isRunning = true;
		new Thread(runnable).start();
	}

	/**
	 * 
	 * @param imageView
	 *            需要延迟加载图片的对象
	 * @param url
	 *            图片的URL地址
	 * @param resId
	 *            图片加载过程中显示的图片资源
	 */
	public void showImageAsyn(ImageView imageView, String url, int resId) {

		Bitmap bitmap = null;

		bitmap = loadImg(url);
		if (bitmap == null) {
			imageView.setTag(url);
			bitmap = loadImageAsyn(url, getImageCallback(imageView, resId));
			if (bitmap == null) {
				imageView.setImageResource(resId);
			} else {
				imageView.setImageBitmap(bitmap);
			}
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * @方法： loadImageAsyn
	 * @描述： 加载图片
	 * @param path
	 * @param callback
	 * @return
	 */
	public Bitmap loadImageAsyn(String path, ImageCallback callback) {
		// 判断缓存中是否已经存在该图片
		if (caches.containsKey(path)) {
			// 取出软引用
			SoftReference<Bitmap> rf = caches.get(path);
			// 通过软引用，获取图片
			Bitmap bitmap = rf.get();
			// 如果该图片已经被释放，则将该path对应的键从Map中移除掉
			if (bitmap == null) {
				caches.remove(path);
			} else {
				// 如果图片未被释放，直接返回该图片
				Log.i(TAG, "return image in cache" + path);
				return bitmap;
			}
		} else {
			// 如果缓存中不常在该图片，则创建图片下载任务
			Task task = new Task();
			task.path = path;
			task.callback = callback;
			Log.i(TAG, "new Task ," + path);
			if (!taskQueue.contains(task)) {
				taskQueue.add(task);
				// 唤醒任务下载队列
				synchronized (runnable) {
					runnable.notify();
				}
			}
		}

		// 缓存中没有图片则返回null
		return null;
	}

	/**
	 * 
	 * @param imageView
	 * @param resId
	 *            图片加载完成前显示的图片资源ID
	 * @return
	 */
	private ImageCallback getImageCallback(final ImageView imageView,
			final int resId) {
		return new ImageCallback() {

			@Override
			public void loadImage(String path, Bitmap bitmap) {
				if (path.equals(imageView.getTag().toString())) {
					System.out.println("图片的下载路径：" + path);
					imageView.setImageBitmap(bitmap);
					if (bitmap != null) {
						saveImageToFile(fileUrl, bitmap);
					}
				} else {
					imageView.setImageResource(resId);
				}
			}
		};
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 子线程中返回的下载完成的任务
			Task task = (Task) msg.obj;
			// 调用callback对象的loadImage方法，并将图片路径和图片回传给adapter
			task.callback.loadImage(task.path, task.bitmap);
		}

	};

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			while (isRunning) {
				// 当队列中还有未处理的任务时，执行下载任务
				while (taskQueue.size() > 0) {
					// 获取第一个任务，并将之从任务队列中删除
					Task task = taskQueue.remove(0);
					// 将下载的图片添加到缓存
					task.bitmap = PicUtil.getbitmap(task.path);
					caches.put(task.path,
							new SoftReference<Bitmap>(task.bitmap));
					if (handler != null) {
						// 创建消息对象，并将完成的任务添加到消息对象中
						Message msg = handler.obtainMessage();
						msg.obj = task;
						// 发送消息回主线程
						handler.sendMessage(msg);
					}
				}

				// 如果队列为空,则令线程等待
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};

	// 回调接口
	public interface ImageCallback {
		void loadImage(String path, Bitmap bitmap);
	}

	class Task {
		// 下载任务的下载路径
		String path;
		// 下载的图片
		Bitmap bitmap;
		// 回调对象
		ImageCallback callback;

		@Override
		public boolean equals(Object o) {
			Task task = (Task) o;
			return task.path.equals(path);
		}
	}

	/**
	 * 从本地加载图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap LoadImageFromLocal(String url) {
		try {
			System.out.println("加载本地图片");
			FileInputStream fis = new FileInputStream(url);
			BitmapFactory.Options options = new BitmapFactory.Options();
			// 越大压的越狠
			options.inSampleSize = 0;
			return BitmapFactory.decodeStream(fis, null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 把bitmap存储到file中
	 * 
	 * @param file
	 * @param bitmap
	 */
	public static void saveImageToFile(File file, Bitmap bitmap) {

		FileOutputStream fos = null;
		System.out.println("保存在本地,保存的路径：" + file);
		try {
			if (file.createNewFile()) {
				fos = new FileOutputStream(file);
				// 越小图片越模糊
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 加载图片 如果本地SD卡中存在，则直接从本地读取，否者，先从网上下下来，再转存到SD卡中
	 * 
	 * @param url
	 *            图片路径
	 * @return
	 */

	File fileUrl = null;

	public Bitmap loadImg(String url) {
		System.out.println("从SD卡上查找");
		String sDStateString = android.os.Environment.getExternalStorageState();// 判断SD卡状态
		// 有SD卡
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			// SD卡存在并可以进行写操作
			String path = Constants.APPFiles.APP_PATH;
			File dir = new File(path);
			if (!dir.exists()) {
				System.out.println(dir.mkdirs());
				dir.mkdirs();
			}
			path = Constants.APPFiles.CAHCE_FILE_CONTENT_PATH;
			File dir2 = new File(path);
			if (!dir2.exists()) {
				System.out.println(dir2.mkdirs());
				dir2.mkdirs();
			}
			String filename = url.substring(url.lastIndexOf("/") + 1,
					url.length());
			String imagepath = path + "/" + filename;
			fileUrl = new File(imagepath);
			if (fileUrl.exists()) {
				// SD卡中已存在该图片
				return LoadImageFromLocal(imagepath);
			} else {
				return null;
			}

		} else {// 无SD卡, 从网络上下载

			return null;
		}
	}

}

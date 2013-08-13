package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.io.File;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ApplyDeptService;
import com.wuxi.app.engine.ApplyGoverService;
import com.wuxi.app.engine.GoverSaoonFileService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.ApplyDept;
import com.wuxi.domain.ApplyGover;
import com.wuxi.exception.NetException;

public class GoverMsgApplyDownloadFragment extends BaseFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;

	private List<ApplyDept> depts;
	private List<ApplyGover> govers;
	private final static int LOAD_GOVER_SUCCESS=1;
	private final static int LOAD_DEPT_SUCCESS=2;
	private final static int LOAD_FAILED=0;

	private static final int FILE_DOWN_SUCCESS = 4;
	private static final int FILE_DOWN_ERROR = 5;

	private ListView content_listView;
	private ProgressBar progressBar;

	public static final int GOVER_TYPE=0;
	public static final int DEPT_TYPE=1;

	private int dataType=GOVER_TYPE;  //默认加载政府公开

	private ProgressDialog pd;

	public void setType(int type){
		this.dataType=type;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case LOAD_GOVER_SUCCESS:
				progressBar.setVisibility(ProgressBar.INVISIBLE);
				showGoverList();		
				break;
			case LOAD_DEPT_SUCCESS:
				progressBar.setVisibility(ProgressBar.INVISIBLE);
				showDeptList();		
				break;
			case FILE_DOWN_SUCCESS:
				Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
				break;
			case FILE_DOWN_ERROR:
				Toast.makeText(context, "下载出错，稍后再试", Toast.LENGTH_SHORT).show();
				break;
			case LOAD_FAILED:
				progressBar.setVisibility(ProgressBar.INVISIBLE);
				Toast.makeText(context, "提交失败！", 2000).show();
				break;
			}
		};
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.govermsg_deptapply_layout, null);
		mInflater = inflater;
		context = getActivity();

		initView();
		return view;
	}

	public void initView(){
		content_listView=(ListView)view.findViewById(R.id.govermsg_deptapply_listview);
		progressBar=(ProgressBar)view.findViewById(R.id.govermsg_deptapply_progressbar);

		progressBar.setVisibility(ProgressBar.VISIBLE);
		if(dataType==GOVER_TYPE){
			loadGoverData();
		}
		else if(dataType==DEPT_TYPE){
			loadDeptData();
		}

		pd = new ProgressDialog(context);

		pd.setMessage("正在下载表格....");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

	}


	public void loadGoverData(){
		new Thread(new Runnable() {

			@Override
			public void run() {

				ApplyGoverService applyGoverService = new ApplyGoverService(context);
				try {
					govers = applyGoverService.getGovers(Constants.Urls.APPLYGOVER_URL);
					if (null != govers) {
						handler.sendEmptyMessage(LOAD_GOVER_SUCCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(LOAD_FAILED);
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LOAD_FAILED);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				).start();
	}

	public void loadDeptData(){
		new Thread(new Runnable() {

			@Override
			public void run() {

				ApplyDeptService applyDeptService = new ApplyDeptService(context);
				try {
					depts = applyDeptService.getDepts(Constants.Urls.APPLYDEPT_URL);
					if (null != depts) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来
						handler.sendEmptyMessage(LOAD_DEPT_SUCCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(LOAD_FAILED);
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LOAD_FAILED);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				).start();
	}

	public void showGoverList(){
		BaseSlideFragment baseSlideFragment = this.baseSlideFragment;
		if(govers!=null)
			content_listView.setAdapter(new ApplyGoverAdapter(baseSlideFragment));
	}

	public void showDeptList(){

		BaseSlideFragment baseSlideFragment =this.baseSlideFragment;
		if(depts!=null)	
			content_listView.setAdapter(new ApplyDeptAdapter(baseSlideFragment));
	}

	public class ApplyGoverAdapter extends BaseAdapter implements OnClickListener{

		BaseSlideFragment baseSlideFragment;

		public ApplyGoverAdapter(BaseSlideFragment baseSlideFragment){
			this.baseSlideFragment=baseSlideFragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return govers.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return govers.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			public TextView title_text;
			public ImageButton download_imgbtn;
			public ImageButton guide_imgbtn;
			public ImageButton apply_imgbtn;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.govermsg_deptapply_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.govermsg_deptapply_item_title);
				viewHolder.download_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_download);
				viewHolder.guide_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_guide);
				viewHolder.apply_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_apply);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.title_text.setText(govers.get(position).getDepName());
			viewHolder.apply_imgbtn.setOnClickListener(ApplyGoverAdapter.this);
			viewHolder.download_imgbtn.setOnClickListener(ApplyGoverAdapter.this);
			return convertView;
		}

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.govermsg_deptapply_item_download:
				downloadTable();
				break;
			case R.id.govermsg_deptapply_item_guide:
				break;
			case R.id.govermsg_deptapply_item_apply:
				break;
			}
		}
	}

	public class ApplyDeptAdapter extends BaseAdapter implements OnClickListener{

		BaseSlideFragment baseSlideFragment;
		ApplyDept applyDept=null;

		public ApplyDeptAdapter(BaseSlideFragment baseSlideFragment){
			this.baseSlideFragment=baseSlideFragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return depts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return depts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			public TextView title_text;
			public ImageButton download_imgbtn;
			public ImageButton guide_imgbtn;
			public ImageButton apply_imgbtn;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.govermsg_deptapply_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.govermsg_deptapply_item_title);
				viewHolder.download_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_download);
				viewHolder.guide_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_guide);
				viewHolder.apply_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_apply);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			applyDept=depts.get(position);
			viewHolder.title_text.setText(depts.get(position).getDepName());
			viewHolder.apply_imgbtn.setOnClickListener(ApplyDeptAdapter.this);
			viewHolder.download_imgbtn.setOnClickListener(ApplyDeptAdapter.this);
			return convertView;
		}

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.govermsg_deptapply_item_download:
				downloadTable();
				break;
			case R.id.govermsg_deptapply_item_guide:
				break;
			case R.id.govermsg_deptapply_item_apply:
				Bundle bundle=new Bundle();
				if(applyDept!=null){
					bundle.putSerializable("applyDept", applyDept);
					baseSlideFragment.slideLinstener.replaceFragment(null, position,
							Constants.FragmentName.GOVERMSG_APPLYTABLE_FRAGMENT, bundle);
				}
				break;
			}
		}
	}

	/**
	 * 下载表格
	 * */
	public void downloadTable(){

		showDownloadComfirmDialog(Constants.Urls.GOVERMSG_TABLE_DOWNLOAD_URL);
	}

	private void showDownloadComfirmDialog(
			final String url) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.logo);
		builder.setTitle("下载提示");
		builder.setMessage("确认要下载该" + Constants.APPFiles.GOVERMSG_APPLYOPEN_TABLENAME
				+ "文件吗?\n 文件存放地址:" + Constants.APPFiles.DOWNLOAF_FILE_PATH
				+ Constants.APPFiles.GOVERMSG_APPLYOPEN_TABLENAME);
		builder.setCancelable(false);
		File file = new File(Constants.APPFiles.DOWNLOAF_FILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					DownLoadThreadTask dowTask = new DownLoadThreadTask(url,
							Constants.APPFiles.DOWNLOAF_FILE_PATH
							+Constants.APPFiles.GOVERMSG_APPLYOPEN_TABLENAME);

					new Thread(dowTask).start();
					pd.show();

				} else {
					Toast.makeText(context, "SDK不存在", 1).show();

				}

			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();

	}

	private class DownLoadThreadTask implements Runnable {

		private String url;
		private String filePath;

		public DownLoadThreadTask(String url, String filePath) {

			this.url = url;
			this.filePath = filePath;
		}

		@Override
		public void run() {

			try {

				GoverSaoonFileService goverSaoonFileService = new GoverSaoonFileService(
						context);
				File file = goverSaoonFileService.dowloadGoverMsgTable(url,
						filePath, pd);
				if (file != null) {
					handler.sendEmptyMessage(FILE_DOWN_SUCCESS);
				}
				pd.dismiss();
			} catch (Exception e) {

				e.printStackTrace();

				pd.dismiss();

				handler.sendEmptyMessage(FILE_DOWN_ERROR);
			}

		}

	}

}

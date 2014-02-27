/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PicUtil.java 
 * @包名： com.wuxi.app.util 
 * @描述: 图片工具类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-26 上午11:16:30
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @类名： PicUtil
 * @描述： 图片工具类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-26 上午11:16:30
 * @修改时间： 
 * @修改描述： 
 */
public class PicUtil {
	
	private static final String TAG = "PicUtil";  
	
	/** 
     * 根据一个网络连接(String)获取bitmap图像 
     *  
     * @param imageUri 
     * @return 
     * @throws MalformedURLException 
     */  
    public static Bitmap getbitmap(String imageUri) {  
        // 显示网络上的图片  
        Bitmap bitmap = null;  
        try {  
            URL myFileUrl = new URL(imageUri);  
            HttpURLConnection conn = (HttpURLConnection) myFileUrl  
                    .openConnection();  
            conn.setDoInput(true);  
            conn.connect();  
            InputStream is = conn.getInputStream();  
            bitmap = BitmapFactory.decodeStream(is);  
            is.close();  
  
            Log.i(TAG, "image download finished." + imageUri);  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        }  
        return bitmap;  
    }  

}

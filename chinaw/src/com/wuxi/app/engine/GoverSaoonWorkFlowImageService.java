package com.wuxi.app.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wuxi.app.util.Constants;

/**
 * 
 * @author wanglu 泰得利通 政务大厅流程图片地址获取
 * 
 */
public class GoverSaoonWorkFlowImageService extends Service {

	public GoverSaoonWorkFlowImageService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 返回流程图片URL
	 * 
	 * @param id
	 * @return
	 * @throws JSONException
	 */
	public String getWorkFlowImag(String id) throws JSONException {

		String url = Constants.Urls.GETLIUC_IMG_URL.replace("{id}", id);

		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);

			String path = jsonObject.getString("result");
			if (!path.equals("null")) {

				int lastIndex = path.lastIndexOf("/");
				try {
					path = path.substring(0, lastIndex + 1)
							+ URLEncoder.encode(path.substring(lastIndex + 1),
									"utf-8");
					return Constants.Urls.ROOT_URL + path;
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}

			
			}

		}
		return null;

	}

	/**
	 * 
	 * wanglu 泰得利通 返回图片
	 * 
	 * @param id
	 * @return
	 * @throws JSONException
	 */
	public Bitmap getBitMap(String id) throws JSONException {
		String url = getWorkFlowImag(id);
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}

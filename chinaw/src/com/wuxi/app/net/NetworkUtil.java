package com.wuxi.app.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	private NetworkUtil() {
	}

	public static NetworkUtil networkUtil;

	public synchronized static NetworkUtil getInstance() {

		if (networkUtil == null) {
			networkUtil = new NetworkUtil();
		}
		return networkUtil;
	}

	/** ————————————检查网络连接———————————— **/
	public boolean isConnet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected())
			return true;
		else
			return false;
	}

	public boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI")) {
			return true;
		}
		return false;
	}

	/** —————————获取url返回状态码——————————— **/
	public boolean UrlStatusCode(String url) {
		HttpResponse httpResponse = null;
		try {
			httpResponse = HttpUtils.getInstance().executeGet_response(url,
					5000);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String str = String.valueOf(statusCode);
			if (str.startsWith("4") || str.startsWith("5"))
				return false;
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public boolean checkInternet(Context context) {
		if (!isConnet(context)) {

			return false;
		} else
			return true;
	}

	/** ——————————————获取网络数据—————————————— **/
	/**
	 * 从网上获取内容get方式
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String getStringFromUrl(String url) {
		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		HttpEntity entity = null;
		String Entity = null;
		try {
			response = client.execute(get);
			entity = response.getEntity();
			Entity = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Entity;
	}

	public String get(String http_url, int Connect_timeout, int Read_timeout) {

		HttpURLConnection conn = null;
		InputStream inputStream = null;
		ByteArrayOutputStream out = null;
		String content = null;
		try {
			URL url = new URL(http_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(Connect_timeout);
			conn.setReadTimeout(Read_timeout);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/html");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			inputStream = conn.getInputStream();
			byte[] buffer = null;
			if (conn.getResponseCode() == 200) {
				buffer = new byte[1024];
				out = new ByteArrayOutputStream();
				int len;
				while ((len = inputStream.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				buffer = out.toByteArray();
				content = new String(buffer, "UTF-8");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (conn != null)
					conn.disconnect();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
}

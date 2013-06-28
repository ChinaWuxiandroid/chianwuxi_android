package com.wuxi.app.net;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: HttpUtils，该类是进行网络交互的，进行网络下载数据，上传网络数据 可以get提交，可以post提交
 * @author 方庆银
 * 
 */
public final class HttpUtils {

	public static HttpUtils instance = null;

	public synchronized static HttpUtils getInstance() {

		if (instance == null)
			instance = new HttpUtils();
		return instance;
	}

	/** —————————get提交——————————— **/
	public InputStream executeGet(String url, int timeout) {
		HttpResponse httpResponse = executeGet_response(url, timeout);
		InputStream result = null;
		try {
			result = httpResponse.getEntity().getContent();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/** —————————get提交获取String——————————— **/
	public String executeGetToString(String url, int timeout) {
		HttpResponse httpResponse = executeGet_response(url, timeout);
		HttpEntity entity = null;
		String result = null;
		try {
			if(httpResponse!=null){
				entity = httpResponse.getEntity();
				if(entity!=null){
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
			
		
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/** —————————post提交——————————— **/
	public InputStream executePost(String url,List<NameValuePair> nameValuesPairs, int timeout, String charSet) {
		HttpPost httpPost = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();
		InputStream result = null;
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			httpPost.setParams(httpParams);
			setHeader(httpPost, url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuesPairs,
					charSet));
			result = httpClient.execute(httpPost).getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/** —————————post提交 获取字符串——————————— **/
	public String executePostTostring(String url,List<NameValuePair> nameValuesPairs, int timeout) {
		HttpPost httpPost = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();
		InputStream result = null;
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			httpPost.setParams(httpParams);
			setHeader(httpPost, url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuesPairs,HTTP.UTF_8));
			result = httpClient.execute(httpPost).getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readLine(result);
	}

	// get submit
	public HttpResponse executeGet_response(String url, int timeout) {
		HttpResponse httpResponse = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			httpGet.setParams(httpParams);
			setHeader(httpGet, url);
			httpResponse = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpResponse;
	}

	// provider readLine
	public String readLine(InputStream input) {
		String readResult = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		StringBuffer cacheBuffer = new StringBuffer();
		try {
			if (input == null) {
				throw new RuntimeException("InputStream is null");
			}
			inputStreamReader = new InputStreamReader(input);
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((readResult = bufferedReader.readLine()) != null) {
				cacheBuffer.append(readResult + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (bufferedReader != null) {
					bufferedReader.close();
					bufferedReader = null;
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
					inputStreamReader = null;
				}
				if (input != null) {
					input.close();
					input = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return cacheBuffer.toString();
	}

	// Setting request header info
	private void setHeader(HttpRequest httpRequest, String url) {

		httpRequest
				.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");
		httpRequest
				.addHeader(
						"Accept",
						"text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		httpRequest.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpRequest.addHeader("Keep-Alive", "300");
		httpRequest.addHeader("Referer", url);
		httpRequest.addHeader("Connection", "keep-alive");
	}

	private Object readResolve() {
		return instance;
	}

	public String postData(String posturl, List<NameValuePair> nameValuePairs) {
		String strResult = null;
		HttpResponse response = null;
		try {
			HttpParams parms = new BasicHttpParams();
			parms.setParameter("charset", HTTP.UTF_8);
			HttpClient httpclient = new DefaultHttpClient(parms);
			HttpPost httppost = new HttpPost(posturl);
			httppost.addHeader("charset", HTTP.UTF_8);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
			httppost.setEntity(ent);
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				strResult = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strResult;
	}

}

package com.wuxi.app.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 
 * @author wanglu 泰得利通
 * 政务大厅 附近类
 */
public class GoverSaoonFileService extends Service {

	public GoverSaoonFileService(Context context) {
		super(context);
		
	}

	
	/**
	 * 
	 *wanglu 泰得利通 
	 *表格下载
	 * @param path
	 * @param FilePath
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public  File dowloadTable(String id, String FilePath,ProgressDialog pd)
			throws Exception {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String path=Constants.Urls.GOVER_FILE_DOWN_URL.replace("{id}", id);
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(5000);
		httpURLConnection.setRequestMethod("GET");
		if (httpURLConnection.getResponseCode() == 200) {
			int total=httpURLConnection.getContentLength();
			pd.setMax(total);
			File file = new File(FilePath);
			FileOutputStream fos = new FileOutputStream(file);
			InputStream is = httpURLConnection.getInputStream();
			int currentLen=0;
			int len = 0;
			byte buffer[] = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				currentLen+=len;
				pd.setProgress(currentLen);
				Thread.sleep(50);
			}

			fos.flush();
			fos.close();
			is.close();

			return file;

		}

		return null;
	}
}

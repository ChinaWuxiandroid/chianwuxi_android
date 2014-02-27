package com.wuxi.app.engine;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.GoverTableDownLoad;
import com.wuxi.domain.GoverTableDownLoadWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**'
 * 
 * @author wanglu 泰得利通
 * 政务大厅表格下载 业务
 *
 */
public class GoverTableDownLoadService extends Service {

	public GoverTableDownLoadService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 表格下载分页获取
	 * 
	 * @param start
	 *            开始记录
	 * @param end
	 *            结束记录
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 * @throws UnsupportedEncodingException 
	 */
	public GoverTableDownLoadWrapper getTableDownLoadsPage(String deptid,String fileName,
			int start, int end) throws NetException, JSONException,
			NODataException, UnsupportedEncodingException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.GETTABLE_DOWNLOADS_URL
				.replace("{deptid}", deptid).replace("{start}", start + "")
				.replace("{end}", end + "");
		if(fileName!=null&&!fileName.equals("")){
			url=url+"&"+URLEncoder.encode(fileName, "utf-8");
		}
		String resultStr = httpUtils.executeGetToString(url, 5000);
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");
			GoverTableDownLoadWrapper goverTableDownLoadWrapper = new GoverTableDownLoadWrapper();
			goverTableDownLoadWrapper.setEnd(jresult.getInt("end"));
			goverTableDownLoadWrapper.setStart(jresult.getInt("start"));
			goverTableDownLoadWrapper.setNext(jresult.getBoolean("next"));
			goverTableDownLoadWrapper.setPrevious(jresult
					.getBoolean("previous"));
			goverTableDownLoadWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));
			Object o = jresult.get("data");
			if (!o.toString().equals("[]") && !o.equals("null")) {
				JSONArray jData = (JSONArray) o;
				try {
					goverTableDownLoadWrapper
							.setGoverTableDownLoads(JAsonPaserUtil
									.getListByJassory(GoverTableDownLoad.class,
											jData));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			return goverTableDownLoadWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}
}

package com.wuxi.app.engine;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.MailBoxParameterItem;
import com.wuxi.domain.WorkSuggestionBoxWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;


/**
 * 工作意见箱 布局 业务类
 * @author 杨宸 智佳
 * */

public class WorkSuggestionService extends Service{

	public WorkSuggestionService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * 杨宸 智佳 
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	
	public WorkSuggestionBoxWrapper getBoxWrapper(String url) throws NODataException, NetException, JSONException{
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			WorkSuggestionBoxWrapper boxWrapper = new WorkSuggestionBoxWrapper();
			boxWrapper.setId(jresult.getString("id"));
			boxWrapper.setFormId(jresult.getString("formId"));
			boxWrapper.setFormTitle(jresult.getString("formTitle"));
			boxWrapper.setSendType(jresult.getInt("sendType"));
			boxWrapper.setLinkDept(jresult.getInt("linkDept"));
			Object o = jresult.get("parameters");
			if (!o.toString().equals("[]") && !o.equals("null")) {
				JSONArray jData = (JSONArray) o;
				try {
					boxWrapper.setParameters(JAsonPaserUtil
							.getListByJassory(MailBoxParameterItem.class, jData));
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
				}
			}

			return boxWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}
	
	/**
	 * @author 杨宸 智佳
	 * 提交工作意见箱  的   自定义列表信息
	 * @throws NetException 
	 * @throws JSONException 
	 * @throws NODataException 
	 * */
	public boolean submitMailBox(String access_token,WorkSuggestionBoxWrapper boxWrapper) throws NetException, JSONException, NODataException{
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET); // 检查网络
		}

		String url = Constants.Urls.SUBMIT_SELFFORM_URL+"?access_token="+access_token;	
		for(MailBoxParameterItem item:boxWrapper.getParameters()){
			url=url+"&"+item.getKeyName()+"="+item.getValueList();
		}
		url=url+"&selfFormId="+boxWrapper.getId();
		
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
//			JSONObject jresult = jsonObject.getJSONObject("result");		
//			System.out.println("jresult:"+jresult);
			System.out.println("提交结果："+jsonObject.getBoolean("success"));
			return  jsonObject.getBoolean("success");
		} else {
			return false;
		}
		
	}
	
	
}

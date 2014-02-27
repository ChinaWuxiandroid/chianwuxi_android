package com.wuxi.app.engine;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.JAsonPaserUtil;
import com.wuxi.domain.GoverApplyOnlie;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 政务大厅 在线办理业务类
 * 
 */
public class GoverApplyOnlieService extends Service {

	public GoverApplyOnlieService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 表单提交 access_token true String OAuth Token id true String
	 * 申请主键id UUID 手机端生成，因为需要先上传附件，需要做关联 sqzlx true Int 申请者类型 sqzmc true String
	 * 申请者名称 dwdm true String 单位代码 dz true String 地址 lxr true String 联系人 dh true
	 * String 电话 sj true String 手机号码 zjmc true String 证件名称 zjhm true String 证件号码
	 * sqsy true String 申请事由 itemid true String 办件id itemtype true String 办件类型
	 * 
	 * @throws NetException
	 * @throws JSONException
	 */
	public GoverApplyOnlie commitOnlieForm(Map<String, String> params)
			throws NetException, JSONException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> paramSet : params.entrySet()) {

			try {
				sb.append(paramSet.getKey())
						.append("=")
						.append(URLEncoder.encode(paramSet.getValue(), "utf-8"))
						.append("&");
				

			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}

		}

		sb.deleteCharAt(sb.length() - 1);// 删除最后一个字符
		String url = Constants.Urls.GOVER_APPLY_ONLINE_URL + "?"
				+ sb.toString();

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jreslut = new JSONObject(resultStr);
			JSONObject jb = jreslut.getJSONObject("result");
			JSONObject jbaseInfo = jb.getJSONObject("baseInfo");
			GoverApplyOnlie goverApplyOnlie;
			try {
				goverApplyOnlie = JAsonPaserUtil.getBeanByJASSON(
						GoverApplyOnlie.class, jbaseInfo);
				return goverApplyOnlie;
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

		return null;
	}

}

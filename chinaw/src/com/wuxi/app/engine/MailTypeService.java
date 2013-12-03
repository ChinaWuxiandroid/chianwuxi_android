/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MailTypeService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 信件查询 信件类型业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-27 下午5:22:05
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.MailTypeWrapper;
import com.wuxi.domain.MailTypeWrapper.MailType;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： MailTypeService
 * @描述： 信件查询 信件类型业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-8-27 下午5:22:05
 * @修改时间：
 * @修改描述：
 * 
 */
public class MailTypeService extends Service {

	/**
	 * @方法： MailTypeService
	 * @描述： 构造方法
	 * @param context
	 */
	public MailTypeService(Context context) {
		super(context);
	}

	/**
	 * @方法： getMailTypeWrapper
	 * @描述： 解析数据集
	 * @return MailTypeWrapper
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public MailTypeWrapper getMailTypeWrapper() throws NetException,
			JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.QUERY_MAIL_TYPE_URL;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);

			JSONArray jresult = jsonObject.getJSONArray("result");

			MailTypeWrapper typeWrapper = new MailTypeWrapper();

			if (jresult != null) {
				typeWrapper.setMailTypes(getMailTypes(jresult));
			}

			return typeWrapper;
		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * @方法： getMailTypes
	 * @描述： 解析单个数据
	 * @param array
	 * @return List<MailType>
	 * @throws JSONException
	 */
	private List<MailType> getMailTypes(JSONArray array) throws JSONException {
		if (array != null) {
			List<MailType> mailTypes = new ArrayList<MailType>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				MailTypeWrapper typeWrapper = new MailTypeWrapper();
				MailType mailType = typeWrapper.new MailType();

				mailType.setIsnull(jb.getString("null"));
				mailType.setTypename(jb.getString("typename"));
				mailType.setTypeid(jb.getString("typeid"));

				mailTypes.add(mailType);
			}

			return mailTypes;
		}

		return null;
	}

}

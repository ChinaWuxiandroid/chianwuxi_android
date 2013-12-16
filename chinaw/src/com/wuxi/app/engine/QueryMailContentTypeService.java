/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: QueryMailContentTypeService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 信件查询内容类型业务类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-27 下午4:14:30
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
import com.wuxi.domain.QueryMailContentTypeWrapper;
import com.wuxi.domain.QueryMailContentTypeWrapper.QueryMailContentType;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： QueryMailContentTypeService
 * @描述： 信件查询内容类型业务类 
 * @作者： 罗森
 * @创建时间： 2013 2013-8-27 下午4:14:30
 * @修改时间：
 * @修改描述：
 * 
 */
public class QueryMailContentTypeService extends Service {

	/**
	 * @方法： QueryMailContentTypeService
	 * @描述： 构造方法
	 * @param context
	 */
	public QueryMailContentTypeService(Context context) {
		super(context);
	}

	/**
	 * @方法： getQueryMailContentType
	 * @描述： 解析返回的数据集
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public QueryMailContentTypeWrapper getQueryMailContentType() throws NetException,
			JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		
		String url = Constants.Urls.QUERY_MAIL_CONTENT_TYPE_URL;
		
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);
		
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);

			JSONArray jresult = jsonObject.getJSONArray("result");
			
			QueryMailContentTypeWrapper contentType = new QueryMailContentTypeWrapper();
			
			if (jresult != null) {
				contentType.setContentTypes(getContentTypes(jresult));
			}
			
			return contentType;
		}else{
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * @方法： getContentTypes
	 * @描述： 解析单条数据
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<QueryMailContentType> getContentTypes(JSONArray array)
			throws JSONException {
		if (array != null) {
			List<QueryMailContentType> types = new ArrayList<QueryMailContentType>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);
				
				QueryMailContentTypeWrapper contentType = new QueryMailContentTypeWrapper();
				QueryMailContentType type = contentType.new QueryMailContentType();

				type.setIsnull(jb.getString("null"));
				type.setTypename(jb.getString("typename"));
				type.setTypeid(jb.getString("typeid"));
				
				types.add(type);

			}
			
			return types;
		}
		
		return null;
	}

}

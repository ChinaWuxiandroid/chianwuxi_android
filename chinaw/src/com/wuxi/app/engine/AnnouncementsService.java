package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.domain.Content;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 推荐公告
 * 
 */
public class AnnouncementsService extends Service {

	public AnnouncementsService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取公告列表
	 * 
	 * @return
	 * @throws JSONException
	 * @throws NetException
	 * @throws NODataException
	 */
	public List<Content> getAnnouncements() throws JSONException, NetException,
			NODataException {

		ContentService contentService=new ContentService(context);
		
		return contentService.getContentsByUrl(Constants.Urls.ANNOUNCENTS_URL);

	}

}

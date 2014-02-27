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
 * @author wanglu 泰得利通 推荐新闻
 * 
 */
public class ImportNewsService extends Service {

	public ImportNewsService(Context context) {
		super(context);
	}

	/**
	 * 
	 * wanglu 泰得利通 获取推荐新闻
	 * 
	 * @return
	 * @throws JSONException
	 * @throws NetException
	 * @throws NODataException
	 */
	public List<Content> getImportNews() throws JSONException, NetException,
			NODataException {

		ContentService contentService = new ContentService(context);

		return contentService.getContentsByUrl(Constants.Urls.IMPORT_NEWS_URL);

	}

}

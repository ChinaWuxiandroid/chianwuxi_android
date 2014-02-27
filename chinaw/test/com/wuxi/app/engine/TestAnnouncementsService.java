package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Content;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;
/**
 * 
 * @author wanglu 泰得利通
 *
 */
public class TestAnnouncementsService extends AndroidTestCase {

	
	private static final String TAG = "TestAnnouncementsService";

	public void tetGetContents() throws JSONException, NetException, NODataException{
		AnnouncementsService announcementsService=new AnnouncementsService(getContext());
		
		
		List<Content> contents=announcementsService.getAnnouncements();
		
		
		if(contents!=null){
			for(Content content : contents){
				LogUtil.i(TAG, content.getTitle());
				LogUtil.i(TAG, "===============================");
				
			}
			
		}
		
	}
	
	
}

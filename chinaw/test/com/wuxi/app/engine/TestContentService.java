package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

public class TestContentService extends AndroidTestCase{

	private static final String TAG = "TestContentService";

	public  void testGetPageContentsById() throws NetException, JSONException, NODataException{
		
		ContentService contentService=new ContentService(getContext());
		ContentWrapper contentWrapper=contentService.getPageContentsById("aede3e2e-72a4-45dc-b8d4-021b14442da4", 0, 10);
		List<Content> contents=contentWrapper.getContents();
		
		if(contents!=null){
			for(Content content : contents){
				LogUtil.i(TAG, content.getTitle());
				
			}
			
			
		}
		
	}
	
	
	public void testGetContentsById() throws NetException, JSONException, NODataException{
		
		ContentService contentService=new ContentService(getContext());
		List<Content> contents=contentService.getContentsById("64b3b95a-33e3-4ac4-a2ac-a7ff57fdc401");
		
		if(contents!=null){
			for(Content content : contents){
				LogUtil.i(TAG, content.getTitle());
				
			}
		}
		
		
	}
}

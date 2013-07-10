package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Content;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

public class TestImportNewsService extends AndroidTestCase {

	private static final String TAG = "TestImportNewsService";

	public void tetGetContents() throws JSONException, NetException, NODataException{
		ImportNewsService importNewsService=new ImportNewsService(getContext());
		
		
		List<Content> contents=importNewsService.getImportNews();
		
		
		if(contents!=null){
			for(Content content : contents){
				LogUtil.i(TAG, content.getTitle());
				LogUtil.i(TAG, "===============================");
				
			}
			
		}
		
	}
}

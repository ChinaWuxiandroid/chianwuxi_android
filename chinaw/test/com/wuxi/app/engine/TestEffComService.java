package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.EfficaComplain;
import com.wuxi.domain.EfficaComplainWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

public class TestEffComService extends AndroidTestCase {

	
	private static final String TAG = "TestEffComService";

	public void testPage() throws NetException, JSONException, ResultException{
		
		EfficaComplainService efficaComplainService=new EfficaComplainService(getContext());
		
		EfficaComplainWrapper efficaComplainWrapper=efficaComplainService.getPageEfficaComplains(0, 5);
		
		
		List<EfficaComplain> efficaComplains=efficaComplainWrapper.getEfficaComplains();
		
		
		if(efficaComplains!=null){
			for(EfficaComplain eff :efficaComplains){
				LogUtil.i(TAG, eff.getTitle());
			}
			
		}
		
	}

}

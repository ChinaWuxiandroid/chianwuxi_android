package com.wuxi.app.engine;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.domain.GoverItemCount;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

public class TestGoverItemCountService extends AndroidTestCase {

	public void test() throws NetException, JSONException, NODataException{
		GoverItemCountService goverItemCountService=new GoverItemCountService(getContext());
		
		GoverItemCount goverItemCount=goverItemCountService.getGoverItemCount();
		
		goverItemCount.getBybj();
		
	}
	
	
	

}

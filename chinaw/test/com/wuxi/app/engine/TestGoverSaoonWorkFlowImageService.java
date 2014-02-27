package com.wuxi.app.engine;

import org.json.JSONException;

import android.test.AndroidTestCase;

public class TestGoverSaoonWorkFlowImageService extends AndroidTestCase{

	public void test(){
		GoverSaoonWorkFlowImageService t=new GoverSaoonWorkFlowImageService(getContext());
		try {
			String url=t.getWorkFlowImag("4028818a27b6ee920127b70d240e02ca");
			
			System.out.println(url);
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
}

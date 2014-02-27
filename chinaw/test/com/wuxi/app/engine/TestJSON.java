package com.wuxi.app.engine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

public class TestJSON extends AndroidTestCase {

	public void test(){
		
		String str="{\"reslut\":[{\"402881de3f758726013f758734c40000\":{\"id\":\"402881de3f758726013f758734c40000\",\"name\":\"??????\"}}]}";
		
		try {
			JSONObject json=new JSONObject(str);
			JSONArray jsonArray=json.getJSONArray("reslut");
			//jsonArray.
			
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		
		
	}
}

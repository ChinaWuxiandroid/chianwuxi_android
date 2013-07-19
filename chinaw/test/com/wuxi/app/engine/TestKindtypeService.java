package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Kindtype;
import com.wuxi.exception.NetException;

public class TestKindtypeService extends AndroidTestCase {

	private static final String TAG = "TestKindtypeService";

	public void testGetKindType() throws NetException, JSONException{
		
		KindTypeService kindTypeService=new KindTypeService(getContext());
		//List<Kindtype>  kindTypes=kindTypeService.getKindtypesByType("01");
		List<Kindtype>  kindTypes=kindTypeService.getKindtypesByType2("01");
		
		if(kindTypes!=null){
			for(Kindtype kindtype : kindTypes){
				LogUtil.i(TAG, kindtype.getKindName()+" "+ kindtype.getSubKindName());
			}
		}
		
	}
}

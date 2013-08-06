package com.wuxi.app.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;

import com.wuxi.domain.GoverApplyOnlie;
import com.wuxi.exception.NetException;

import android.test.AndroidTestCase;

public class TestGoverApplyOnlieService extends AndroidTestCase {

	/**
	 * id=4e51005e-83ee-4134-9df9-06e8450a8fdc&sqzlx=0&sqzmc=test&dwdm=0001&dz=地址&lxr=联系人&dh=2134&sj=1231242&zjmc=证件名称&zjhm=123142&sqsy=申请事由&itemid=4028818a3a4903cc013a4de8894e027d&itemtype=XK&access_token=0a857a943d6d4af58cc5935bc224eed1
	 *wanglu 泰得利通
	 * @throws JSONException 
	 * @throws NetException 
	 */
	public void test() throws NetException, JSONException{
		Map<String, String> params=new HashMap<String, String>();
		
		params.put("id", UUID.randomUUID().toString());
		params.put("sqzlx", "0");//申请者类型
		params.put("sqzmc", "test");//名称
		params.put("dwdm", "0001");//单位代码
		params.put("dz", "地址");
		params.put("lxr", "联系人");
		params.put("dh", "1234");
		params.put("sj", "1234");
		params.put("zjmc", "证件名称");
		params.put("zjhm", "123142");
		params.put("sqsy", "申请事由");
		params.put("itemid", "4028818a3a4903cc013a4de8894e027d");
		params.put("itemtype", "XK");
		params.put("access_token", "0a857a943d6d4af58cc5935bc224eed1");
		
		
		
		GoverApplyOnlieService goverApplyOnlieService=new GoverApplyOnlieService(getContext());
		GoverApplyOnlie goverApplyOnlie=goverApplyOnlieService.commitOnlieForm(params);
		goverApplyOnlie.getAddress();
	}
	
}

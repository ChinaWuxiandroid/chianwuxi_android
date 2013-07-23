package com.wuxi.app.engine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.GoverSaoonItemDetail;
import com.wuxi.domain.GoverSaoonItemWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**'
 * 
 * @author wanglu 泰得利通
 * 政务大厅办件测试
 *
 */
public class TestGoverSaItemService extends AndroidTestCase {

	private static final String TAG = "TestGoverSaItemService";

	public void test() throws JSONException, NetException, NODataException{
		GoverSaoonItemService goverSaoonItemService=new GoverSaoonItemService(getContext());
		
		GoverSaoonItemWrapper wapper=goverSaoonItemService.getGoverSaoonItemsByDeptId("018181932195157f01219a80e6960134", 0, 3);
		
		LogUtil.i(TAG, wapper.getTotalRowsAmount()+"");
	}
	
	
	public void test1() throws JSONException, NetException, NODataException{
		GoverSaoonItemService goverSaoonItemService=new GoverSaoonItemService(getContext());
		
		GoverSaoonItemWrapper wapper=goverSaoonItemService.getGoverSaoonItemsByKindType("01", "1", 0, 3);
		
		LogUtil.i(TAG, wapper.getTotalRowsAmount()+"");
	}
	
	public void test2() throws JSONException, NetException, NODataException{
		GoverSaoonItemService goverSaoonItemService=new GoverSaoonItemService(getContext());
		Map<String, String> params=new HashMap<String, String>();
		params.put("qltype", "XK");
		params.put("start", "0");
		params.put("end", "10");
		
		GoverSaoonItemWrapper wapper=goverSaoonItemService.getGoverSaoonItemsByParas(params);
		
		LogUtil.i(TAG, wapper.getTotalRowsAmount()+"");
	}
	
	
	public void test3() throws JSONException, NetException, NODataException{
		GoverSaoonItemService goverSaoonItemService=new GoverSaoonItemService(getContext());
	
		GoverSaoonItemDetail go=goverSaoonItemService.getGoverItemDetailById("XK", "4028818a28c483370128cd1caf8f24db");
		
		
		LogUtil.i(TAG, go.getFlfg());
	}
}

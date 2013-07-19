package com.wuxi.app.engine;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
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
}

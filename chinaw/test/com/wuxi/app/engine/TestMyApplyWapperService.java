package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.MyApply;
import com.wuxi.domain.MyApplyWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通
 *
 */
public class TestMyApplyWapperService extends AndroidTestCase {

	private static final String TAG = "TestMyApplyWapperService";

	public void testGetWapper() throws NetException, JSONException, ResultException{
		MyApplyService myApplyService=new MyApplyService(getContext());
		
		MyApplyWrapper wa=myApplyService.getPageMyApplyes("bd58fcdfe5b54f4c95ed5f2e3a945f7c", 0, 10);
		
		List<MyApply> myApplies=wa.getMyApplies();
		
		
		
		for(MyApply my : myApplies){
			
			LogUtil.i(TAG, my.getTitle());
		}
		
	}
	
}

package com.wuxi.app.engine;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.GoverTableDownLoadWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

public class TestGoverTableDownLoadService extends AndroidTestCase {

	private static final String TAG = "TestGoverTableDownLoadService";

	public void test1() throws NetException, JSONException, NODataException, UnsupportedEncodingException{
		
		
		GoverTableDownLoadService goverTableDownLoadService=new GoverTableDownLoadService(getContext());
		GoverTableDownLoadWrapper goverTableDownLoadWrapper=goverTableDownLoadService.getTableDownLoadsPage("018181932195157f01219a80e6960134",null, 0, 10);
		
		
		LogUtil.i(TAG, goverTableDownLoadWrapper.getTotalRowsAmount()+"");
	}
}

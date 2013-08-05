package com.wuxi.app.engine;

import org.json.JSONException;

import com.wuxi.domain.GoversaoonOnlineASKDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.test.AndroidTestCase;

/**
 * 
 * @author wanglu 泰得利通
 * 在线咨询详情
 *
 */
public class TestGoversaoonOnlineASKDetailService extends AndroidTestCase {

	public void test() throws NetException, JSONException, NODataException{
		GoversaoonOnlineASKDetailService goversaoonOnlineASKDetailService=new GoversaoonOnlineASKDetailService(getContext());
		GoversaoonOnlineASKDetail g=goversaoonOnlineASKDetailService.getGoversaoonOnlineASKDetail("775", "bd58fcdfe5b54f4c95ed5f2e3a945f7c");
		g.getAnswerContent();
	} 
}

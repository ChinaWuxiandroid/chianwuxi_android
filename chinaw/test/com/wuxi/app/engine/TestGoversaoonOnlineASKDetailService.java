package com.wuxi.app.engine;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.domain.GoversaoonOnlineASKDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

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
	
	
	public void test1() throws NetException, JSONException, NODataException{
		GoversaoonOnlineASKDetailService goversaoonOnlineASKDetailService=new GoversaoonOnlineASKDetailService(getContext());
		GoversaoonOnlineASKDetail g=goversaoonOnlineASKDetailService.commitGoversaoonOnlineASKDetail("4028818a28c483370128cd1caf8f24db", "XK", "你好", "0a857a943d6d4af58cc5935bc224eed1");
		g.getAnswerContent();
	} 
}

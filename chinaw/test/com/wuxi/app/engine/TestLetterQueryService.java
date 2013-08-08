package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import com.wuxi.domain.ContentType;
import com.wuxi.domain.LetterType;
import com.wuxi.exception.NetException;

import android.test.AndroidTestCase;
/**
 * 
 * @author wanglu 泰得利通
 *信件查询分类
 */
public class TestLetterQueryService extends AndroidTestCase {

	public void test1() throws NetException, JSONException{
		LetterQueryService letterQueryService=new LetterQueryService(getContext());
		
		List<ContentType> contentTypes=letterQueryService.getContentTypes();
		contentTypes.size();
	}
	
	public void test2() throws NetException, JSONException{
		LetterQueryService letterQueryService=new LetterQueryService(getContext());
		List<LetterType> letterTypes=letterQueryService.getLetterTypes();
		letterTypes.size();
	}
}

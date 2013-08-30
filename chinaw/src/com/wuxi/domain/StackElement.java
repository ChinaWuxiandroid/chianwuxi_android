package com.wuxi.domain;

import android.view.View;

/**
 * 
 * @author wanglu 泰得利通
 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00
 * activity栈元素
 *
 */
public class StackElement {
	
	private String tag;
	
	private View view;
	
	public StackElement(String tag,View view){
		this.tag = tag;
		this.view = view;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getTag() {
		return tag;
	}

	public View getView() {
		return view;
	}
	
	
}

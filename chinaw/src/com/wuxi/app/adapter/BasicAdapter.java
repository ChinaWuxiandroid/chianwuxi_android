package com.wuxi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * 
 * listview  gridview 的适配器的父类
 * @author 方庆银 
 *
 */
public abstract class BasicAdapter extends BaseAdapter {


	private int mView;
	private int[] mViewId;
	private String[] mDataName;
	protected Context context;
	

	public BasicAdapter(Context context, int view, int[] viewId,String[] dataName) {
		
		this.mView = view;
		this.mViewId = viewId;
		this.mDataName = dataName;
		this.context=context;
	}

	protected LayoutInflater getInflater() {
		
		return LayoutInflater.from(context);
	}

	protected int getView() {
		return mView;
	}

	protected int[] getViewId() {
		return mViewId;
	}

	protected String[] getDataName() {
		return mDataName;
	}
}


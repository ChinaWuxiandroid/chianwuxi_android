package com.wuxi.app.adapter;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BasicAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private int mView;
	private int[] mViewId;
	private String[] mDataName;

	public BasicAdapter(LayoutInflater inflater, int view, int[] viewId,String[] dataName) {
		this.mInflater = inflater;
		this.mView = view;
		this.mViewId = viewId;
		this.mDataName = dataName;
	}

	protected LayoutInflater getInflater() {
		return mInflater;
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

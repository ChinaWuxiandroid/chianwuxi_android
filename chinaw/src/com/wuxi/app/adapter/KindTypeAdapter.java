package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Kindtype;

/**
 * 
 * @author wanglu 泰得利通 办件分类适配器
 * 
 */
public class KindTypeAdapter extends BaseAdapter {

	public List<Kindtype> kindTypes;
	public void setKindTypes(List<Kindtype> kindTypes) {
		this.kindTypes = kindTypes;
	}

	public Context context;

	public KindTypeAdapter(List<Kindtype> kindTypes, Context context) {
		this.kindTypes = kindTypes;
		this.context = context;

	}

	@Override
	public int getCount() {
		return kindTypes.size();
	}

	@Override
	public Object getItem(int position) {
		return kindTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tv_kindType;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Kindtype kindtype = kindTypes.get(position);
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.comstuom_spinner_item_layout, null);
			viewHolder = new ViewHolder();
			TextView tv = (TextView) convertView.findViewById(R.id.sp_tv);
			viewHolder.tv_kindType = tv;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_kindType.setText(kindtype.getSubKindName());
		return convertView;
	}
	
	

}

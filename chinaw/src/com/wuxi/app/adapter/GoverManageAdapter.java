package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 组织管理适配器
 * 
 */
public class GoverManageAdapter extends BaseAdapter {

	private List<Content> contents;
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}


	private Context context;

	public GoverManageAdapter(List<Content> contents, Context context) {
		this.contents = contents;
		this.context = context;
	}

	@Override
	public int getCount() {
		return contents.size();
	}

	@Override
	public Object getItem(int position) {
		return contents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tv_title;
		TextView tv_time;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Content content = this.contents.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View
					.inflate(context, R.layout.gover_mange_item, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.gover_manage_title);

			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.gover_manage_time);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_title.setText((position + 1) + "." + content.getTitle());
		viewHolder.tv_time.setText(TimeFormateUtil.formateTime(
				content.getPublishTime(), TimeFormateUtil.DATE_PATTERN));
		return convertView;

	}
	
	
	public void addItem(Content content){
		this.contents.add(content);
	}

}

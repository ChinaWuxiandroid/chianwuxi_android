package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 首页新闻和公告适配器
 */
public class IndexNewsListAdapter extends BasicAdapter {

	private List<Content> contents;

	public IndexNewsListAdapter(Context context, int view, int[] viewId,
			List<Content> contents, String[] dataName) {
		super(context, view, viewId, dataName);

		this.contents = contents;
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

	class ViewHolder {
		public ImageView image;
		public TextView numText, title_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Content content = contents.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(getView(), null);
			int[] viewId = getViewId();
			viewHolder = new ViewHolder();
			viewHolder.numText = (TextView) convertView.findViewById(viewId[0]);
			viewHolder.title_text = (TextView) convertView
					.findViewById(viewId[1]);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		//viewHolder.numText.setText((position + 1) + "");
		String textStr = "";
		textStr = content.getTitle();

		viewHolder.title_text.setText(textStr);
		return convertView;
	}

}

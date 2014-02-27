package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.wuxi.app.R;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 内容列表适配器
 * 
 */
public class ContentListAdapter extends BaseAdapter {

	private List<Content> contents;

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	private Context context;

	public ContentListAdapter(List<Content> contents, Context context) {

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
		TextView title_text;// 标题
		TextView title_time;// 时间
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Content content = contents.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.content_list_item_layout, null);
			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.content_list_tv_title);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String title = "";
		title = "·" + content.getTitle();
		String time = "("
				+ TimeFormateUtil.formateTime(content.getPublishTime(),
						TimeFormateUtil.DATE_PATTERN) + ")";

		String showText = title + "   " + time;
		viewHolder.title_text.setText(showText, BufferType.EDITABLE);

		Spannable spannable = (Spannable) viewHolder.title_text.getText();
		spannable.setSpan(new RelativeSizeSpan(1.1f), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#808080")),
				title.length(), showText.length(),
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		viewHolder.title_text.setText(spannable);
		return convertView;
	}

	public void addItem(Content content) {
		this.contents.add(content);
	}

}

package com.wuxi.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.PromoMoreVideoInfo;

public class PromoMoreVideoAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<PromoMoreVideoInfo> mList;

	public PromoMoreVideoAdapter(Context context,
			ArrayList<PromoMoreVideoInfo> list) {
		this.context = context;
		this.mList = list;
	}

	public void addItem(ArrayList<PromoMoreVideoInfo> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.promo_more_video_item,
					null);
			holder = new ViewHolder();

			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.promo_more_video_item_img);
			holder.mTextTheme = (TextView) convertView
					.findViewById(R.id.promo_more_video_item_theme_text);
			holder.mTextTime = (TextView) convertView
					.findViewById(R.id.promo_more_video_item_time_text);
			holder.mTextGuests = (TextView) convertView
					.findViewById(R.id.promo_more_video_item_guests_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PromoMoreVideoInfo mInfo = mList.get(position);

		holder.mTextTheme.setText("访谈主题：" + mInfo.getSubject());
		holder.mTextTime.setText("访谈时间：" + mInfo.getWorkDate());
		holder.mTextGuests.setText("访谈嘉宾：" + mInfo.getGuests());
		// if(mInfo.get)
		// holder.mImageView.set
		return convertView;
	}

	private class ViewHolder {
		// 图片
		private ImageView mImageView;
		// 主题
		private TextView mTextTheme;
		// 时间
		private TextView mTextTime;
		// 嘉宾
		private TextView mTextGuests;
	}

}

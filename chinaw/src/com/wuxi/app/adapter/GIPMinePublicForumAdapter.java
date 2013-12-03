package com.wuxi.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.GIPMinePublicForumInfo;

public class GIPMinePublicForumAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<GIPMinePublicForumInfo> mList;

	public GIPMinePublicForumAdapter(Context context,
			ArrayList<GIPMinePublicForumInfo> list) {
		this.context = context;
		this.mList = list;

	}

	/**
	 * 添加数据
	 * 
	 * @方法： addItem
	 * @param list
	 */
	public void addItem(ArrayList<GIPMinePublicForumInfo> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public void clear() {
		if (mList != null) {
			mList.clear();
			notifyDataSetChanged();
		}
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
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.gip_mine_publicforum_list_item, null);
			holder.mTextTilte = (TextView) convertView
					.findViewById(R.id.gip_mine_publicforum_list_title);
			holder.mTextCreateTime = (TextView) convertView
					.findViewById(R.id.gip_mine_publicforum_createTime);
			holder.mTextUpdateTime = (TextView) convertView
					.findViewById(R.id.gip_mine_publicforum_list_updateTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		GIPMinePublicForumInfo mInfo = mList.get(position);

		holder.mTextTilte.setText((position + 1) + ".标题：" + mInfo.getTitle());
		if (mInfo.getCreateDate().length() > 5) {
			holder.mTextCreateTime.setText("发布时间："
					+ TimeFormateUtil.formateTime(mInfo.getCreateDate()
							.toString(), TimeFormateUtil.DATE_PATTERN));
		} else {
			holder.mTextCreateTime.setText("发布时间：");
		}
		if (mInfo.getBegintime().length() > 5) {
			holder.mTextUpdateTime.setText("最后修改时间："
					+ TimeFormateUtil.formateTime(mInfo.getBegintime()
							.toString(), TimeFormateUtil.DATE_PATTERN));
		} else {
			holder.mTextUpdateTime.setText("最后修改时间：");
		}
		return convertView;
	}

	/**
	 * 定义控件
	 * 
	 * @类名： ViewHolder
	 * @作者： 陈彬
	 * @创建时间： 2013 2013-11-25 下午3:07:54
	 * @修改时间：
	 * @修改描述：
	 */
	private class ViewHolder {

		private TextView mTextTilte;

		private TextView mTextCreateTime;

		private TextView mTextUpdateTime;
	}
}

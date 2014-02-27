package com.wuxi.app.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.MyOpinionOpenWrapper;

public class MyOpinionOpenAdapter extends BaseAdapter {

	private Context mContext = null;

	ArrayList<MyOpinionOpenWrapper> mList = null;

	public MyOpinionOpenAdapter(Context mContext,
			ArrayList<MyOpinionOpenWrapper> list) {
		this.mContext = mContext;
		this.mList = list;
	}

	public void addListItem(ArrayList<MyOpinionOpenWrapper> mList) {
		this.mList = mList;
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
			convertView = View.inflate(mContext,
					R.layout.my_opinion_open_list_layout, null);
			holder = new ViewHolder();
			holder.mTextTitle = (TextView) convertView
					.findViewById(R.id.my_opinion_open_list_title);
			holder.mTextTime = (TextView) convertView
					.findViewById(R.id.my_opinion_open_list_time);
			holder.mTextStatus = (TextView) convertView
					.findViewById(R.id.my_opinion_open_list_statue);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MyOpinionOpenWrapper mWrapper = mList.get(position);

		holder.mTextTitle
				.setText((position + 1) + ".标题：" + mWrapper.getTitle());

		if (mWrapper.getSendTime() != null && mWrapper.getSendTime() != ""
				&& !mWrapper.getSendTime().equals("")
				&& !mWrapper.getSendTime().equals(null)
				&& mWrapper.getSendTime().length() > 5) {
			holder.mTextTime.setText("提交时间：" + getDate(mWrapper.getSendTime()));
		} else {
			holder.mTextTime.setText("提交时间：");
		}

		if (mWrapper.getState().equals("0")) {
			holder.mTextStatus.setText("办理状态：处理中");
		} else {
			holder.mTextStatus.setText("办理状态：已处理");
		}

		return convertView;
	}

	/**
	 * 定义控件
	 */
	private class ViewHolder {

		// 标题
		private TextView mTextTitle;

		// 提交时间
		private TextView mTextTime;

		// 处理状态
		private TextView mTextStatus;

	}

	/**
	 * 
	 * 时间戳转换为String
	 * 
	 * @param unixDate
	 * @return
	 */
	public Date getDate(String unixDate) {

		SimpleDateFormat fm1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long unixLong = 0;
		// String date = "";
		Date date = null;
		try {
			unixLong = Long.parseLong(unixDate) * 1000;
		} catch (Exception ex) {
			System.out.println("String转换Long错误，请确认数据可以转换！");
		}
		try {
			// date = fm2.format(unixLong);
			date = new Date(fm2.format(unixLong));
			// date = fm2.format(new Date(date));
		} catch (Exception ex) {
			System.out.println("String转换Date错误，请确认数据可以转换！");
		}
		return date;
	}

	/**
	 * 
	 * 将String类型转换为Date数据类型
	 * 
	 * @param str
	 *            将要进行转换的String
	 * @return 返回Date
	 */
	// public static Date StringToDate(String str) {
	// // 判断是否为空
	// if (str != null) {
	//
	// String time = getDate(str);
	//
	// Date date = null;
	// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
	// "yyyy-MM-dd hh:mm:ss");
	// try {
	// date = simpleDateFormat.parse(time);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// return date;
	// } else {
	// return null;
	// }
	// }

}

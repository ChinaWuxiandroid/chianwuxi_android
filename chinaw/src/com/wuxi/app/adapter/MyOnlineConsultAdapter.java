package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goversaloon.MyOnlineAskActivity;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Myconsult;

/**
 * 
 * @author wanglu 泰得利通 我的在线咨询 适配器
 * 
 */
public class MyOnlineConsultAdapter extends BaseAdapter implements
		OnClickListener {

	private List<Myconsult> myconsults;

	private Context context;

	private Myconsult selectMyconsult;

	public MyOnlineConsultAdapter(List<Myconsult> myconsults, Context context) {
		this.myconsults = myconsults;
		this.context = context;

	}

	@Override
	public int getCount() {
		return myconsults.size();
	}

	@Override
	public Object getItem(int position) {
		return myconsults.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tv_title;

		ImageView iv_view;

		ImageView iv_goask;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Myconsult myconsult = myconsults.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(
				context, R.layout.gover_myonlineask_item, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView.findViewById(R.id.gover_onlineask_title);
			viewHolder.iv_view = (ImageView) convertView.findViewById(R.id.gover_onlineask_view);
			viewHolder.iv_goask = (ImageView) convertView.findViewById(R.id.gover_onlineask_goask);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String text = myconsult.getTitle();

		String time = TimeFormateUtil.formateTime(
			myconsult.getSendDate(), TimeFormateUtil.DATE_PATTERN);
		viewHolder.tv_title.setText(text + " (" + time + " )");
		viewHolder.iv_view.setOnClickListener(this);
		viewHolder.iv_goask.setOnClickListener(this);
		selectMyconsult = myconsult;
		return convertView;

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent(context, MyOnlineAskActivity.class);
		switch (v.getId()) {
		case R.id.gover_onlineask_view:// 咨询详情

			intent.putExtra("showType", 0);
			break;
		case R.id.gover_onlineask_goask:// 继续咨询
			intent.putExtra("showType", 1);
			break;
		}

		intent.putExtra("selectMyconsult", selectMyconsult);
		MainTabActivity.instance.addView(intent);

	}

	public void addItem(Myconsult myconsult) {
		myconsults.add(myconsult);
	}

}

package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.EfficaComplain;

/**
 * 
 * @author wanglu 泰得利通 能效评价适配器
 * 
 * 
 */
public class EfficacyComplaintAdapter extends BaseAdapter {

	private List<EfficaComplain> efficaComplains;
	private Context context;

	public EfficacyComplaintAdapter(List<EfficaComplain> efficaComplains,
			Context context) {
		this.efficaComplains = efficaComplains;
		this.context = context;
	}

	@Override
	public int getCount() {
		return efficaComplains.size();
	}

	@Override
	public Object getItem(int position) {
		return efficaComplains.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tv_title;
		TextView tv_state;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		EfficaComplain eff = efficaComplains.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gover_effcom_item,
					null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.gover_myonline_effcom_title);
			viewHolder.tv_state = (TextView) convertView
					.findViewById(R.id.gover_myonline_effcom_state);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_title.setText(eff.getTitle());

		viewHolder.tv_state.setText("答复部门："
				+ eff.getDoDpetname()
				+ " 答复时间: "
				+ TimeFormateUtil.formateTime(eff.getEndTime(),
						TimeFormateUtil.DATE_PATTERN));

		return convertView;

	}

	public void addItem(EfficaComplain eff) {
		this.efficaComplains.add(eff);
	}
}

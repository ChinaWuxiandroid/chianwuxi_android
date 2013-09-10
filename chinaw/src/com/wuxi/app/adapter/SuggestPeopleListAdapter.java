/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: SuggestPeopleListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 民意调查列表适配器 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-9 下午2:08:43
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.domain.PoliticsWrapper.Politics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： SuggestPeopleListAdapter
 * @描述： 民意调查列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-9 下午2:08:43
 * @修改时间：
 * @修改描述：
 * 
 */
public class SuggestPeopleListAdapter extends BaseAdapter {

	private Context context;

	private List<Politics> politics;

	/**
	 * @方法： SuggestPeopleListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param politics
	 */
	public SuggestPeopleListAdapter(Context context,
			List<PoliticsWrapper.Politics> politics) {
		this.context = context;
		this.politics = politics;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param politics
	 */
	public void addItem(Politics politics) {
		this.politics.add(politics);
	}

	/**
	 * @param politics
	 *            要设置的 politics
	 */
	public void setPolitics(List<PoliticsWrapper.Politics> politics) {
		this.politics = politics;
	}

	@Override
	public int getCount() {
		return politics.size();
	}

	@Override
	public Object getItem(int position) {
		return politics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-9 下午2:13:34
	 * @修改时间： 
	 * @修改描述： 
	 *
	 */
	class ViewHolder {
		public TextView title_text;
		public TextView beginTime_text;
		public TextView endTime_text;
		public TextView depName_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_suggest_peopelwill_listview_item, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.gip_suggest_peoplewill_listitem_tile);
			viewHolder.beginTime_text = (TextView) convertView
					.findViewById(R.id.gip_suggest_peoplewill_textview_begintime);
			viewHolder.endTime_text = (TextView) convertView
					.findViewById(R.id.gip_suggest_peoplewill_textview_endtime);
			viewHolder.depName_text = (TextView) convertView
					.findViewById(R.id.gip_suggest_peoplewill_textview_depname);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title_text.setText(politics.get(position).getTitle());
		viewHolder.beginTime_text
				.setText(politics.get(position).getBeginTime());
		viewHolder.endTime_text.setText(politics.get(position).getEndTime());
		return convertView;
	}
}

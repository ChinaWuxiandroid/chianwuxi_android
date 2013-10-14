/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GovernmentGeneralizeAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 政府概括 子菜单列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-30 上午10:35:30
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Content;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： GovernmentGeneralizeAdapter
 * @描述： 政府概括 子菜单列表适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-9-30 上午10:35:30
 * @修改时间：
 * @修改描述：
 */
public class GovernmentGeneralizeAdapter extends BaseAdapter {

	// 日期显示格式
	private static final String DATA_STYLE = "yyyy年MM月dd日";

	private List<Content> contents;

	/**
	 * @方法： setContents
	 * @描述： 设置数据
	 * @param contents
	 */
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	private Context context;

	/**
	 * @方法： GovernmentGeneralizeAdapter
	 * @描述： 构造方法
	 * @param contents
	 * @param context
	 */
	public GovernmentGeneralizeAdapter(List<Content> contents, Context context) {
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

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-30 上午11:29:12
	 * @修改时间：
	 * @修改描述：
	 */
	static class ViewHolder {
		TextView title;// 标题
		TextView openTime;// 公开时间
		TextView buildTime;// 生成日期
		TextView changeTime;// 修改时间
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.gpm_government_generalize_list_layout, null);
			viewHolder = new ViewHolder();

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.government_generalize_list_title);
			viewHolder.openTime = (TextView) convertView
					.findViewById(R.id.government_generalize_list_open_time);
			viewHolder.changeTime = (TextView) convertView
					.findViewById(R.id.government_generalize_list_change_time);
			viewHolder.buildTime = (TextView) convertView
					.findViewById(R.id.government_generalize_list_build_time);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText("标题：" + contents.get(position).getTitle());

		viewHolder.openTime.setText("公开日期："
				+ TimeFormateUtil.formateTime(contents.get(position)
						.getPublishTime(), DATA_STYLE));

		if (contents.get(position).getBuildTime().equals("null")) {
			viewHolder.buildTime.setText("生成日期：" + "null");
		} else {
			viewHolder.buildTime.setText("生成日期："
					+ TimeFormateUtil.formateTime(contents.get(position)
							.getBuildTime(), DATA_STYLE));
		}

		if (contents.get(position).getUpdateTime().equals("null")) {
			viewHolder.changeTime.setText("修改日期：" + "null");
		} else {
			viewHolder.changeTime.setText("修改日期："
					+ TimeFormateUtil.formateTime(contents.get(position)
							.getUpdateTime(), DATA_STYLE));
		}

		return convertView;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param content
	 */
	public void addItem(Content content) {
		this.contents.add(content);
	}

}

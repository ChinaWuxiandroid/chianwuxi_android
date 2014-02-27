/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PolicieRegulationAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 政策法规子菜单列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-30 上午11:27:01
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
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
import com.wuxi.domain.MenuItem;

/**
 * @类名： PolicieRegulationAdapter
 * @描述： 政策法规子菜单列表适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-9-30 上午11:27:01
 * @修改时间：
 * @修改描述：
 */
public class PolicieRegulationAdapter extends BaseAdapter {

	// 时间显示格式
	private static final String DATA_STYLE = "yyyy年MM月dd日";

	// 内容列表对象
	private List<Content> contents;

	// 菜单对象
	private MenuItem menuItem;

	/**
	 * @param menuItem
	 *            要设置的 menuItem
	 */
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	/**
	 * @方法： setContents
	 * @描述： 设置列表对象
	 * @param contents
	 */
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	private Context context;

	/**
	 * @方法： PolicieRegulationAdapter
	 * @描述： 构造方法
	 * @param contents
	 * @param context
	 */
	public PolicieRegulationAdapter(List<Content> contents, Context context) {
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
	 * @创建时间： 2013 2013-9-30 下午4:35:07
	 * @修改时间：
	 * @修改描述：
	 */
	static class ViewHolder {
		TextView title;// 标题
		TextView publishDept;// 发布部门
		TextView openTime;// 公开时间
		TextView buildTime;// 生成时间
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.gpm_policie_regulation_layout, null);
			viewHolder = new ViewHolder();

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.policie_regulation_list_title);
			viewHolder.publishDept = (TextView) convertView
					.findViewById(R.id.policie_regulation_list_publish_dept);
			viewHolder.openTime = (TextView) convertView
					.findViewById(R.id.policie_regulation_list_open_time);
			viewHolder.buildTime = (TextView) convertView
					.findViewById(R.id.policie_regulation_list_build_time);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 设置标题
		viewHolder.title.setText("标题：" + contents.get(position).getTitle());
		if (menuItem.getName().endsWith("国家法律")
				|| menuItem.getName().endsWith("国家行政法规规章")
				|| menuItem.getName().endsWith("省级法规规章")) {
			viewHolder.publishDept.setVisibility(View.GONE);
			if (contents.get(position).getBuildTime().equals("null")) {
				viewHolder.buildTime.setText("生成日期：");
			} else {
				viewHolder.buildTime.setText("生成日期："
						+ TimeFormateUtil.formateTime(contents.get(position)
								.getBuildTime(), DATA_STYLE));
			}

			viewHolder.openTime.setVisibility(View.GONE);
		} else {
			if (contents.get(position).getOrganization().equals("null")) {
				viewHolder.publishDept.setText("发布机构：");
			} else {
				viewHolder.publishDept.setText("发布机构："
						+ contents.get(position).getOrganization());
			}
			viewHolder.buildTime.setVisibility(View.GONE);

			viewHolder.openTime.setText("公开日期："
					+ TimeFormateUtil.formateTime(contents.get(position)
							.getPublishTime(), DATA_STYLE));
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

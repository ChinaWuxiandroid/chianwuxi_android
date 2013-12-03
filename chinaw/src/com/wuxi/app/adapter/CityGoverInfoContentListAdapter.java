/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: CityGoverInfoContentListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: TODO(用一句话描述该文件做什么) 
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-25 上午10:57:39
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
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;

/**
 * @类名： CityGoverInfoContentListAdapter
 * @描述： TODO
 * @作者： 罗森
 * @创建时间： 2013 2013-10-25 上午10:57:39
 * @修改时间：
 * @修改描述：
 */
public class CityGoverInfoContentListAdapter extends BaseAdapter {

	// 时间显示格式
	private static final String DATA_STYLE = "yyyy年MM月dd日";

	private Channel channel;

	/**
	 * @param channel
	 *            要设置的 channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	private List<Content> contents;

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	private Context context;

	public CityGoverInfoContentListAdapter(List<Content> contents,
			Context context) {

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
		TextView title;// 标题
		TextView publishdept;// 发布机构
		TextView opentime;// 公开时间
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Content content = contents.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.city_gover_info_content_list_layout, null);
			viewHolder = new ViewHolder();

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.cgi_list_title);
			viewHolder.publishdept = (TextView) convertView
					.findViewById(R.id.cgi_list_publish_dept);
			viewHolder.opentime = (TextView) convertView
					.findViewById(R.id.cgi_list_open_time);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText("标题：" + content.getTitle());

		if (channel.getChannelName().equals("中长期规划")
				|| channel.getChannelName().equals("年度规划计划")
				|| channel.getChannelName().equals("专项规划计划")
				|| channel.getChannelName().equals("区域规划及相关政策")
				|| channel.getChannelName().equals("县区政府工作报告")
				|| channel.getChannelName().equals("部门工作总结")
				|| channel.getChannelName().equals("议案建议办理情况报告")
				|| channel.getChannelName().equals("部门预决算报告")
				|| channel.getChannelName().equals("专项资金")
				|| channel.getChannelName().equals("采购公示公告")
				|| channel.getChannelName().equals("中标公告")
				|| channel.getChannelName().equals("违法违规行为查处")
				|| channel.getChannelName().equals("部门行政事业性收费")
				|| channel.getChannelName().equals("部门统计数据")
				|| channel.getChannelName().equals("数据解读")
				|| channel.getChannelName().equals("公务员招考")
				|| channel.getChannelName().equals("事业单位招聘")
				|| channel.getChannelName().equals("干部任前公告")
				|| channel.getChannelName().equals("干部任免公告")
				|| channel.getChannelName().equals("干部选拔的条件和程序")
				|| channel.getChannelName().equals("应急知识")
				|| channel.getChannelName().equals("其他重要会议")) {
			viewHolder.publishdept.setVisibility(View.VISIBLE);
			if (contents.get(position).getOrganization().equals("null")) {
				viewHolder.publishdept.setText("发布机构：");
			} else {
				viewHolder.publishdept.setText("发布机构："
						+ contents.get(position).getOrganization());
			}
		} else {
			viewHolder.publishdept.setVisibility(View.GONE);
		}

		viewHolder.opentime.setText("公开日期："
				+ TimeFormateUtil.formateTime(contents.get(position)
						.getPublishTime(), DATA_STYLE));

		return convertView;
	}

	public void addItem(Content content) {
		this.contents.add(content);
	}

}

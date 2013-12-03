/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: LiveHomeLeaveMessageListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 留言提问列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-11 上午9:50:23
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.LeaveMessageWrapper.LeaveMessage;

/**
 * @类名： LiveHomeLeaveMessageListAdapter
 * @描述： 留言提问列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-11 上午9:50:23
 * @修改时间：
 * @修改描述：
 * 
 */
public class LiveHomeLeaveMessageListAdapter extends BaseAdapter {

	private Context context;

	private List<LeaveMessage> leaveMessages;

	/**
	 * @方法： LiveHomeLeaveMessageListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param leaveMessages
	 */
	public LiveHomeLeaveMessageListAdapter(Context context,
			List<LeaveMessage> leaveMessages) {
		this.context = context;
		this.leaveMessages = leaveMessages;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param leaveMessage
	 */
	public void addItem(LeaveMessage leaveMessage) {
		this.leaveMessages.add(leaveMessage);
	}

	public void setLeaveMessages(List<LeaveMessage> leaveMessages) {
		this.leaveMessages = leaveMessages;
	}

	@Override
	public int getCount() {
		return leaveMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return leaveMessages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 内部类，声明控件变量
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	class ViewHolder {
		public TextView leave_content_text;
		public TextView leave_ask_time_text;
		public TextView leave_answer_content_text;
		public TextView leave_answer_time_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.vedio_leavemessage_list_layout, null);

			holder = new ViewHolder();

			holder.leave_content_text = (TextView) convertView
					.findViewById(R.id.leave_content_text);
			holder.leave_ask_time_text = (TextView) convertView
					.findViewById(R.id.leave_ask_time_text);
			holder.leave_answer_content_text = (TextView) convertView
					.findViewById(R.id.leave_answer_content_text);
			holder.leave_answer_time_text = (TextView) convertView
					.findViewById(R.id.leave_answer_time_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.leave_content_text.setText(leaveMessages.get(position)
				.getContent());
		holder.leave_ask_time_text.setText("["
				+ leaveMessages.get(position).getSubmitTime() + "]");
		holder.leave_answer_content_text.setText(leaveMessages.get(position)
				.getAnswerContent());
		holder.leave_answer_time_text.setText("["
				+ leaveMessages.get(position).getRecommendTime() + "]");

		return convertView;
	}

}

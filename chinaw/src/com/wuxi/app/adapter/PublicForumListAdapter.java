/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PublicForumListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 公众论坛列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-11 上午10:50:20
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
import com.wuxi.domain.ForumWrapper.Forum;

/**
 * @类名： PublicForumListAdapter
 * @描述： 公众论坛列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-11 上午10:50:20
 * @修改时间：
 * @修改描述：
 * 
 */
public class PublicForumListAdapter extends BaseAdapter {

	private Context context;

	private List<Forum> forums;

	/**
	 * 
	 * @方法： PublicForumListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param forums
	 */
	public PublicForumListAdapter(Context context, List<Forum> forums) {
		this.context = context;
		this.forums = forums;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param forum
	 */
	public void addItem(Forum forum) {
		this.forums.add(forum);
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	@Override
	public int getCount() {
		return forums.size();
	}

	@Override
	public Object getItem(int position) {
		return forums.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 内部类，定义了列表项的布局控件
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	class ViewHolder {
		public TextView title_text;
		public TextView beginTime_text;
		public TextView readCount_text;
		public TextView resultCount_text;
		public TextView sentUser_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_forum_list_item, null);

			holder = new ViewHolder();
			holder.title_text = (TextView) convertView
					.findViewById(R.id.gip_forum_list_title);
			holder.beginTime_text = (TextView) convertView
					.findViewById(R.id.gip_forum_begintime_text);
			holder.readCount_text = (TextView) convertView
					.findViewById(R.id.gip_forum_readCount_text);
			holder.resultCount_text = (TextView) convertView
					.findViewById(R.id.gip_forum_resultCount_text);
			holder.sentUser_text = (TextView) convertView
					.findViewById(R.id.gip_forum_sentUser_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title_text.setText(forums.get(position).getTitle());
		holder.beginTime_text.setText(forums.get(position).getBeginTime());
		holder.readCount_text.setText(forums.get(position).getReadCount());
		holder.resultCount_text.setText(forums.get(position).getResultCount());
		holder.sentUser_text.setText(forums.get(position).getSentUser());

		return convertView;
	}

}

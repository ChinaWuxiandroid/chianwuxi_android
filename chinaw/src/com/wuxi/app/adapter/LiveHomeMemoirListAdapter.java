/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: LiveHomeMemoirListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 访谈实录列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-11 上午9:26:46
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
import com.wuxi.domain.MemoirWrapper.Memoir;

/**
 * @类名： LiveHomeMemoirListAdapter
 * @描述： 访谈实录列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-11 上午9:26:46
 * @修改时间：
 * @修改描述：
 * 
 */
public class LiveHomeMemoirListAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<Memoir> memoirs;
	
	/**
	 * @方法： LiveHomeMemoirListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param memoirs
	 */
	public LiveHomeMemoirListAdapter(Context context,List<Memoir> memoirs){
		this.context = context;
		this.memoirs = memoirs;
	}
	
	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param memoir
	 */
	public void addItem(Memoir memoir){
		this.memoirs.add(memoir);
	}

	public void setMemoirs(List<Memoir> memoirs) {
		this.memoirs = memoirs;
	}

	@Override
	public int getCount() {
		return memoirs.size();
	}

	@Override
	public Object getItem(int position) {
		return memoirs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 内部类，定义了列表的项的布局控件
	 * 
	 * @author 智佳 罗森
	 * @createtime 2013年8月9日 20:36
	 * 
	 */
	class ViewHolder {
		public TextView pepole_text;
		public TextView content_text;
		public TextView time_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.vedio_memoir_list_layout, null);

			holder = new ViewHolder();

			holder.pepole_text = (TextView) convertView
					.findViewById(R.id.memoir_pepole_text);
			holder.content_text = (TextView) convertView
					.findViewById(R.id.memoir_content_text);
			holder.time_text = (TextView) convertView
					.findViewById(R.id.memoir_time_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if ((memoirs.get(position).getAnswerType()) == 0) {
			holder.pepole_text.setText("[主持人：]");
		} else if ((memoirs.get(position).getAnswerType()) == 1) {
			holder.pepole_text.setText("[嘉宾：]");
		}
		holder.content_text.setText(memoirs.get(position).getContent());
		holder.time_text.setText("[" + memoirs.get(position).getSubmitTime()
				+ "]");

		return convertView;
	}

}

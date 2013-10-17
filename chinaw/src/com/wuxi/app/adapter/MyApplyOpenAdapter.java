/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MyApplyOpenAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 我的依申请公开适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-15 上午11:49:59
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.MyApplyOpenWrapper.MyApplyOpen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： MyApplyOpenAdapter
 * @描述： 我的依申请公开适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-10-15 上午11:49:59
 * @修改时间： 
 * @修改描述： 
 */
public class MyApplyOpenAdapter extends BaseAdapter {

	private Context context;
	
	private List<MyApplyOpen> myApplyOpens;
	
	public MyApplyOpenAdapter(Context context,List<MyApplyOpen> myApplyOpens){
		this.context = context;
		this.myApplyOpens = myApplyOpens;
	}
	
	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param myApplyOpen
	 */
	public void addItem(MyApplyOpen myApplyOpen){
		this.myApplyOpens.add(myApplyOpen);
	}
	
	/**
	 * @param myApplyOpens 要设置的  myApplyOpens 
	 */
	public void setMyApplyOpens(List<MyApplyOpen> myApplyOpens) {
		this.myApplyOpens = myApplyOpens;
	}

	/* (non-Javadoc)
	 * @方法： getCount
	 * @描述： 
	 * @return 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return myApplyOpens.size();
	}

	/* (non-Javadoc)
	 * @方法： getItem
	 * @描述： 
	 * @param position
	 * @return 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return myApplyOpens.get(position);
	}

	/* (non-Javadoc)
	 * @方法： getItemId
	 * @描述： 
	 * @param position
	 * @return 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder{
		TextView title;
		TextView code;
		TextView answerdep;
		TextView answertime;
	}
	
	/* (non-Javadoc)
	 * @方法： getView
	 * @描述： 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return 
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.my_apply_open_list_layout, null);
			
			holder = new ViewHolder();
			
			holder.title = (TextView) convertView.findViewById(R.id.my_apply_open_title);
			holder.code = (TextView) convertView.findViewById(R.id.my_apply_open_code);
			holder.answerdep = (TextView) convertView.findViewById(R.id.my_apply_open_answerdep);
			holder.answertime = (TextView) convertView.findViewById(R.id.my_apply_open_answertime);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText("标题："+myApplyOpens.get(position).getTitle());
		holder.code.setText("编号："+myApplyOpens.get(position).getCode());
		holder.answerdep.setText("答复部门："+myApplyOpens.get(position).getAnswerDep());
		holder.answertime.setText("答复时间："+myApplyOpens.get(position).getAnswerDate());
		
		return convertView;
	}

}

/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: SuggestLawSuggestionAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: TODO(用一句话描述该文件做什么) 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-29 下午4:10:37
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.PoliticsWrapper.Politics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： SuggestLawSuggestionAdapter
 * @描述： TODO
 * @作者： 罗森
 * @创建时间： 2013 2013-9-29 下午4:10:37
 * @修改时间： 
 * @修改描述： 
 */
public class SuggestLawSuggestionAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<Politics> politics;
	
	public SuggestLawSuggestionAdapter(Context context,List<Politics> politics){
		this.context = context;
		this.politics = politics;
	}
	
	public void addItem(Politics politics){
		this.politics.add(politics);
	}

	/**
	 * @param politics 要设置的  politics 
	 */
	public void setPolitics(List<Politics> politics) {
		this.politics = politics;
	}

	/* (non-Javadoc)
	 * @方法： getCount
	 * @描述： 
	 * @return 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return politics.size();
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
		return politics.get(position);
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
	
	class ViewHolder {
		public TextView title_text;
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_suggest_law_listview_item, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.gip_suggest_textview_title);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title_text.setText(politics.get(position).getTitle());

		return convertView;
	}

}

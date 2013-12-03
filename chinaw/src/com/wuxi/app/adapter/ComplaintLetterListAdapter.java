/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: ComplaintLetterListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 建议咨询投诉信件列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-6 下午3:33:18
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
import com.wuxi.domain.LetterWrapper.Letter;

/**
 * @类名： ComplaintLetterListAdapter
 * @描述： 建议咨询投诉信件列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-6 下午3:33:18
 * @修改时间： 
 * @修改描述： 
 *
 */
public class ComplaintLetterListAdapter extends BaseAdapter{

	private Context context;
	//信件列表
	private List<Letter> letters;
	
	
	/**
	 * @param letters 要设置的  letters 
	 */
	public void setLetters(List<Letter> letters) {
		this.letters = letters;
	}

	/**
	 * @方法： LettersListViewAdapter
	 * @描述： 构造方法
	 * @param fragment
	 */
	public ComplaintLetterListAdapter(List<Letter> letters,Context context) {
		this.context = context;
		this.letters = letters;
	}
	
	/**
	 * @方法： addItem
	 * @描述： 添加信件到信件列表
	 * @param letter
	 */
	public void addItem(Letter letter){
		this.letters.add(letter);
	}

	@Override
	public int getCount() {
		return letters.size();
	}

	@Override
	public Object getItem(int position) {
		return letters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 建议咨询投诉信件类表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-6 下午3:35:40
	 * @修改时间： 
	 * @修改描述： 
	 *
	 */
	class ViewHolder {
		public TextView title_text;
		public TextView code_text;
		public TextView type_text;
		public TextView depname_text;
		public TextView answerDate_text;
		public TextView readCount_text;
		public TextView appraise_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_12345_complaint_listview_item, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_tilte);
			viewHolder.code_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_code);
			viewHolder.type_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_type);
			viewHolder.depname_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_depname);
			viewHolder.answerDate_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_answerDate);
			viewHolder.readCount_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_readcount);
			viewHolder.appraise_text = (TextView) convertView
					.findViewById(R.id.gip_12345_complaint_appraise);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.title_text.setText(letters.get(position).getTitle());
		viewHolder.code_text.setText(letters.get(position).getCode());
		viewHolder.type_text.setText(letters.get(position).getType());
		viewHolder.depname_text.setText(letters.get(position).getDepname());
		
		viewHolder.answerDate_text.setText(letters.get(position)
				.getAnswerdate());
		
		viewHolder.readCount_text.setText(String.valueOf(letters.get(
				position).getReadcount()));
		viewHolder.appraise_text.setText(letters.get(position)
				.getAppraise());

		return convertView;
	}
}

/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: AnswerStatisticsListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 答复率统计列表适配器 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-9 上午9:52:10
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
import com.wuxi.domain.StatisticsLetter;

/**
 * @类名： AnswerStatisticsListAdapter
 * @描述： 答复率统计列表适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-9-9 上午9:52:10
 * @修改时间：
 * @修改描述：
 * 
 */
public class AnswerStatisticsListAdapter extends BaseAdapter {

	private Context context;

	private List<StatisticsLetter> letters;

	/**
	 * @方法： AnswerStatisticsListAdapter
	 * @描述： 构造方法
	 * @param letters
	 * @param context
	 */
	public AnswerStatisticsListAdapter(List<StatisticsLetter> letters,
			Context context) {
		this.context = context;
		this.letters = letters;
	}
	
	/**
	 * @方法： addItem
	 * @描述： 添加信件到列表
	 * @param letter
	 */
	public void addItem(StatisticsLetter letter){
		letters.add(letter);
	}

	/**
	 * @param letters
	 *            要设置的 letters
	 */
	public void setLetters(List<StatisticsLetter> letters) {
		this.letters = letters;
	}

	@Override
	public int getCount() {
		if (letters != null && letters.size() > 0) {
			return letters.size();
		} else {
			return 0;
		}

	}

	@Override
	public Object getItem(int position) {
		return letters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		public TextView depName_text;
		public TextView acceptedNum_text;
		public TextView replyNum_text;
		public TextView replyRate_text;
		public TextView replyDay_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_12345_answerstati_listview_item, null);

			viewHolder = new ViewHolder();

			viewHolder.depName_text = (TextView) convertView
					.findViewById(R.id.gip_12345_answerstati_depname);
			viewHolder.acceptedNum_text = (TextView) convertView
					.findViewById(R.id.gip_12345_answerstati_acceptedNum);
			viewHolder.replyNum_text = (TextView) convertView
					.findViewById(R.id.gip_12345_answerstati_replyNum);
			viewHolder.replyRate_text = (TextView) convertView
					.findViewById(R.id.gip_12345_answerstati_replyRate);
			viewHolder.replyDay_text = (TextView) convertView
					.findViewById(R.id.gip_12345_answerstati_replyDay);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.depName_text.setText(letters.get(position).getDepname());
		viewHolder.acceptedNum_text.setText(String.valueOf(letters
				.get(position).getAcceptedNum()));
		viewHolder.replyNum_text.setText(String.valueOf(letters.get(position)
				.getReplyNum()));
		viewHolder.replyRate_text.setText(String.valueOf(letters.get(position)
				.getReplyRate()));
		viewHolder.replyDay_text.setText(letters.get(position).getReplyDay());

		return convertView;
	}

}

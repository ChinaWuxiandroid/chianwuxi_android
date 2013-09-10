/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: HotLettersListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 热门信件选登信件列表适配器 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-6 下午4:47:13
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.LetterWrapper.Letter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： HotLettersListAdapter
 * @描述： 热门信件选登信件列表适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-9-6 下午4:47:13
 * @修改时间： 
 * @修改描述： 
 *
 */
public class HotLettersListAdapter extends BaseAdapter{
	
	private Context context;
	
	private List<Letter> letters;
	
	/**
	 * @方法： HotLettersListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param letters
	 */
	public HotLettersListAdapter(Context context,List<Letter> letters){
		this.context = context;
		this.letters = letters;
	}
	
	/**
	 * @param letters 要设置的  letters 
	 */
	public void setLetters(List<Letter> letters) {
		this.letters = letters;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加信件到列表
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

	class ViewHolder {
		public TextView title_text;
		public TextView depname_text;
		public TextView answerDate_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_12345_hotmail_listview_item, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.gip_12345_hotmail_tilte);
			viewHolder.depname_text = (TextView) convertView
					.findViewById(R.id.gip_12345_hotmail_depname);
			viewHolder.answerDate_text = (TextView) convertView
					.findViewById(R.id.gip_12345_hotmail_answerDate);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.title_text.setText("信件标题："+letters.get(position).getTitle());
		viewHolder.depname_text.setText(letters.get(position).getDepname());
		viewHolder.answerDate_text.setText(letters.get(position)
				.getAnswerdate());

		return convertView;
	}

}

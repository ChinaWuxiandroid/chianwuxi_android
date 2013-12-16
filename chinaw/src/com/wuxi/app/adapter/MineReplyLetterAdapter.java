/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MineReplyLetterAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 政民互动 我的政民互动 12345 我的回信 列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-14 上午11:14:05
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
 * @类名： MineReplyLetterAdapter
 * @描述： 政民互动 我的政民互动 12345 我的回信 列表适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-10-14 上午11:14:05
 * @修改时间：
 * @修改描述：
 */
public class MineReplyLetterAdapter extends BaseAdapter {

	private List<Letter> letters;

	private Context context;

	/**
	 * @方法： MineReplyLetterAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param letters
	 */
	public MineReplyLetterAdapter(Context context, List<Letter> letters) {
		this.context = context;
		this.letters = letters;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param letter
	 */
	public void addItem(Letter letter) {
		this.letters.add(letter);
	}

	/**
	 * @param letters
	 *            要设置的 letters
	 */
	public void setLetters(List<Letter> letters) {
		this.letters = letters;
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
		public TextView code_text;
		public TextView answerDate_text;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_mine_12345_listview_item, null);

			System.out.println("初始化控件");

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.gip_mine_12345_textView_title);
			viewHolder.code_text = (TextView) convertView
					.findViewById(R.id.gip_mine_12345_textView_code);
			viewHolder.answerDate_text = (TextView) convertView
					.findViewById(R.id.gip_mine_12345_textView_reply);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		System.out.println("绑定数据");

		String title_str = (position + 1) + "." + "["
				+ letters.get(position).getType() + "]"
				+ letters.get(position).getTitle();
		viewHolder.title_text.setText(title_str);
		viewHolder.code_text.setText(letters.get(position).getCode());

		viewHolder.answerDate_text.setText(letters.get(position)
				.getAnswerdate());

		return convertView;
	}

}

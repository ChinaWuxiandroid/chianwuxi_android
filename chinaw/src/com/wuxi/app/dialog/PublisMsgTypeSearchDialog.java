package com.wuxi.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.PublicMsgTypeActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailXKActivity;
import com.wuxi.app.util.MenuItemChannelIndexUtil;
import com.wuxi.domain.PublicMsgTypeSearchFilter;

/**
 * 政府信息公开目录分类查询dialog
 * 
 * @author wanglu 泰得利通
 * 
 */
public class PublisMsgTypeSearchDialog extends Dialog {

	private GridView gv_type_list;
	private Context context;
	private String types[] = new String[] { "计划", "工业", "交通", "能源", "邮电", "旅游",
			"服务业", "环保", "农业", "林业", "水利", "气象", "财政", "金融", "商业", "外贸", "外交",
			"公安", "司法", "监察", "民政", "机构", "人事", "劳动", "工资", "科技", "侨务", "港澳台",
			"综合", "文化", "卫生", "体育", "军事", "外事", "教育", "统战", "民族", "宗教", "城乡建设",
			"经济管理", "文秘工作", "行政事务", "党派团体", "其他"

	};

	private Button type_btn_search;

	private RadioGroup rg_channeltype;
	private int depth=0;
	private String currentChannelId;
	private String searchChannleId;
	private int selectTypeIndex=0;
	private String channelName="政府信息公开";

	public PublisMsgTypeSearchDialog(Context context,String currentChannelId) {
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.public_msg_tye_search);
		gv_type_list = (GridView) findViewById(R.id.gv_type_list);
		rg_channeltype = (RadioGroup) findViewById(R.id.rg_channeltype);
		type_btn_search = (Button) findViewById(R.id.type_btn_search);
		this.context = context;
		this.currentChannelId=currentChannelId;
		searchChannleId=currentChannelId;
		initData();

		radioStyleChangeListner();

		initSearch();

	}

	private void initSearch() {

		type_btn_search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				PublisMsgTypeSearchDialog.this.dismiss();
				
				
				Intent intent=new Intent(context,PublicMsgTypeActivity.class);
				intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 0);
				
				PublicMsgTypeSearchFilter publicMsgTypeSearchFilter=new PublicMsgTypeSearchFilter();
				
				publicMsgTypeSearchFilter.setChannelId(searchChannleId);
				publicMsgTypeSearchFilter.setDept(depth);
				publicMsgTypeSearchFilter.setTypeword(types[selectTypeIndex]);
				publicMsgTypeSearchFilter.setChannelName(channelName);
				intent.putExtra(PublicMsgTypeActivity.INTENT_FILETER_KEY, publicMsgTypeSearchFilter);
			
				MainTabActivity.instance.addView(intent);

			}
		});
	}

	private void radioStyleChangeListner() {

		RadioButton radioButton = (RadioButton) rg_channeltype.getChildAt(0);
		radioButton.setTextColor(Color.BLACK);
		radioButton.setBackgroundColor(Color.WHITE);// 默认第一个选择样式

		rg_channeltype
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						for (int i = 0; i < group.getChildCount(); i++) {

							RadioButton rb = (RadioButton) group.getChildAt(i);
							if (rb.getId() == checkedId) {
								rb.setTextColor(Color.BLACK);
								rb.setBackgroundColor(Color.WHITE);
								
								if(i==0){//当前目录
									depth=0;
									searchChannleId=currentChannelId;
									channelName="当前栏目";
								}
								
								if(i==1){//公开目录
									depth=2;
									
									searchChannleId="54d5e114-d9b5-4d14-a5f8-78ea88439ce9";
									
									channelName="公开目录";
									
								}
								
								if(i==2){//政策法规
									depth=1;
									searchChannleId="233718ad-5d84-437a-a4e8-d6df6da86ac0";
									channelName="政策法规";
								}
								
							} else {
								rb.setTextColor(Color.WHITE);
								rb.setBackgroundColor(Color.TRANSPARENT);

							}
							
							
							

						}

					}
				});

	}

	private void initData() {
		final TypeAdapter typeAdapter = new TypeAdapter();
		gv_type_list.setAdapter(typeAdapter);

		gv_type_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selectTypeIndex=arg2;
				typeAdapter.setSelectIndex(selectTypeIndex);

				typeAdapter.notifyDataSetChanged();
			}
		});

	}

	private final static class ViewHolder {
		TextView textView;

	}

	private class TypeAdapter extends BaseAdapter {

		private int selectIndex = -1;

		public void setSelectIndex(int selectIndex) {
			this.selectIndex = selectIndex;
		}

		@Override
		public int getCount() {

			return types.length;
		}

		@Override
		public Object getItem(int position) {
			return types[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			String type = types[position];

			ViewHolder viewHolder = null;

			if (convertView == null) {

				TextView tvTextView = (TextView) View.inflate(context,
						R.layout.public_type_item, null);

				convertView = tvTextView;

				viewHolder = new ViewHolder();
				viewHolder.textView = tvTextView;
				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();

			}

			viewHolder.textView.setText("." + type);

			if (selectIndex == position) {
				viewHolder.textView.setBackgroundColor(Color.WHITE);
				viewHolder.textView.setTextColor(Color.BLACK);
			} else {
				viewHolder.textView.setTextColor(Color.WHITE);
				viewHolder.textView.setBackgroundColor(Color.TRANSPARENT);
			}

			return convertView;
		}

	}

}

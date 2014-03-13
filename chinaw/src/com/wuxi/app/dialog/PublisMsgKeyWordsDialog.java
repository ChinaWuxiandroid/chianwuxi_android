package com.wuxi.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.PublicMsgKeyWordsActivity;
import com.wuxi.domain.PublicMsgKeyWordsSearchFilter;

/**
 * 政府信息公开目录分类查询dialog
 * 
 * @author wanglu 泰得利通
 * 
 */
public class PublisMsgKeyWordsDialog extends Dialog {

	private Context context;
	private EditText search_et_keywords;
	private String ranges[] = new String[] { "当前栏目", "政策法规", "信息公开目录"

	};

	private Spinner search_sp_range;
	private Spinner search_sp_type;

	private String types[] = new String[] { "按标题", "按内容", "按标题或内容", "按文号" };
	private Button search_btn_search;
	private String channelId;

	public PublisMsgKeyWordsDialog(Context context, String channelId) {
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.public_msg_keywords_search);

		search_sp_range = (Spinner) findViewById(R.id.search_sp_range);
		search_sp_type = (Spinner) findViewById(R.id.search_sp_type);
		search_btn_search = (Button) findViewById(R.id.search_btn_search);
		search_et_keywords=(EditText) findViewById(R.id.search_et_keywords);
		this.context = context;
		this.channelId = channelId;
		initData();

	}

	private boolean checkInput() {
		if (search_et_keywords.getText().toString().equals("")) {
			Toast.makeText(context, "请输入搜索关键字", Toast.LENGTH_LONG).show();
			return false;
		}

		return true;

	}

	private void initData() {

		search_sp_range.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, ranges));

		search_sp_type.setAdapter(new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, types));

		search_btn_search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkInput()) {

					PublicMsgKeyWordsSearchFilter publicMsgKeyWordsSearchFilter = new PublicMsgKeyWordsSearchFilter();

					switch (search_sp_range.getSelectedItemPosition()) {// 范围
					case 0:
						publicMsgKeyWordsSearchFilter.setDepth(0);
						publicMsgKeyWordsSearchFilter.setChannelName("当前栏目");
						publicMsgKeyWordsSearchFilter.setChannelId(channelId);
						break;
					case 1:
						publicMsgKeyWordsSearchFilter.setChannelName("政策法规");
						publicMsgKeyWordsSearchFilter.setDepth(1);
						publicMsgKeyWordsSearchFilter
								.setChannelId("233718ad-5d84-437a-a4e8-d6df6da86ac0");

						break;
					case 2:
						publicMsgKeyWordsSearchFilter.setChannelName("信息公开目录");
						publicMsgKeyWordsSearchFilter.setDepth(2);
						publicMsgKeyWordsSearchFilter
								.setChannelId("54d5e114-d9b5-4d14-a5f8-78ea88439ce9");

						break;

					}
					String content = search_et_keywords.getText().toString();
					publicMsgKeyWordsSearchFilter.setContent(content);

					switch (search_sp_type.getSelectedItemPosition()) {// {"按标题","按类容","按标题或内容","按文号"}

					case 0:// 标题
						publicMsgKeyWordsSearchFilter.setParamString("title="
								+ content);
						
						publicMsgKeyWordsSearchFilter.setTypeName("按标题");
						break;
					case 1:// 类容
						publicMsgKeyWordsSearchFilter.setParamString("content="
								+ content);
						publicMsgKeyWordsSearchFilter.setTypeName("按内容");
						break;
					case 2:
						publicMsgKeyWordsSearchFilter.setParamString("title="
								+ content + "&content=" + content);
						
						publicMsgKeyWordsSearchFilter.setTypeName("按标题或内容");
						break;
					case 3:
						publicMsgKeyWordsSearchFilter.setParamString("indexId="
								+ content);
						publicMsgKeyWordsSearchFilter.setTypeName("按文号");
						break;

					}

					PublisMsgKeyWordsDialog.this.dismiss();

					Intent intent = new Intent(context,
							PublicMsgKeyWordsActivity.class);
					intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY,
							0);

					intent.putExtra(
							PublicMsgKeyWordsActivity.INTENT_FILETER_KEY,
							publicMsgKeyWordsSearchFilter);

					MainTabActivity.instance.addView(intent);

				}
			}
		});

	}
}

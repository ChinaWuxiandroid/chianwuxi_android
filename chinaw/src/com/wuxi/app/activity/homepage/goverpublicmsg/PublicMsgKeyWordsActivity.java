package com.wuxi.app.activity.homepage.goverpublicmsg;

import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.PublicMsgKeyWordsSearchFilter;
import com.wuxi.domain.PublicMsgTypeSearchFilter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

public class PublicMsgKeyWordsActivity extends BaseItemContentActivity {

	private ListView public_type_search_layout;
	public static final String INTENT_FILETER_KEY = "publicMsgKeywordsSearchFilter";

	@Override
	public void onClick(View v) {

		super.onClick(v);
		switch (v.getId()) {
		case R.id.loadMoreButton:
			loadData();

			loadMoreButton.setText("loading.....");
			pb_loadmoore.setVisibility(View.VISIBLE);
			break;
		}

	}

	private PublicMsgKeyWordsSearchFilter filter;
	private ProgressBar pb_content;
	private ContentService contentService;
	private static int PAGE_SIZE = 10;
	private int start = 0;

	private ContentWrapper contentWrapper;
	private static final int LOADSUCCESS = 1;
	protected static final int LOADFIAL = 0;
	private boolean isFisrLoad = true;
	private View loadMoreView;
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;
	private ContentListAdapter contentListAdapter;
	private List<Content> contents;
	private TextView search_tv_range, search_tv_type, search_tv_keywords;
	private Handler mHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADSUCCESS:
				pb_content.setVisibility(View.GONE);
				showData();
				break;

			case LOADFIAL:
				Toast.makeText(PublicMsgKeyWordsActivity.this, "获取数据失败",
						Toast.LENGTH_SHORT).show();
				break;

			}

		};
	};

	/* (non-Javadoc)
	 * @see com.wuxi.app.activity.BaseItemContentActivity#findMainContentViews(android.view.View)
	 */
	@Override
	protected void findMainContentViews(View view) {

		super.findMainContentViews(view);
		rl_down.setVisibility(RelativeLayout.GONE);
		rl_setting.setVisibility(RelativeLayout.GONE);
		rl_search_share.setVisibility(RelativeLayout.GONE);
		public_type_search_layout = (ListView) view
				.findViewById(R.id.public_type_search_layout);
		pb_content = (ProgressBar) view.findViewById(R.id.pb_content);
		search_tv_range=(TextView) view.findViewById(R.id.search_tv_range);
		search_tv_type=(TextView) view.findViewById(R.id.search_tv_type);
		search_tv_keywords=(TextView) view.findViewById(R.id.search_tv_keywords);
		
		pb_content.setVisibility(View.GONE);

		filter = (PublicMsgKeyWordsSearchFilter) getIntent().getExtras().get(
				INTENT_FILETER_KEY);

		contentService = new ContentService(this);

		loadMoreView = View.inflate(this, R.layout.list_loadmore_layout, null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		public_type_search_layout.addFooterView(loadMoreView);

		loadMoreButton.setOnClickListener(this);
		public_type_search_layout
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						Content content = contents.get(arg2);
						Intent intent = new Intent(
								PublicMsgKeyWordsActivity.this,
								GoverMsgContentDetailWebActivity.class);
						intent.putExtra("url", content.getWapUrl());
						intent.putExtra("fragmentTitle",
								filter.getChannelName());

						Animation animation = AnimationUtils.loadAnimation(
								PublicMsgKeyWordsActivity.this,
								R.anim.rbm_in_from_right);
						MainTabActivity.instance.addView(intent, animation);

					}
				});

		loadData();
		
		
		search_tv_range.setText(search_tv_range.getText().toString()+filter.getChannelName());
		search_tv_type.setText(search_tv_type.getText().toString()+filter.getTypeName());
		search_tv_keywords.setText(search_tv_keywords.getText().toString()+filter.getContent());

	}

	protected void showData() {

		contents = contentWrapper.getContents();
		if (contents != null && contents.size() > 0) {
			if (isFisrLoad) {// 首次加载
				contentListAdapter = new ContentListAdapter(contents, this);
				isFisrLoad = false;
				public_type_search_layout.setAdapter(contentListAdapter);

			} else {

				for (Content content : contents) {
					contentListAdapter.addItem(content);
				}
				pb_loadmoore.setVisibility(View.GONE);
				contentListAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter

			}

		} else {
			Toast.makeText(this, "没有查询到数据", Toast.LENGTH_SHORT).show();
		}

		if (contentWrapper.isNext()) {
			loadMoreButton.setText("点击加载更多");
			pb_loadmoore.setVisibility(ProgressBar.GONE);

		} else {
			if (contentListAdapter != null) {
				public_type_search_layout.removeFooterView(loadMoreView);
			}

		}

	}

	private void loadData() {

		if (isFisrLoad) {

			pb_content.setVisibility(View.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = mHandler.obtainMessage();

				try {

					contentWrapper = contentService
							.getPageContentsByUrl(getURL());

					if (contentWrapper != null) {

						start = start + PAGE_SIZE;

						msg.what = LOADSUCCESS;

					} else {
						msg.what = LOADFIAL;
					}

					mHandler.sendMessage(msg);
				} catch (NetException e) {

					e.printStackTrace();
				} catch (JSONException e) {

					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private String getURL() {

		StringBuffer sb = new StringBuffer(
				"http://3g.wuxi.gov.cn/api/content/query.json?");

		sb.append("depth=" + filter.getDepth()).append("&")
				.append("start=" + start).append("&")
				.append("end=" + (start + PAGE_SIZE)).append("&")
				.append(filter.getParamString()).append("&")
				.append("channelId=" + filter.getChannelId());

		return sb.toString();

	}

	@Override
	protected int getContentLayoutId() {

		return R.layout.public_msg_search_keywords__layout;
	}

	@Override
	protected String getContentTitleText() {

		return "政府信息公开目录";
	}

}

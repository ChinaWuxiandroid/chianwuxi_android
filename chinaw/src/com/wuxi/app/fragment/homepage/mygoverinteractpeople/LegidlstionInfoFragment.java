/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.LegislationContentService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.NoticePostWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 征求意见平台 立法征求意见 详情界面
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class LegidlstionInfoFragment extends BaseFragment {

	private static final String TAG = "LegidlstionInfoFragment";

	private View view = null;
	private Context context = null;

	private TextView sentpepole_text = null;
	private TextView begintime_text = null;
	private TextView endtime_text = null;
	private TextView readnum_text = null;
	private TextView replynum_text = null;
	private TextView content_text = null;
	private TextView title_text = null;

	private WebView post_WebView = null;

	private ScrollView post_scrollview = null;

	private ProgressBar progressBar = null;

	private LinearLayout layout = null;

	private Politics politics = null;

	private NoticePostWrapper noticePostWrapper = null;
	private NoticePostReplyWrapper noticePostReplyWrapper = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private TextView post_summary_text = null;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				progressBar.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);
				setLayoutValue();
				break;

			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	/**
	 * @param politics
	 *            the politics to set
	 */
	public void setPolitics(Politics politics) {
		this.politics = politics;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forum_ordinary_content_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		layout = (LinearLayout) view.findViewById(R.id.ordinary_linearlayout);
		layout.setVisibility(View.GONE);

		progressBar = (ProgressBar) view
				.findViewById(R.id.ordinary_progressbar);
		progressBar.setVisibility(View.VISIBLE);

		sentpepole_text = (TextView) view
				.findViewById(R.id.ordinary_sentpepole_text);
		begintime_text = (TextView) view
				.findViewById(R.id.ordinary_begintime_text);
		endtime_text = (TextView) view.findViewById(R.id.ordinary_endtime_text);

		readnum_text = (TextView) view.findViewById(R.id.ordinary_readnum_text);
		// post_readnum = (TextView) view.findViewById(R.id.post_readnum);

		replynum_text = (TextView) view
				.findViewById(R.id.ordinary_replynum_text);

		content_text = (TextView) view.findViewById(R.id.ordinary_content_text);
		title_text = (TextView) view.findViewById(R.id.ordinary_title_text);
		// post_bengintime = (TextView) view.findViewById(R.id.post_bengintime);
		// post_endtime = (TextView) view.findViewById(R.id.post_endtime);

		post_WebView = (WebView) view.findViewById(R.id.post_webview);
		post_WebView.getSettings().setJavaScriptEnabled(true);
		post_WebView.getSettings().setDefaultTextEncodingName("utf-8");
		post_WebView.getSettings().setBuiltInZoomControls(true);
		post_WebView.getSettings().setTextSize(TextSize.SMALLER);

		post_scrollview = (ScrollView) view.findViewById(R.id.post_scrollview);

		post_summary_text = (TextView) view
				.findViewById(R.id.post_summary_text);

		loadData();

	}

	/**
	 * @方法： setLayoutValue
	 * @描述： 设置相关控件的值
	 */
	private void setLayoutValue() {

		sentpepole_text.setText("版主");
		post_summary_text.setText("\u3000\u3000"
				+ noticePostWrapper.getSummary());

		begintime_text.setText(noticePostWrapper.getBegintime());
		endtime_text.setText(noticePostWrapper.getEndtime());
		readnum_text.setText(noticePostWrapper.getReadCount());
		replynum_text.setText(String.valueOf(noticePostReplyWrapper
				.getTotalRowsAmount()));
		title_text.setText(noticePostWrapper.getTitle());

		content_text.setVisibility(View.GONE);
		post_scrollview.setVisibility(View.GONE);

		post_WebView.setVisibility(View.VISIBLE);
		post_WebView.loadData(noticePostWrapper.getContent(),
				"text/html; charset=UTF-8", null);
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					LegislationContentService legislationContentService = new LegislationContentService(
							context);
					noticePostWrapper = legislationContentService
							.getNoticePostWrapper(politics.getId());

					if (noticePostWrapper != null) {
						noticePostReplyWrapper = noticePostWrapper
								.getNoticePostReplyWrapper();
						handler.sendEmptyMessage(DATA__LOAD_SUCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

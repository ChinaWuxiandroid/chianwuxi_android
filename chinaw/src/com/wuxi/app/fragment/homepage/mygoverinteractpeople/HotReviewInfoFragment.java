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
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.HotReviewInfoService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.HotReviewInfoWrapper;
import com.wuxi.domain.HotReviewWrapper.HotReview;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 热点话题 单个话题内容 界面碎片
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressWarnings("deprecation")
public class HotReviewInfoFragment extends BaseFragment {

	private static final String TAG = "HotReviewInfoFragment";

	private View view = null;
	private Context context = null;

	private TextView title = null;
	private WebView content = null;
	private TextView bengintime = null;
	private TextView endtime = null;
	private TextView deptname = null;
	private TextView readnum = null;

	private LinearLayout layout = null;

	private ProgressBar progressBar = null;

	private HotReview hotReview;

	private HotReviewInfoWrapper infoWrapper = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.hot_review_info_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initLayout() {
		layout = (LinearLayout) view.findViewById(R.id.hotreview_layout);
		layout.setVisibility(View.GONE);

		progressBar = (ProgressBar) view
				.findViewById(R.id.hotreview_progressbar);
		progressBar.setVisibility(View.VISIBLE);

		content = (WebView) view.findViewById(R.id.hotrevire_info_webview);
		content.getSettings().setJavaScriptEnabled(true);
		content.getSettings().setDefaultTextEncodingName("utf-8");
		content.getSettings().setBuiltInZoomControls(true);
		content.getSettings().setTextSize(TextSize.SMALLER);

		title = (TextView) view.findViewById(R.id.hotrevire_info_title_text);
		bengintime = (TextView) view
				.findViewById(R.id.hotreview_info_begintime_text);
		endtime = (TextView) view
				.findViewById(R.id.hotreview_info_endtime_text);
		deptname = (TextView) view
				.findViewById(R.id.hotreview_info_deptname_text);
		readnum = (TextView) view
				.findViewById(R.id.hotreview_info_readcount_text);

		loadData();
	}

	/**
	 * 设置界面显示数据
	 */
	private void setLayoutValue() {
		title.setText(infoWrapper.getTitle());
		content.loadData(infoWrapper.getContent(), "text/html; charset=UTF-8",
				null);
		bengintime.setText(hotReview.getStartTime());
		endtime.setText(infoWrapper.getEndTime());
		deptname.setText(infoWrapper.getDepName());
		readnum.setText(hotReview.getReadcount());

	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					HotReviewInfoService infoService = new HotReviewInfoService(
							context);

					infoWrapper = infoService.getHotReviewInfoWrapper(hotReview
							.getId());

					if (infoWrapper != null) {

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

	/**
	 * @param hotReview
	 *            the hotReview to set
	 */
	public void setHotReview(HotReview hotReview) {
		this.hotReview = hotReview;
	}

}

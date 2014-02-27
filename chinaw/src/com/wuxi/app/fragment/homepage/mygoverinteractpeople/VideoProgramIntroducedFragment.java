/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VideoProgramIntroducedFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述: 政民互动 视频直播平台 走进直播间 节目介绍 界面
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-25 上午10:56:36
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Content;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： VideoProgramIntroducedFragment
 * @描述： 政民互动 视频直播平台 走进直播间 节目介绍 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-25 上午10:56:36
 * @修改时间：
 * @修改描述：
 */
@SuppressLint("SetJavaScriptEnabled")
public class VideoProgramIntroducedFragment extends BaseFragment {

	private static final String TAG = "VideoProgramIntroducedFragment";

	// 数据加载成功标志
	private static final int DATA_LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private Context context;
	private View view;

	private TextView titleTxt = null;
	private TextView timeTxt = null;
	private TextView readnumTxt = null;
	private WebView webView = null;

	private LinearLayout linearLayout = null;

	private ProgressBar progressBar = null;

	private List<Content> contents;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA_LOAD_SUCESS:

				showData();
				progressBar.setVisibility(View.GONE);
				linearLayout.setVisibility(View.VISIBLE);
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
		view = inflater.inflate(R.layout.video_program_introduced_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		linearLayout = (LinearLayout) view
				.findViewById(R.id.video_program_introduced_layout);

		progressBar = (ProgressBar) view
				.findViewById(R.id.video_program_introduced_progressbar);

		titleTxt = (TextView) view
				.findViewById(R.id.video_program_introduced_title);
		timeTxt = (TextView) view
				.findViewById(R.id.video_program_introduced_begintime);
		readnumTxt = (TextView) view
				.findViewById(R.id.video_program_introduced_readnum);

		webView = (WebView) view
				.findViewById(R.id.video_program_introduced_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setBuiltInZoomControls(true);

		loadData();
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String id = "4242dba1-3b51-4124-aa7a-b3557f81334f";
				Message message = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
					contents = contentService.getContentsById(id);
					if (contents != null || contents.size() > 0) {
						handler.sendEmptyMessage(DATA_LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);
				} catch (JSONException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);
				} catch (NODataException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);
				}

			}
		}).start();
	}

	/**
	 * @方法： showData
	 * @描述： 显示数据
	 */
	private void showData() {
		for (int i = 0; i < contents.size(); i++) {
			titleTxt.setText(contents.get(i).getTitle());
			timeTxt.setText(TimeFormateUtil.formateTime(contents.get(i)
					.getPublishTime(), TimeFormateUtil.DATE_PATTERN));
			readnumTxt
					.setText(String.valueOf(contents.get(i).getBrowseCount()));

			webView.loadData(contents.get(i).getDocSummary(),
					"text/html; charset=UTF-8", null);
		}
	}
}

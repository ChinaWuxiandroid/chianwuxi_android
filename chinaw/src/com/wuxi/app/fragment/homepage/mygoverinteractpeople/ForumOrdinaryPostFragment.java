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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.HotPostService;
import com.wuxi.app.engine.OrdinaryPostService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.ForumWrapper.Forum;
import com.wuxi.domain.HotPostWrapper;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper;
import com.wuxi.domain.OrdinaryPostWrapper;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公众论坛 帖子详细内容 普通帖子界面碎片
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressLint({ "HandlerLeak", "SetJavaScriptEnabled" })
public class ForumOrdinaryPostFragment extends BaseFragment {

	private static final String TAG = "ForumOrdinaryPostFragment";

	private View view = null;
	private Context context = null;

	private TextView sentpepole_text = null;
	private TextView begintime_text = null;
	private TextView endtime_text = null;
	private TextView readnum_text = null;
	private TextView replynum_text = null;
	private TextView content_text = null;
	private TextView title_text = null;
	private TextView post_bengintime = null;
	private TextView post_endtime = null;
	private TextView post_readnum = null;
	
	private WebView post_WebView = null;
	
	private ScrollView post_scrollview = null;
	
	private ProgressBar progressBar = null;

	private LinearLayout layout = null;

	private Forum forum;

	private OrdinaryPostWrapper postWrapper = null;
	private OrdinaryPostRaplyWrapper postRaplyWrappers = null;

	private HotPostWrapper hotPostWrapper = null;
	private HotPostReplyWrapper hotPostReplyWrapper = null;
	
	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取帖子回复的起始坐标
	private int startIndex = 0;
	// 获取帖子回复的结束坐标
	private int endIndex = 60;

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
	 * @param forum
	 *            the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
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
		post_readnum = (TextView) view.findViewById(R.id.post_readnum);
		
		replynum_text = (TextView) view
				.findViewById(R.id.ordinary_replynum_text);
		
		content_text = (TextView) view.findViewById(R.id.ordinary_content_text);
		title_text = (TextView) view.findViewById(R.id.ordinary_title_text);
		post_bengintime = (TextView) view.findViewById(R.id.post_bengintime);
		post_endtime = (TextView) view.findViewById(R.id.post_endtime);

		post_WebView = (WebView) view.findViewById(R.id.post_webview);
		post_WebView.getSettings().setJavaScriptEnabled(true);
		post_WebView.getSettings().setDefaultTextEncodingName("utf-8") ;
		post_WebView.setClickable(false);
		post_WebView.getSettings().setUseWideViewPort(true);
		post_WebView.getSettings().setBuiltInZoomControls(true);

		post_scrollview = (ScrollView) view.findViewById(R.id.post_scrollview);
		
		loadData();

	}

	/**
	 * 设置界面显示数据
	 */
	private void setLayoutValue() {
		// 普通帖子数据显示
		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			sentpepole_text.setText(postWrapper.getSentUser());
			begintime_text.setVisibility(View.GONE);
			post_bengintime.setVisibility(View.GONE);
			endtime_text.setVisibility(View.GONE);
			post_endtime.setVisibility(View.GONE);
			readnum_text.setText(postWrapper.getReadCount());
			replynum_text.setText(String.valueOf(postRaplyWrappers
					.getTotalRowsAmount()));
			title_text.setText(postWrapper.getTitle());
			content_text.setText("\u3000\u3000" + postWrapper.getContent());
		}
		//热点话题类帖子数据显示
		else if (forum.getViewpath().equals("/HotReviewContent")) {
			if (hotPostWrapper.getDepName().equals("null")) {
				sentpepole_text.setText("版主");
			}else{
				sentpepole_text.setText(hotPostWrapper.getDepName());
			}
			
			begintime_text.setVisibility(View.GONE);
			post_bengintime.setVisibility(View.GONE);
			
			post_endtime.setVisibility(View.VISIBLE);
			endtime_text.setText(hotPostWrapper.getEndTime());
			
			readnum_text.setVisibility(View.GONE);
			post_readnum.setVisibility(View.GONE);
			
			
			replynum_text.setText(String.valueOf(hotPostReplyWrapper.getTotalRowsAmount()));
			title_text.setText(hotPostWrapper.getTitle());
			content_text.setVisibility(View.GONE);
			post_scrollview.setVisibility(View.GONE);
			
			post_WebView.setVisibility(View.VISIBLE);
			post_WebView.loadData(hotPostWrapper.getContent(), "text/html; charset=UTF-8",null);
		}
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					//普通帖子数据加载
					if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
						OrdinaryPostService postService = new OrdinaryPostService(
								context);

						postWrapper = postService.getOrdinaryPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								endIndex);

						if (postWrapper != null) {
							postRaplyWrappers = postWrapper.getRaplyWrapper();

							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					//热点话题类帖子数据加载
					else if (forum.getViewpath().equals("/HotReviewContent")) {
						HotPostService hotPostService = new HotPostService(context);
						hotPostWrapper = hotPostService.getHotPostWrapper(forum.getId(), forum.getViewpath(), startIndex, endIndex);
						if (hotPostWrapper != null) {
							hotPostReplyWrapper = hotPostWrapper.getHotPostReplyWrapper();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						}else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}			
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

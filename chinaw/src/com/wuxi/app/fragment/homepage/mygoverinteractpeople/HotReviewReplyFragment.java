/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.HotReviewReplayService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.HotReviewReplyWrapper;
import com.wuxi.domain.HotReviewReplyWrapper.HotReviewReply;
import com.wuxi.domain.HotReviewWrapper.HotReview;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 热点话题 网友留言与回复 碎片界面
 * 
 * @author 智佳 罗森
 * 
 */
public class HotReviewReplyFragment extends BaseFragment {

	private static final String TAG = "HotReviewInfoFragment";

	private View view = null;
	private Context context = null;

	private HotReview hotReview;

	private ListView listView = null;

	private ProgressBar progressBar = null;

	private HotReviewReplyWrapper replyWrapper = null;
	private List<HotReviewReply> hotReviewReplies = null;

	private int start = 0;
	private int end = 20;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				progressBar.setVisibility(View.GONE);
				showHotReviewReply();
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
		view = inflater.inflate(R.layout.hot_review_reply_layout, null);
		context = getActivity();
		
		initLayout();
		
		return view;
	}

	/**
	 * @param hotReview
	 *            the hotReview to set
	 */
	public void setHotReview(HotReview hotReview) {
		this.hotReview = hotReview;
	}
	
	/**
	 * 初始化布局控件
	 */
	private void initLayout(){
		listView = (ListView) view.findViewById(R.id.hot_review_reply_listview);
		
		progressBar = (ProgressBar) view.findViewById(R.id.hot_review_reply_progressbar);
		progressBar.setVisibility(View.VISIBLE);
		
		loadData();
	}
	
	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HotReviewReplayService replayService = new HotReviewReplayService(context);

				try {
					replyWrapper = replayService
							.getHotReviewReplyWrapper(hotReview.getId(), start, end);
					if (replyWrapper != null) {
						hotReviewReplies = replyWrapper.getHotReviewReplies();
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
	 * 显示列表
	 */
	private void showHotReviewReply() {

		HotReviewReplyListAdapter forumListAdapter = new HotReviewReplyListAdapter();

		if (hotReviewReplies == null || hotReviewReplies.size() == 0) {
			Toast.makeText(context, "对不起，暂无该话题回复信息", 2000).show();
		} else {
			listView.setAdapter(forumListAdapter);
		}

	}

	private class HotReviewReplyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return hotReviewReplies.size();
		}

		@Override
		public Object getItem(int position) {
			return hotReviewReplies.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView content;
			public TextView senduser;
			public TextView sendtime;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.hot_review_reply_list_layout, null);
				holder = new ViewHolder();

				holder.content = (TextView) convertView
						.findViewById(R.id.hot_review_reply_content_text);
				holder.senduser = (TextView) convertView
						.findViewById(R.id.hot_review_reply_senduser_text);
				holder.sendtime = (TextView) convertView
						.findViewById(R.id.hot_review_sendtime_text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.content.setText(hotReviewReplies.get(position).getContent());
			holder.senduser.setText(hotReviewReplies.get(position).getSenduser());
			holder.sendtime.setText(hotReviewReplies.get(position).getSendtime());
			
			return convertView;
		}

	}

}

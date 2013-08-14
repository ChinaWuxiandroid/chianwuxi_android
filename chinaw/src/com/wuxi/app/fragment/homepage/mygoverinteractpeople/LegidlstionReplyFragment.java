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
import com.wuxi.app.engine.LegislationContentService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.NoticePostWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 征求意见平台 立法征求意见 详情回复界面
 * 
 * @author 智佳 罗森
 * 
 */
public class LegidlstionReplyFragment extends BaseFragment {

	private static final String TAG = "LegidlstionReplyFragment";

	private NoticePostWrapper noticePostWrapper = null;
	private NoticePostReplyWrapper noticePostReplyWrapper = null;
	private List<NoticePostReply> noticePostReplies = null;

	private View view = null;
	private Context context = null;

	private ProgressBar progressBar = null;

	private ListView listView = null;

	private Politics politics = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	/**
	 * @param politics
	 *            the politics to set
	 */
	public void setPolitics(Politics politics) {
		this.politics = politics;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				progressBar.setVisibility(View.GONE);
				showReplayList();
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
		view = inflater.inflate(R.layout.forum_ordinary_replay_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		listView = (ListView) view.findViewById(R.id.ordinary_replay_listview);

		progressBar = (ProgressBar) view
				.findViewById(R.id.ordinary_replay_progressbar);
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

				try {

					LegislationContentService legislationContentService = new LegislationContentService(
							context);
					noticePostWrapper = legislationContentService
							.getNoticePostWrapper(politics.getId());
					if (noticePostWrapper != null) {
						noticePostReplyWrapper = noticePostWrapper
								.getNoticePostReplyWrapper();
						noticePostReplies = noticePostReplyWrapper
								.getNoticePostReplies();
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
	 * 显示回复数据列表
	 */
	private void showReplayList() {

		OrdinaryReplayListAdapter ordinaryReplayListAdapter = new OrdinaryReplayListAdapter();

		if (noticePostReplies == null || noticePostReplies.size() == 0) {
			Toast.makeText(context, "对不起，暂无该论坛的回复信息", 2000).show();
		} else {
			listView.setAdapter(ordinaryReplayListAdapter);
		}

	}

	/**
	 * 内部类，普通帖子回复列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class OrdinaryReplayListAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return noticePostReplies.size();
		}

		@Override
		public Object getItem(int position) {

			return noticePostReplies.get(position);

		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView replay_name_text;
			public TextView replay_time_text;
			public TextView replay_content_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = LayoutInflater.from(context).inflate(
						R.layout.legidlstion_reply_list_layout, null);

				holder.replay_name_text = (TextView) convertView
						.findViewById(R.id.reply_name_text);
				holder.replay_time_text = (TextView) convertView
						.findViewById(R.id.reply_time_text);
				holder.replay_content_text = (TextView) convertView
						.findViewById(R.id.reply_content_text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.replay_name_text.setText(noticePostReplies.get(position)
					.getUserName());
			holder.replay_time_text.setText(noticePostReplies.get(position)
					.getSentTime());
			holder.replay_content_text.setText(noticePostReplies.get(position)
					.getContent());
			return convertView;
		}

	}

}

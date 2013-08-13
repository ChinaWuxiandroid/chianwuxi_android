package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.ForumService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.ForumWrapper;
import com.wuxi.domain.ForumWrapper.Forum;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 之 公众论坛模块
 * 
 * @author 杨宸 智佳
 * */

@SuppressLint({ "HandlerLeak", "ShowToast" })
public class GoverInterPeoplePublicForumFragment extends
		RadioButtonChangeFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected static final String TAG = "GoverInterPeoplePublicForumFragment";

	// 贴子列表
	private ListView mListView;
	// 数据刷新进度条
	private ProgressBar list_pb;
	// 我要发帖按钮
	private ImageButton postButton;

	// 论坛包装类对象
	private ForumWrapper forumWrapper;

	// 论坛列表
	private List<ForumWrapper.Forum> forums;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取帖子的起始坐标
	private int startIndex = 0;
	// 获取帖子的结束坐标
	private int endIndex = 200;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				list_pb.setVisibility(View.GONE);
				showForums();
				break;

			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_publicforum_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return 0;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return null;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		mListView = (ListView) view.findViewById(R.id.gip_forum_listview);

		list_pb = (ProgressBar) view.findViewById(R.id.gip_forum_listview_pb);
		list_pb.setVisibility(View.VISIBLE);
		
		postButton = (ImageButton) view.findViewById(R.id.gip_forum_imagebtn);
		postButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseSlideFragment baseSlideFragment = GoverInterPeoplePublicForumFragment.this.baseSlideFragment;
				baseSlideFragment.slideLinstener.replaceFragment(null, position,
						Constants.FragmentName.GIP_FORUM_POST_FRAGMENT, null);
			}
		});
		
		loadData();
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ForumService forumService = new ForumService(context);

				try {
					forumWrapper = forumService
							.getForumWrapper(Constants.Urls.FORUM_LIST_URL,
									startIndex, endIndex);
					if (forumWrapper != null) {
						forums = forumWrapper.getForums();
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
	 * 显示论坛列表
	 */
	private void showForums() {
		BaseSlideFragment baseSlideFragment = this.baseSlideFragment;
		ForumListAdapter forumListAdapter = new ForumListAdapter(
				baseSlideFragment);

		if (forums == null || forums.size() == 0) {
			Toast.makeText(context, "对不起，暂无论坛信息", 2000).show();
		} else {
			mListView.setAdapter(forumListAdapter);
			mListView.setOnItemClickListener(forumListAdapter);
		}

	}

	/**
	 * 
	 * 论坛列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class ForumListAdapter extends BaseAdapter implements
			OnItemClickListener {

		BaseSlideFragment baseSlideFragment;

		/**
		 * 构造方法
		 * 
		 * @param baseSlideFragment
		 */
		public ForumListAdapter(BaseSlideFragment baseSlideFragment) {
			this.baseSlideFragment = baseSlideFragment;
		}

		@Override
		public int getCount() {
			return forums.size();
		}

		@Override
		public Object getItem(int position) {
			return forums.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * 内部类，定义了列表项的布局控件
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		class ViewHolder {
			public TextView title_text;
			public TextView beginTime_text;
			public TextView readCount_text;
			public TextView resultCount_text;
			public TextView sentUser_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.gip_forum_list_item,
						null);

				holder = new ViewHolder();
				holder.title_text = (TextView) convertView
						.findViewById(R.id.gip_forum_list_title);
				holder.beginTime_text = (TextView) convertView
						.findViewById(R.id.gip_forum_begintime_text);
				holder.readCount_text = (TextView) convertView
						.findViewById(R.id.gip_forum_readCount_text);
				holder.resultCount_text = (TextView) convertView
						.findViewById(R.id.gip_forum_resultCount_text);
				holder.sentUser_text = (TextView) convertView
						.findViewById(R.id.gip_forum_sentUser_text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title_text.setText(forums.get(position).getTitle());
			holder.beginTime_text.setText(forums.get(position).getBeginTime());
			holder.readCount_text.setText(forums.get(position).getReadCount());
			holder.resultCount_text.setText(forums.get(position)
					.getResultCount());
			holder.sentUser_text.setText(forums.get(position).getSentUser());

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?>  adapterView, View arg1, int position,
				long arg3) {
			Forum forum=(Forum)adapterView.getItemAtPosition(position);
			Bundle bundle=new Bundle();
			bundle.putSerializable("forum", forum);
			
			baseSlideFragment.slideLinstener.replaceFragment(null, position,
					Constants.FragmentName.GIP_FOROUM_FRAGMENT, bundle);
		}
	}

}

/**
 * 
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.MemoirService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.MemoirWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 栏目首页中访谈实录碎片布局
 * 
 * @author 智佳 罗森
 * @time 2013年8月8日 20:42
 * 
 */
@SuppressLint({ "ShowToast", "HandlerLeak" })
public class LiveHomeMemoirFragment extends BaseFragment {

	protected static final String TAG = "LiveHomeMemoirFragment";

	private View view = null;

	private Context context = null;

	private ProgressBar memoirBar = null;

	private ListView memoirListView = null;

	private MemoirWrapper memoirWrapper = null;

	private List<MemoirWrapper.Memoir> memoirs = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取实录的起始坐标
	private int startIndex = 0;
	// 获取实录的结束坐标
	private int endIndex = 10;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				memoirBar.setVisibility(View.GONE);
				showMemoirList();
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

		view = inflater.inflate(R.layout.gip_live_home_memoir_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 加载布局文件
	 */
	private void initLayout() {
		memoirListView = (ListView) view
				.findViewById(R.id.gip_live_home_memoir_listview);

		memoirBar = (ProgressBar) view
				.findViewById(R.id.gip_live_home_memoir_progressbar);
		memoirBar.setVisibility(View.VISIBLE);
		
		loadData();
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				MemoirService memoirService = new MemoirService(context);

				try {
					memoirWrapper = memoirService.getMemoirWrapper(
							"32480e19-76b8-45d9-b7d1-a6c54933f9f7", startIndex,
							endIndex);
					if (memoirWrapper != null) {
						memoirs = memoirWrapper.getMemoirs();
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
	 * 显示实录列表
	 */
	private void showMemoirList() {
//		BaseSlideFragment baseSlideFragment = (BaseSlideFragment) this
//				.getArguments().get("BaseSlideFragment");

		MemoirListAdapter memoirListAdapter = new MemoirListAdapter();

		for (int i = 0; i < memoirs.size(); i++) {
			System.out.println(memoirs.get(i).getAnswerUser());
		}
		
		if (memoirs == null || memoirs.size() == 0) {
			Toast.makeText(context, "对不起，暂无访谈实录信息", 2000).show();
		} else {
			memoirListView.setAdapter(memoirListAdapter);
		}
	}

	/**
	 * 
	 * 内部类，访谈实录列表适配器
	 * 
	 * @author 智佳 罗森
	 * @createtime 2013年8月9日 20:33
	 * 
	 */
	public class MemoirListAdapter extends BaseAdapter {

//		BaseSlideFragment baseSlideFragment;
//
//		/**
//		 * 构造方法
//		 * 
//		 * @param baseSlideFragment
//		 */
//		public MemoirListAdapter(BaseSlideFragment baseSlideFragment) {
//			this.baseSlideFragment = baseSlideFragment;
//		}

		@Override
		public int getCount() {
			return memoirs.size();
		}

		@Override
		public Object getItem(int position) {
			return memoirs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * 内部类，定义了列表的项的布局控件
		 * 
		 * @author 智佳 罗森
		 * @createtime 2013年8月9日 20:36
		 * 
		 */
		class ViewHolder {
			public TextView pepole_text;
			public TextView content_text;
			public TextView time_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.vedio_memoir_list_layout, null);

				holder = new ViewHolder();

				holder.pepole_text = (TextView) convertView
						.findViewById(R.id.memoir_pepole_text);
				holder.content_text = (TextView) convertView
						.findViewById(R.id.memoir_content_text);
				holder.time_text = (TextView) convertView
						.findViewById(R.id.memoir_time_text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if ((memoirs.get(position).getAnswerType()) == 0) {
				holder.pepole_text.setText("["
						+ memoirs.get(position).getAnswerUser() + "：]");
			} else if ((memoirs.get(position).getAnswerType()) == 1) {
				holder.pepole_text.setText("["
						+ memoirs.get(position).getAnswerUser() + "：]");
			}
			holder.content_text.setText(memoirs.get(position).getContent());
			holder.time_text.setText("["
					+ memoirs.get(position).getSubmitTime() + "]");

			return convertView;
		}
	}

}

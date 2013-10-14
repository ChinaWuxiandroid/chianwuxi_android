/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GIPSuggestLawSuggestionSearchFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述:  征求意见平台 立法征求意见 搜索 界面 
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-8 下午4:57:23
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.LegislationContentActivity;
import com.wuxi.app.adapter.SuggestLawSuggestionAdapter;
import com.wuxi.app.engine.PoliticsService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.domain.SuggestLawSearchCon;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GIPSuggestLawSuggestionSearchFragment
 * @描述： 征求意见平台 立法征求意见 搜索 界面 
 * @作者： 罗森
 * @创建时间： 2013 2013-10-8 下午4:57:23
 * @修改时间：
 * @修改描述：
 */
public class GIPSuggestLawSuggestionSearchFragment extends BaseFragment
		implements OnItemClickListener, OnClickListener, OnScrollListener {

	private static final String TAG = "GIPSuggestLawSuggestionSearchFragment";

	private View view = null;
	private Context context = null;

	private ListView mListView = null;
	private ProgressBar list_pb = null;

	private PoliticsWrapper politicsWrapper;
	private List<Politics> politicss;

	private static final int DATA_LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	public final int POLITICS_TYPE = 0; // politics类型，接口里0 为立法征集，1 为民意征集
	private int startIndex = 0; // 获取话题的起始坐标

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private SuggestLawSuggestionAdapter adapter = null;

	private SuggestLawSearchCon searchCon;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				showPoloticsList();
				break;
			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.content_list_layout, null);

		context = getActivity();

		initLayout();

		loadFirstData(startIndex, PAGE_NUM);

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view.findViewById(R.id.content_list_lv);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view.findViewById(R.id.content_list_pb);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);

		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	/**
	 * @方法： getUrl
	 * @描述： 获取URL
	 * @param con
	 * @return
	 */
	private String getUrl(SuggestLawSearchCon con) {
		// 构建立法征求意见数据查询URL
		String url = Constants.Urls.POLITICS_LIST_URL + "?type="
				+ POLITICS_TYPE + "&start=" + con.getStart() + "&end="
				+ con.getEnd() + "&year=" + con.getYear();

		return url;
	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载立法征求意见数据
	 * @param url
	 */
	private void loadData(final int start, final int end) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				PoliticsService politicsService = new PoliticsService(context);

				searchCon.setStart(start);
				searchCon.setEnd(end);

				try {
					politicsWrapper = politicsService
							.getPoliticsWrapper(getUrl(searchCon));
					if (null != politicsWrapper) {
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
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @方法： showPoloticsList
	 * @描述： 显示立法征求意见列表
	 */
	private void showPoloticsList() {
		politicss = politicsWrapper.getData();
		if (politicss == null || politicss.size() == 0) {
			Toast.makeText(context, "对不起，暂无立法征求意见信息", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (isFirstLoad) {
				adapter = new SuggestLawSuggestionAdapter(context, politicss);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setPolitics(politicss);
					list_pb.setVisibility(View.GONE);
				} else {
					for (Politics pol : politicss) {
						adapter.addItem(pol);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (politicsWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			mListView.removeFooterView(loadMoreView);
		}
	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (politicsWrapper != null && politicsWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Politics politics = (Politics) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(),
				LegislationContentActivity.class);
		intent.putExtra("politics", politics);

		MainTabActivity.instance.addView(intent);
	}

	/**
	 * @param searchCon
	 *            要设置的 searchCon
	 */
	public void setSearchCon(SuggestLawSearchCon searchCon) {
		this.searchCon = searchCon;
	}

}

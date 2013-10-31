package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.LegislationContentActivity;
import com.wuxi.app.adapter.SuggestLawSuggestionAdapter;
import com.wuxi.app.engine.PoliticsService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.domain.SuggestLawSearchCon;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment 之 征求意见平台 子fragment --立法征求意见
 * 
 * @author 杨宸 智佳
 * */

public class GIPSuggestLawSuggestionFragment extends BaseFragment implements
		OnClickListener {

	private static final String TAG = "GIPSuggestLawSuggestionFragment";

	private View view = null;
	private Context context = null;

	private Spinner chooseYear_spinner = null;

	private ListView mListView = null;
	private ProgressBar list_pb = null;

	private PoliticsWrapper politicsWrapper;
	private List<Politics> politicss;

	private static final int DATA_LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	private static final int POLITICS_TYPE = 0; // politics类型，接口里0 为立法征集，1 为民意征集
	private static final int START = 0; // 获取话题的起始坐标

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

	private String[] years = { "2013", "2012", "2011", "2010" };

	private SuggestLawSearchCon searchCon = new SuggestLawSearchCon();

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
				list_pb.setVisibility(View.INVISIBLE);
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
		view = inflater
				.inflate(R.layout.gip_suggest_lawsuggestion_layout, null);

		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 * @param view
	 */
	private void initLayout() {
		// 初始化年份下拉框控件变量
		chooseYear_spinner = (Spinner) view
				.findViewById(R.id.gip_suggest_lawsuggest_spinner_chooseYear);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_suggest_lawsuggest_listView_poloticsList_pb);

		mListView = (ListView) view
				.findViewById(R.id.gip_suggest_lawsuggest_listView);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				Politics politics = (Politics) adapterView
						.getItemAtPosition(position);

				Intent intent = new Intent(getActivity(),
						LegislationContentActivity.class);
				intent.putExtra("politics", politics);

				MainTabActivity.instance.addView(intent);
			}
		});
		mListView.addFooterView(getFootView());// 为listView添加底部视图
		mListView.setOnScrollListener(new LawOnScrollListener());// 增加滑动监听

		// 年份下拉框适配器实例
		MyAryAdapter spinner_adapter = new MyAryAdapter(context,
				android.R.layout.simple_spinner_item, years);
		spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 设置年份下拉框适配器
		chooseYear_spinner.setAdapter(spinner_adapter);
		// 设置年份下拉框选项事件监听
		chooseYear_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						searchCon.setYear(Integer.valueOf(years[position]));
						list_pb.setVisibility(View.VISIBLE);
						isSwitch = true;
						loadFirstData(START, PAGE_NUM);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

	}

	/**
	 * @方法： getUrl
	 * @描述： 获取URL
	 * @param con
	 * @return
	 */
	private String getSearchUrl(SuggestLawSearchCon con) {
		// 构建立法征求意见数据查询URL
		String url = Constants.Urls.POLITICS_LIST_URL + "?type="
				+ POLITICS_TYPE + "&start=" + con.getStart() + "&end="
				+ con.getEnd() + "&year=" + con.getYear();

		return url;
	}

	/**
	 * @方法： getFootView
	 * @描述： 获取列表底部视图
	 * @return
	 */
	private View getFootView() {
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		loadMoreButton.setOnClickListener(this);
		return loadMoreView;
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
							.getPoliticsWrapper(getSearchUrl(searchCon));
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
			Toast.makeText(context,
					"对不起，暂无" + searchCon.getYear() + "年的立法征求意见信息",
					Toast.LENGTH_SHORT).show();
			list_pb.setVisibility(View.GONE);
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
			if (mListView.getFooterViewsCount() != 0) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				mListView.addFooterView(getFootView());
			}

		} else {
			if (adapter != null) {
				mListView.removeFooterView(loadMoreView);
			}

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

	/**
	 * @类名： MyAryAdapter
	 * @描述： 按年份筛选征集主题年份下拉框适配器类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-2 下午4:48:31
	 * @修改时间：
	 * @修改描述：
	 * 
	 */
	private class MyAryAdapter extends ArrayAdapter<String> {

		Context context;
		String[] items = new String[] {};

		public MyAryAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);

			this.items = objects;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);

			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);

			return convertView;
		}

	}

	/**
	 * @类名： LawOnScrollListener
	 * @描述： 分页加载数据监听类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-17 下午3:58:44
	 * @修改时间：
	 * @修改描述：
	 */
	private class LawOnScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemNum, int totalItemCount) {
			visibleItemCount = visibleItemNum;
			visibleLastIndex = firstVisibleItem + visibleItemNum - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		}
	}

}

/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GPMPolicieRegulationSearchFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.goverpublicmsg 
 * @描述: 市政府信息公开目录 政策法规 搜索 界面
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-8 下午4:00:43
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.goverpublicmsg;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.app.adapter.PolicieRegulationAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.domain.PolicieRegulationCon;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GPMPolicieRegulationSearchFragment
 * @描述： 市政府信息公开目录 政策法规 搜索 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-10-8 下午4:00:43
 * @修改时间：
 * @修改描述：
 */
public class GPMPolicieRegulationSearchFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {

	private PolicieRegulationCon regulationCon;

	// 上下文对象
	private Context context;
	// 视图对象
	private View view;

	// 菜单对象
	private MenuItem parentItem;

	// 内容数据包装对象
	private ContentWrapper contentWrapper;// 内容
	// 内容列表对象
	private List<Content> contents;

	// 列表适配器对象
	private PolicieRegulationAdapter adapter;

	// 列表对象
	private ListView mListView;
	// 进度条对象
	private ProgressBar list_pb;

	// 数据加载成功标识
	private static final int DATA_LOAD_SUCESS = 0;
	// 数据加载失败标识
	private static final int DATA_LOAD_ERROR = 1;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				showContentData();
				break;
			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.content_list_layout, null);
		context = getActivity();

		initLayout();

		loadFirstData(0, PAGE_NUM);

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
	private String getUrl(PolicieRegulationCon regulationCon) {

		String url = Constants.Urls.CHANNEL_CONTENT_P_URL
				.replace("{id}", regulationCon.getDept())
				.replace("{start}", String.valueOf(regulationCon.getStart()))
				.replace("{end}", String.valueOf(regulationCon.getEnd()));

		String id = regulationCon.getDept();
		int year = regulationCon.getYear();

		if (id != null && !"".equals(id)) {
			url = url + "&deptid=" + id;
		}
		if (year != -1) {
			url = url + "&year=" + year;
		}
		
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
	 * @描述： 加载数据
	 * @param startIndex
	 * @param endIndex
	 */
	private void loadData(final int startIndex, final int endIndex) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
					
					regulationCon.setStart(startIndex);
					regulationCon.setEnd(endIndex);
					
					contentWrapper = contentService.getPageContentsByUrl(getUrl(regulationCon));
					
					if (contentWrapper != null) {
						msg.what = DATA_LOAD_SUCESS;
					} else {
						msg.what = DATA_LOAD_ERROR;
						msg.obj = "加载办件信息失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();

					msg.what = DATA_LOAD_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();
	}
	
	/**
	 * @方法： showContentData
	 * @描述： 显示数据
	 */
	private void showContentData() {
		contents = contentWrapper.getContents();
		if (contents != null && contents.size() > 0) {
			if (isFirstLoad) {
				adapter = new PolicieRegulationAdapter(contents, context);
				adapter.setMenuItem(parentItem);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(ProgressBar.GONE);
				isLoading = false;
			} else {

				if (isSwitch) {

					adapter.setContents(contents);
					list_pb.setVisibility(ProgressBar.GONE);
				} else {
					for (Content content : contents) {
						adapter.addItem(content);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}

		} else {
			Toast.makeText(context, "对不起，没有该部门的" + parentItem.getName() + "的数据！",
					Toast.LENGTH_SHORT).show();
			list_pb.setVisibility(ProgressBar.GONE);
		}

		if (contentWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");

		} else {
			mListView.removeFooterView(loadMoreView);
		}

	}

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
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View View,
			int position, long arg3) {
		Content content = (Content) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(),
				GoverMsgContentDetailWebActivity.class);
		intent.putExtra("url", content.getWapUrl()+"?backurl=false");
		intent.putExtra("fragmentTitle", parentItem.getName());
		
		MainTabActivity.instance.addView(intent);
	}

	/**
	 * @param parentItem
	 *            要设置的 parentItem
	 */
	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	/**
	 * @param regulationCon
	 *            要设置的 regulationCon
	 */
	public void setRegulationCon(PolicieRegulationCon regulationCon) {
		this.regulationCon = regulationCon;
	}

}

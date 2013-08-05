package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 搜索结果
 * 
 */
public class SearchResultFragment extends BaseFragment implements
		OnScrollListener {

	protected static final int GOVERITEM_LOAD_SUCCESS = 1;
	protected static final int GOVERITEM_LOAD_FIAL = 0;
	private View view;
	private boolean isFirstLoadGoverItem = true;
	private ProgressBar pb_item;
	private Context context;
	private GoverSaoonItemWrapper goverSaoonItemWrapper;
	private Map<String, String> params = null;
	public static final String PARAMS_KEY = "params";
	private static final int PAGE_SIZE = 10;
	private GoverOnlineApproveAdapter goverOnlineApproveAdapter;
	private ListView gover_search_reasult;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GOVERITEM_LOAD_SUCCESS:
				showItemList();
				break;
			case GOVERITEM_LOAD_FIAL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}

		};
	};

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.goversaloon_searchreslut_layout, null);
		context = getActivity();
		initUI();
		params = (Map<String, String>) getArguments().getSerializable(
				PARAMS_KEY);
		params.put("start", "0");
		params.put("end", PAGE_SIZE + "");
		loadItem(params);

		return view;
	}

	private void initUI() {
		pb_item = (ProgressBar) view.findViewById(R.id.pb_item);
		gover_search_reasult = (ListView) view
				.findViewById(R.id.gover_search_reasult);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		gover_search_reasult.addFooterView(loadMoreView);
		gover_search_reasult.setOnScrollListener(this);

	}

	/**
	 * 获取办件信息
	 */
	private void loadItem(final Map<String, String> params) {

		if (isFirstLoadGoverItem) {// 首次加载时或切换部门时显示进度条

			pb_item.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				GoverSaoonItemService goverSaoonItemService = new GoverSaoonItemService(
						context);
				try {
					goverSaoonItemWrapper = goverSaoonItemService
							.getGoverSaoonItemsByParas(params);
					if (goverSaoonItemWrapper != null) {
						msg.what = GOVERITEM_LOAD_SUCCESS;
					} else {
						msg.what = GOVERITEM_LOAD_FIAL;
						msg.obj = "加载办件信息失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();

					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示办件事项详细信息
	 */
	protected void showItemList() {

		if (goverSaoonItemWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {
			loadMoreButton.setText(" ");
		}

		List<GoverSaoonItem> goverSaoonItems = goverSaoonItemWrapper
				.getGoverSaoonItems();
		if (goverSaoonItems != null && goverSaoonItems.size() > 0) {
			if (isFirstLoadGoverItem) {// 首次加载
				goverOnlineApproveAdapter = new GoverOnlineApproveAdapter(
						goverSaoonItems, context);
				isFirstLoadGoverItem = false;
				gover_search_reasult.setAdapter(goverOnlineApproveAdapter);
				pb_item.setVisibility(ProgressBar.GONE);

			} else {

				for (GoverSaoonItem item : goverSaoonItems) {
					goverOnlineApproveAdapter.addItem(item);
				}

				goverOnlineApproveAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_search_reasult.setSelection(visibleLastIndex
						- visibleItemCount + 1); // 设置选中项

			}

		}else{
			
			pb_item.setVisibility(ProgressBar.GONE);
			Toast.makeText(context, "没有检索到数据", Toast.LENGTH_SHORT).show();
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

		int itemsLastIndex = goverOnlineApproveAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (goverSaoonItemWrapper != null && goverSaoonItemWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");

				params.put("start", (visibleLastIndex + 1) + "");
				params.put("end", (visibleLastIndex + 1 + PAGE_SIZE) + "");

				loadItem(params);
			}

		}
	}
	
	

}

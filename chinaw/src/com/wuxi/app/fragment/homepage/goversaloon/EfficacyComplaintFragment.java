package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.adapter.EfficacyComplaintAdapter;
import com.wuxi.app.engine.EfficaComplainService;
import com.wuxi.domain.Content;
import com.wuxi.domain.EfficaComplain;
import com.wuxi.domain.EfficaComplainWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 效能投诉Fragment
 * 
 */
public class EfficacyComplaintFragment extends BaseFragment implements
		OnScrollListener {

	private View view;
	private ImageView gover_eff_btn_mail_search;
	private ImageView gover_eff_btn_writemail;
	private ListView gover_eff_lv;
	private Context context;
	private ProgressBar gover_eff_pb;
	private static final int PAGE_SIZE = 10;
	protected static final int LOAD_EFF_SUCCESS = 0;
	protected static final int LOAD_EFF_FAIL = 1;
	private boolean isFistLoad = true;
	private EfficaComplainWrapper efficaWrapper;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private EfficacyComplaintAdapter efficacyComplaintAdapter;
	private String[] itemStr = new String[] { "清西路的高烟囱何时拆掉？", "清西路的高烟囱何时拆掉？",

	"清西路的高烟囱何时拆掉？" };

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case LOAD_EFF_SUCCESS:
				showEffData();
				break;
			case LOAD_EFF_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gover_efficacycomplaint_layout, null);
		context = getActivity();
		initUI();
		return view;
	}

	/**
	 * 
	 * wanglu 泰得利通 显示效能投诉列表数据
	 */
	protected void showEffData() {
		if (efficaWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {
			loadMoreButton.setText("没有数据了...");
		}

		List<EfficaComplain> efficaComplains = efficaWrapper
				.getEfficaComplains();
		if (efficaComplains != null && efficaComplains.size() > 0) {
			if (isFistLoad) {
				efficacyComplaintAdapter = new EfficacyComplaintAdapter(
						efficaComplains, context);
				isFistLoad = false;
				gover_eff_lv.setAdapter(efficacyComplaintAdapter);
				gover_eff_pb.setVisibility(ProgressBar.GONE);

			} else {

				for (EfficaComplain eff : efficaComplains) {
					efficacyComplaintAdapter.addItem(eff);
				}

				efficacyComplaintAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_eff_lv.setSelection(visibleLastIndex - visibleItemCount
						+ 1); // 设置选中项

			}

		}

	}

	private void initUI() {
		gover_eff_btn_mail_search = (ImageView) view
				.findViewById(R.id.gover_eff_btn_mail_search);
		gover_eff_btn_writemail = (ImageView) view
				.findViewById(R.id.gover_eff_btn_writemail);
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);

		gover_eff_lv = (ListView) view.findViewById(R.id.gover_eff_lv);
		gover_eff_lv.addFooterView(loadMoreView);
		gover_eff_lv.setOnScrollListener(this);
		gover_eff_pb = (ProgressBar) view.findViewById(R.id.gover_eff_pb);

		loadEffData(0, PAGE_SIZE);

	}

	private void loadEffData(final int start, final int end) {
		if (isFistLoad) {
			gover_eff_pb.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				EfficaComplainService efficaComplainService = new EfficaComplainService(
						context);
				try {
					efficaWrapper = efficaComplainService
							.getPageEfficaComplains(start, end);
					if (efficaWrapper != null) {
						msg.what = LOAD_EFF_SUCCESS;

					} else {

						msg.what = LOAD_EFF_FAIL;
						msg.obj = "没有获取到数据!";

					}

					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_EFF_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_EFF_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (ResultException e) {
					e.printStackTrace();
					msg.what = LOAD_EFF_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = efficacyComplaintAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (efficaWrapper != null && efficaWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				loadEffData(visibleLastIndex + 1, visibleLastIndex + 1
						+ PAGE_SIZE);
			}

		}
	}

}

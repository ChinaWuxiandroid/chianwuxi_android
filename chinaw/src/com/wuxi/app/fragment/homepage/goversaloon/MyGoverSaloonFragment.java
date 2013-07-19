package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.MyOnlineApplyAdapter;
import com.wuxi.app.adapter.MyOnlineConsultAdapter;
import com.wuxi.app.engine.MyApplyService;
import com.wuxi.app.engine.MyconsultService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.domain.MyApply;
import com.wuxi.domain.MyApplyWrapper;
import com.wuxi.domain.Myconsult;
import com.wuxi.domain.MyconsultWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 我的政务大厅
 * 
 */
public class MyGoverSaloonFragment extends GoverSaloonContentFragment implements
		OnCheckedChangeListener {

	private RadioGroup gover_btn_rg;
	private RadioButton gover_sallon_myconsult;
	private RadioButton gover_sallon_my_apply;
	private ListView gover_saloon_lv_myapply;// 我的申请
	private ListView gover_saloon_lv_myconsult;// 我的在线咨询
	private static final String ACCESS_TOKEN = "bd58fcdfe5b54f4c95ed5f2e3a945f7c";

	private static final int PAGE_SIZE = 4;
	private static final int MYCONSULT_CHECKED = 0;// 标识哪个按钮选中
	private static final int MYCONAPPLY_CHECKED = 1;
	protected static final int MYCONSULT_LOADSUCCESS = 0;// 咨询列表加载成功
	protected static final int MYCONSULT_LOADFAIL = 1;// 咨询列表加载失败
	protected static final int MYAPP_LOADSUCCESS = 2;// 申报列表加载成功
	protected static final int MYAPP_LOADFAIL = 3;// 申报列表加载失败
	private MyconsultWrapper myconsultWrapper;// 我的咨询包装类
	private MyApplyWrapper myApplyWrapper;
	private int type = MYCONSULT_CHECKED;

	private MyOnlineConsultAdapter myOnlineConsultAdapter;

	private MyOnlineApplyAdapter myOnlineApplyAdapter;
	private boolean isFristLoadMyConsultData = true;// 是不是首次加载
	private boolean isFistLoadMyApplyData = true;
	private ProgressBar gover_pb_myonlineapply;
	private View myconsultloadMoreView;
	private Button myconsultloadMoreButton;
	private View myapplyloadMoreView;
	private Button myapplyloadMoreButton;
	private int myconsultvisibleItemCount;// 当前我的在线咨询显示条数
	private int myconsultvisibleLastIndex;// 我的在线咨询最后一条索引
	private int myappvisibleItemCount;// 当前我的在申报列表显示条数
	private int myappvisibleLastIndex;// 当前我的在申报列表最后一条索引

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MYCONSULT_LOADSUCCESS:
				showMyConsultData();
				break;
			case MYAPP_LOADSUCCESS:
				showMyApplyData();
				break;
			case MYAPP_LOADFAIL:
			case MYCONSULT_LOADFAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();

				break;
			}
		};
	};

	/**
	 * 
	 * wanglu 泰得利通 显示申报列表数据
	 */
	protected void showMyApplyData() {
		if (myApplyWrapper.isNext()) {
			myapplyloadMoreButton.setText("more");

		} else {
			myapplyloadMoreButton.setText("没有数据了...");
		}

		List<MyApply> myApplies = myApplyWrapper.getMyApplies();
		if (myApplies != null && myApplies.size() > 0) {
			if (isFistLoadMyApplyData) {
				myOnlineApplyAdapter = new MyOnlineApplyAdapter(myApplies,
						context);
				isFistLoadMyApplyData = false;
				gover_saloon_lv_myapply.setAdapter(myOnlineApplyAdapter);
				gover_pb_myonlineapply.setVisibility(ProgressBar.GONE);

			} else {

				for (MyApply myApply : myApplies) {
					myOnlineApplyAdapter.addItem(myApply);
				}

				myOnlineApplyAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_saloon_lv_myapply.setSelection(myappvisibleLastIndex
						- myappvisibleItemCount + 1); // 设置选中项

			}

		}

	}

	protected void initUI() {
		super.initUI();
		gover_pb_myonlineapply = (ProgressBar) view
				.findViewById(R.id.gover_pb_myonlineapply);
		gover_btn_rg = (RadioGroup) view.findViewById(R.id.gover_btn_rg);
		gover_btn_rg.setOnCheckedChangeListener(this);
		gover_sallon_myconsult = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_ask);
		gover_sallon_my_apply = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_apply);

		gover_saloon_lv_myconsult = (ListView) view
				.findViewById(R.id.gover_saloon_lv_myconsult);
		gover_saloon_lv_myapply = (ListView) view
				.findViewById(R.id.gover_saloon_lv_myapply);

		gover_saloon_lv_myconsult.setVisibility(ListView.VISIBLE);
		gover_saloon_lv_myapply.setVisibility(ListView.INVISIBLE);

		gover_sallon_myconsult.setChecked(true);

		myconsultloadMoreView = View.inflate(context,
				R.layout.list_loadmore_layout, null);
		myapplyloadMoreView = View.inflate(context,
				R.layout.myapply_list_loadmore_layout, null);
		myconsultloadMoreButton = (Button) myconsultloadMoreView
				.findViewById(R.id.loadMoreButton);
		myapplyloadMoreButton = (Button) myapplyloadMoreView
				.findViewById(R.id.loadMoreButton);

		gover_saloon_lv_myconsult.addFooterView(myconsultloadMoreView);// 为listView添加底部视图
		gover_saloon_lv_myapply.addFooterView(myapplyloadMoreView);
		gover_saloon_lv_myconsult
				.setOnScrollListener(new MyConsultOnScrollListener());// 增加滑动监听
		gover_saloon_lv_myapply
				.setOnScrollListener(new MyAppplyScrollListener());

		gover_sallon_myconsult.setChecked(true);
		switchRadionButtonStyle();

		loadMyConsultData(0, PAGE_SIZE);// 加载我的咨询数据
	}

	private void loadMyConsultData(final int start, final int end) {

		if (isFristLoadMyConsultData) {// 如果是首次加载显示进度条
			gover_pb_myonlineapply.setVisibility(ProgressBar.VISIBLE);// 显示加载进度条
		}

		new Thread(new Runnable() {
			Message msg = handler.obtainMessage();

			@Override
			public void run() {

				MyconsultService myconsultService = new MyconsultService(
						context);
				try {
					myconsultWrapper = myconsultService.getPageMyconsults(
							ACCESS_TOKEN, start, end);
					if (myconsultWrapper.getMyconsults() != null) {
						msg.what = MYCONSULT_LOADSUCCESS;

					} else {
						msg.what = MYCONSULT_LOADFAIL;
						msg.obj = "没有数据";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = MYCONSULT_LOADFAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = MYCONSULT_LOADFAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (ResultException e) {
					e.printStackTrace();
					msg.what = MYCONSULT_LOADFAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	protected void showMyConsultData() {

		if (myconsultWrapper.isNext()) {
			myconsultloadMoreButton.setText("more");

		} else {
			myconsultloadMoreButton.setText("没有数据了...");
		}

		List<Myconsult> myconsults = myconsultWrapper.getMyconsults();
		if (myconsults != null && myconsults.size() > 0) {
			if (isFristLoadMyConsultData) {
				BaseSlideFragment baseSlideFragment=(BaseSlideFragment)this.getArguments().get("BaseSlideFragment");
				myOnlineConsultAdapter = new MyOnlineConsultAdapter(myconsults,
						context, managers,baseSlideFragment);
				isFristLoadMyConsultData = false;
				gover_saloon_lv_myconsult.setAdapter(myOnlineConsultAdapter);
				gover_pb_myonlineapply.setVisibility(ProgressBar.GONE);
				// isLoading = false;
			} else {

				for (Myconsult myconsult : myconsults) {
					myOnlineConsultAdapter.addItem(myconsult);
				}

				myOnlineConsultAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_saloon_lv_myconsult
						.setSelection(myconsultvisibleLastIndex
								- myconsultvisibleItemCount + 1); // 设置选中项
				// isLoading = false;
			}

		}

	}

	private class MyConsultOnScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			myconsultvisibleItemCount = visibleItemCount;
			myconsultvisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = myOnlineConsultAdapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& myconsultvisibleLastIndex == lastIndex) {

				if (myconsultWrapper != null && myconsultWrapper.isNext()) {// 还有下一条记录

					myconsultloadMoreButton.setText("loading.....");
					loadMyConsultData(myconsultvisibleLastIndex + 1,
							myconsultvisibleLastIndex + 1 + PAGE_SIZE);// 加载我的咨询数据
				}

			}
		}

	}

	private class MyAppplyScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			myappvisibleItemCount = visibleItemCount;
			myappvisibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = myOnlineApplyAdapter.getCount() - 1; // 数据集最后一项的索引
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& myappvisibleLastIndex == lastIndex) {

				if (myApplyWrapper != null && myApplyWrapper.isNext()) {// 还有下一条记录

					myapplyloadMoreButton.setText("loading.....");
					loadMyAppLodata(myappvisibleLastIndex + 1,
							myappvisibleLastIndex + 1 + PAGE_SIZE);// 加载我的咨询数据
				}

			}
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.gover_sallon_my_ask:// 我的咨询列表

			gover_saloon_lv_myconsult.setVisibility(ListView.VISIBLE);
			gover_saloon_lv_myapply.setVisibility(ListView.INVISIBLE);
			break;
		case R.id.gover_sallon_my_apply:// 我的申报列表
			gover_saloon_lv_myconsult.setVisibility(ListView.INVISIBLE);
			gover_saloon_lv_myapply.setVisibility(ListView.VISIBLE);
			if (isFistLoadMyApplyData) {

				loadMyAppLodata(0, PAGE_SIZE);
			}

			break;
		}
		switchRadionButtonStyle();

	}

	/**
	 * 
	 * wanglu 泰得利通 加载我的申请数据
	 * 
	 * @param start
	 * @param pageSize
	 */
	private void loadMyAppLodata(final int start, final int end) {

		if (isFistLoadMyApplyData) {// 如果是首次加载显示进度条
			gover_pb_myonlineapply.setVisibility(ProgressBar.VISIBLE);// 显示加载进度条
		}

		new Thread(new Runnable() {
			Message msg = handler.obtainMessage();

			@Override
			public void run() {

				MyApplyService myApplyService = new MyApplyService(context);
				try {
					myApplyWrapper = myApplyService.getPageMyApplyes(
							ACCESS_TOKEN, start, end);
					if (myApplyWrapper.getMyApplies() != null) {
						msg.what = MYAPP_LOADSUCCESS;

					} else {
						msg.what = MYAPP_LOADFAIL;
						msg.obj = "没有数据";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = MYCONSULT_LOADFAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = MYCONSULT_LOADFAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (ResultException e) {
					e.printStackTrace();
					msg.what = MYCONSULT_LOADFAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void switchRadionButtonStyle() {

		for (int i = 0; i < gover_btn_rg.getChildCount(); i++) {
			RadioButton rb = (RadioButton) gover_btn_rg.getChildAt(i);
			if (rb.isChecked()) {
				rb.setTextColor(Color.WHITE);
				rb.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);
			} else {
				rb.setTextColor(Color.BLACK);
				rb.setBackgroundResource(R.color.content_background);
			}
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.my_goversaloon_layout;
	}

}

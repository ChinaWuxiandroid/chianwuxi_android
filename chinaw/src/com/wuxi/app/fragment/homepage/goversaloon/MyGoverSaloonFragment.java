package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.MyApplyService;
import com.wuxi.app.engine.MyconsultService;
import com.wuxi.app.util.SystemUtil;
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
		OnCheckedChangeListener, OnClickListener {

	private RadioGroup gover_btn_rg;
	private RadioButton gover_sallon_myconsult;

	private ListView gover_saloon_lv_myapply;// 我的申请
	private ListView gover_saloon_lv_myconsult;// 我的在线咨询

	private static final int PAGE_SIZE = 10;

	protected static final int MYCONSULT_LOADSUCCESS = 0;// 咨询列表加载成功
	protected static final int MYCONSULT_LOADFAIL = 1;// 咨询列表加载失败
	protected static final int MYAPP_LOADSUCCESS = 2;// 申报列表加载成功
	protected static final int MYAPP_LOADFAIL = 3;// 申报列表加载失败
	private MyconsultWrapper myconsultWrapper;// 我的咨询包装类
	private MyApplyWrapper myApplyWrapper;

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
	private ProgressBar pb_consultloadmoore, pb_applyloadmoore;
	private LoginDialog loginDialog;
	private String accessToekn;

	@SuppressLint("HandlerLeak")
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
				gover_pb_myonlineapply.setVisibility(ProgressBar.GONE);

				break;
			}
		};
	};

	/**
	 * 
	 * wanglu 泰得利通 显示申报列表数据
	 */
	protected void showMyApplyData() {

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

		if (myApplyWrapper.isNext()) {
			if (gover_saloon_lv_myapply.getFooterViewsCount() != 0) {
				pb_applyloadmoore.setVisibility(ProgressBar.GONE);
				myapplyloadMoreButton.setText("点击加载更多");
			} else {
				gover_saloon_lv_myapply.addFooterView(getMyApplyFootView());
			}

		} else {

			gover_saloon_lv_myapply.removeFooterView(myapplyloadMoreView);
		}

	}

	protected void initUI() {
		super.initUI();

	/*	SharedPreferences sp = context.getSharedPreferences(
				Constants.SharepreferenceKey.SHARE_CONFIG, Context.MODE_APPEND);
		Editor ed = sp.edit();
		ed.putString(Constants.SharepreferenceKey.ACCESSTOKEN, "");
		ed.commit();*/

		loginDialog = new LoginDialog(context, baseSlideFragment);// 实例化登录对话框
		gover_pb_myonlineapply = (ProgressBar) view
				.findViewById(R.id.gover_pb_myonlineapply);
		gover_btn_rg = (RadioGroup) view.findViewById(R.id.gover_btn_rg);
		gover_btn_rg.setOnCheckedChangeListener(this);
		gover_sallon_myconsult = (RadioButton) view
				.findViewById(R.id.gover_sallon_my_ask);

		gover_saloon_lv_myconsult = (ListView) view
				.findViewById(R.id.gover_saloon_lv_myconsult);
		gover_saloon_lv_myapply = (ListView) view
				.findViewById(R.id.gover_saloon_lv_myapply);

		gover_saloon_lv_myconsult.setVisibility(ListView.VISIBLE);
		gover_saloon_lv_myapply.setVisibility(ListView.INVISIBLE);

		gover_sallon_myconsult.setChecked(true);

		gover_saloon_lv_myconsult.addFooterView(getMyConsultFootView());// 为listView添加底部视图
		gover_saloon_lv_myapply.addFooterView(getMyApplyFootView());
		gover_saloon_lv_myconsult
				.setOnScrollListener(new MyConsultOnScrollListener());// 增加滑动监听
		gover_saloon_lv_myapply
				.setOnScrollListener(new MyAppplyScrollListener());

		gover_sallon_myconsult.setChecked(true);

		switchRadionButtonStyle();

		if (loginDialog.checkLogin()) {
			this.accessToekn = SystemUtil.getAccessToken(context);// 获取accessToken
			loadMyConsultData(0, PAGE_SIZE);// 加载我的咨询数据
		}

	}

	private View getMyConsultFootView() {
		myconsultloadMoreView = View.inflate(context,
				R.layout.list_loadmore_layout, null);
		pb_consultloadmoore = (ProgressBar) myconsultloadMoreView
				.findViewById(R.id.pb_loadmoore);

		myconsultloadMoreButton = (Button) myconsultloadMoreView
				.findViewById(R.id.loadMoreButton);
		myconsultloadMoreButton.setOnClickListener(this);
		return myconsultloadMoreView;
	}

	/**
	 * 
	 * wanglu 泰得利通 申报列表底部视图
	 * 
	 * @return
	 */
	private View getMyApplyFootView() {
		myapplyloadMoreView = View.inflate(context,
				R.layout.myapply_list_loadmore_layout, null);
		myapplyloadMoreButton = (Button) myapplyloadMoreView
				.findViewById(R.id.loadapply_MoreButton);
		myapplyloadMoreButton.setOnClickListener(this);
		pb_applyloadmoore = (ProgressBar) myapplyloadMoreView
				.findViewById(R.id.pb_applyloadmoore);
		return myapplyloadMoreView;

	}

	private void loadMyConsultData(final int start, final int end) {

		if (isFristLoadMyConsultData) {// 如果是首次加载显示进度条
			gover_pb_myonlineapply.setVisibility(ProgressBar.VISIBLE);// 显示加载进度条
		} else {
			pb_consultloadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {
			Message msg = handler.obtainMessage();

			@Override
			public void run() {

				MyconsultService myconsultService = new MyconsultService(
						context);
				try {
					myconsultWrapper = myconsultService.getPageMyconsults(
							accessToekn, start, end);
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

		List<Myconsult> myconsults = myconsultWrapper.getMyconsults();
		if (myconsults != null && myconsults.size() > 0) {
			if (isFristLoadMyConsultData) {

				myOnlineConsultAdapter = new MyOnlineConsultAdapter(myconsults,
						context);
				isFristLoadMyConsultData = false;
				gover_saloon_lv_myconsult.setAdapter(myOnlineConsultAdapter);
				gover_pb_myonlineapply.setVisibility(ProgressBar.GONE);

			} else {

				for (Myconsult myconsult : myconsults) {
					myOnlineConsultAdapter.addItem(myconsult);
				}

				myOnlineConsultAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_saloon_lv_myconsult
						.setSelection(myconsultvisibleLastIndex
								- myconsultvisibleItemCount + 1); // 设置选中项

			}

		}

		if (myconsultWrapper.isNext()) {
			if (gover_saloon_lv_myconsult.getFooterViewsCount() != 0) {
				myconsultloadMoreButton.setText("点击加载更多");
				pb_consultloadmoore.setVisibility(ProgressBar.GONE);
			} else {
				gover_saloon_lv_myconsult.addFooterView(getMyConsultFootView());
			}

		} else {

			gover_saloon_lv_myconsult.removeFooterView(myconsultloadMoreView);
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

		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.gover_sallon_my_ask:// 我的咨询列表
			if (loginDialog.checkLogin()) {
				gover_saloon_lv_myconsult.setVisibility(ListView.VISIBLE);
				gover_saloon_lv_myapply.setVisibility(ListView.INVISIBLE);
			} else {
				loginDialog.showDialog();
			}

			break;
		case R.id.gover_sallon_my_apply:// 我的申报列表

			gover_saloon_lv_myconsult.setVisibility(ListView.INVISIBLE);
			gover_saloon_lv_myapply.setVisibility(ListView.VISIBLE);
			if (isFistLoadMyApplyData) {

				if (loginDialog.checkLogin()) {
					loadMyAppLodata(0, PAGE_SIZE);
				} else {
					loginDialog.showDialog();
				}

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
		} else {
			pb_applyloadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {
			Message msg = handler.obtainMessage();

			@Override
			public void run() {

				MyApplyService myApplyService = new MyApplyService(context);
				try {
					myApplyWrapper = myApplyService.getPageMyApplyes(
							accessToekn, start, end);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (myconsultWrapper != null && myconsultWrapper.isNext()) {// 还有下一条记录

				myconsultloadMoreButton.setText("loading.....");
				loadMyConsultData(myconsultvisibleLastIndex + 1,
						myconsultvisibleLastIndex + 1 + PAGE_SIZE);// 加载我的咨询数据
			}
			break;
		case R.id.loadapply_MoreButton:
			if (myApplyWrapper != null && myApplyWrapper.isNext()) {// 还有下一条记录

				myapplyloadMoreButton.setText("loading.....");
				loadMyAppLodata(myappvisibleLastIndex + 1,
						myappvisibleLastIndex + 1 + PAGE_SIZE);// 加载我的咨询数据
			}

			break;

		}

	}
}

/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: PortLeaderMailListFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述: 政民互动 12345来信办理平台 部门领导信箱 某部门最新信件列表
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-23 上午11:12:56
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： PortLeaderMailListFragment
 * @描述： 政民互动 12345来信办理平台 部门领导信箱 某部门最新信件列表界面
 * @作者： 罗森
 * @创建时间： 2013 2013-8-23 上午11:12:56
 * @修改时间：
 * @修改描述：
 * 
 */
public class PartLeaderMailFragment extends RadioButtonChangeFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static final String TAG = "PartLeaderMailListFragment";

	private static final int HIDEN_CONTENT_ID = R.id.mayor_box_fragment;

	private static final int ALLCOUNT_LOAD_SUCESS = 0; // 答复率总数统计
	private static final int DATA_LOAD_ERROR = 1;

	private Button dealMailBtn = null;
	private Button queryMailBtn = null;

	private TextView advisoryNum = null;
	private TextView mayorNum = null;
	private TextView leaderNum = null;

	private RadioButton radioBtn = null;

	private View popview = null;

	private List<AllCount> allCounts;

	private PartLeaderMail leaderMail;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private int contentType = 0; // 内容类型，缺省为0-信件列表 1-写信须知 2-办理规则

	private final int[] radioButtonIds = {
			R.id.gip_12345_mayorbox_radioButton_mailList,
			R.id.gip_12345_mayorbox_radioButton_mustKonwMail,
			R.id.gip_12345_mayorbox_radioButton_mayorBoxRule };

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case ALLCOUNT_LOAD_SUCESS:

				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_mayorbox_radioButton_mailList:
			contentType = 0;
			init();
			break;

		case R.id.gip_12345_mayorbox_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			break;

		case R.id.gip_12345_mayorbox_radioButton_mayorBoxRule:
			contentType = 2;
			changeContent(contentType);
			break;
		}
	}

	/**
	 * 切换界面
	 * 
	 * @param type
	 */
	public void changeContent(int type) {
		GoverInterPeopleWebFragment goverInterPeopleWebFragment = new GoverInterPeopleWebFragment();
		switch (type) {
		case 0:
			init();
			break;
		case 1:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/zmhd/6148278.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 2:
			Toast.makeText(context, "该功能暂未实现", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_mayorbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_mayorbox_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		radioBtn = (RadioButton) view
				.findViewById(R.id.gip_12345_mayorbox_radioButton_mayorBoxRule);
		radioBtn.setText("我要写信");

		dealMailBtn = (Button) view
				.findViewById(R.id.gip_12345_mayorbox_button_queryMail);
		dealMailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "暂未实现该功能", Toast.LENGTH_SHORT).show();
			}
		});

		queryMailBtn = (Button) view
				.findViewById(R.id.gip_12345_mayorbox_button_statisticMail);
		queryMailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopWindow(context);
				int[] xy = new int[2];
				queryMailBtn.getLocationOnScreen(xy);
				popWindow.showAtLocation(queryMailBtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, queryMailBtn.getHeight() * 2 + 18);
			}
		});

		PartLeaderMailListFragment leaderMailListFragment = new PartLeaderMailListFragment();
		leaderMailListFragment.setBaseSlideFragment(this.baseSlideFragment);
		leaderMailListFragment.setLeaderMail(getLeaderMail());
		bindFragment(leaderMailListFragment);

		loadAllCountData();
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commit();
	}

	/**
	 * 创建弹出窗体
	 * 
	 * @param con
	 * @return
	 */
	private PopupWindow makePopWindow(Context con) {
		PopupWindow popupWindow = null;

		popview = LayoutInflater.from(con).inflate(
				R.layout.gip_mayormail_deal_statistics_layout, null);

		advisoryNum = (TextView) popview
				.findViewById(R.id.advisory_complain_mail_text);

		mayorNum = (TextView) popview.findViewById(R.id.mayor_box_mail_text);

		leaderNum = (TextView) popview.findViewById(R.id.leader_box_mail_text);

		showAllCounts();

		popWindowManager = PopWindowManager.getInstance();

		popWindowManager.addPopWindow(popupWindow);

		popupWindow = new PopupWindow(con);

		popupWindow.setContentView(popview);

		popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);

		popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupWindow.setTouchable(true); // 设置PopupWindow可触摸
		popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		return popupWindow;
	}

	/*
	 * 显示所有回复统计信息
	 */
	public void showAllCounts() {

		for (AllCount count : allCounts) {
			if (count.getName().equals("领导信箱"))
				advisoryNum.setText(String.valueOf(count.getCount()) + "封");
			else if (count.getName().equals("咨询投诉"))
				mayorNum.setText(String.valueOf(count.getCount()) + "封");
			else if (count.getName().equals("市长信箱"))
				leaderNum.setText(String.valueOf(count.getCount()) + "封");
		}
	}

	/**
	 * 先加载所有统计信息，下面的信息，按 统计按钮后加载
	 * */
	private void loadAllCountData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(
						context);
				try {
					allCounts = replyStatisticsService
							.getAllCount(Constants.Urls.LETTERS_ALLCOUNT_URL);
					if (null != allCounts) {
						handler.sendEmptyMessage(ALLCOUNT_LOAD_SUCESS);
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
	 * @return leaderMail
	 */
	public PartLeaderMail getLeaderMail() {
		return leaderMail;
	}

	/**
	 * @param leaderMail
	 *            要设置的 leaderMail
	 */
	public void setLeaderMail(PartLeaderMail leaderMail) {
		this.leaderMail = leaderMail;
	}

}

package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 12345来信办理平台 主Fragment --区市长信箱 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345CMayorMailBoxFragment extends RadioButtonChangeFragment
		implements OnClickListener {

	private LinearLayout mLayout_jy, mLayout_yx, mLayout_xs, mLayout_hs,
			mLayout_bh, mLayout_ca, mLayout_nc, mLayout_bt, mLayout_wx;

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_cmayorbox_layout;
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
		initlayout();
		setListener();
	}

	/**
	 * 
	 * 初始化控件
	 * 
	 * @方法： initlayout
	 * @描述： TODO
	 */
	private void initlayout() {
		// 江阴市人民政府
		mLayout_jy = (LinearLayout) view
				.findViewById(R.id.district_mayor_jy_layout);
		// 宜兴市人民政府
		mLayout_yx = (LinearLayout) view
				.findViewById(R.id.district_mayor_yx_layout);
		// 锡山市人民政府
		mLayout_xs = (LinearLayout) view
				.findViewById(R.id.district_mayor_xs_layout);
		// 惠山市人民政府
		mLayout_hs = (LinearLayout) view
				.findViewById(R.id.district_mayor_hs_layout);
		// 滨湖市人民政府
		mLayout_bh = (LinearLayout) view
				.findViewById(R.id.district_mayor_bh_layout);
		// 崇安市人民政府
		mLayout_ca = (LinearLayout) view
				.findViewById(R.id.district_mayor_ca_layout);
		// 南长市人民政府
		mLayout_nc = (LinearLayout) view
				.findViewById(R.id.district_mayor_nc_layout);
		// 北塘市人民政府
		mLayout_bt = (LinearLayout) view
				.findViewById(R.id.district_mayor_bt_layout);
		// 无锡新区管委会
		mLayout_wx = (LinearLayout) view
				.findViewById(R.id.district_mayor_wx_layout);
	}

	/**
	 * 事件监听
	 * 
	 * @方法： setListener
	 * @描述： TODO
	 */
	private void setListener() {
		mLayout_jy.setOnClickListener(this);
		mLayout_yx.setOnClickListener(this);
		mLayout_xs.setOnClickListener(this);
		mLayout_hs.setOnClickListener(this);
		mLayout_bh.setOnClickListener(this);
		mLayout_ca.setOnClickListener(this);
		mLayout_nc.setOnClickListener(this);
		mLayout_bt.setOnClickListener(this);
		mLayout_wx.setOnClickListener(this);
	}

	/**
	 * 单击事件的处理
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 江阴
		case R.id.district_mayor_jy_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 宜兴
		case R.id.district_mayor_yx_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 锡山
		case R.id.district_mayor_xs_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 惠山
		case R.id.district_mayor_hs_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 滨湖
		case R.id.district_mayor_bh_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 崇安
		case R.id.district_mayor_ca_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 南长
		case R.id.district_mayor_nc_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 北塘
		case R.id.district_mayor_bt_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		// 无锡
		case R.id.district_mayor_wx_layout:
			Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}

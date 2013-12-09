/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GPMApplyActivity.java 
 * @包名： com.wuxi.app.activity.homepage.goverpublicmsg 
 * @描述: 政府信息公开 依申请公开 申请界面
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-10 上午9:51:16
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.activity.homepage.goverpublicmsg;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgApplyCitizenTableFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgApplyLePersonTableFragment;
import com.wuxi.domain.ApplyDept;

/**
 * @类名： GPMApplyActivity
 * @描述： 政府信息公开 依申请公开 申请界面
 * @作者： 罗森
 * @创建时间： 2013 2013-10-10 上午9:51:16
 * @修改时间：
 * @修改描述：
 */
public class GPMApplyActivity extends FragmentActivity {

	private static final int FRAGMENT_ID = R.id.gpm_apply_fragment;

	private ImageView backBtn = null;

	private ImageButton citizenImgaeBtn = null;
	private ImageButton legalPersonImageBtn = null;

	private ApplyDept applyDept;

	private String doprojectid = null;

	/**
	 * @return applyDept
	 */
	public ApplyDept getApplyDept() {

		return applyDept;
	}

	/**
	 * @param applyDept
	 *            要设置的 applyDept
	 */
	public void setApplyDept(ApplyDept applyDept) {

		this.applyDept = applyDept;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.setContentView(R.layout.gpm_apply_layout);

		doprojectid = getIntent().getStringExtra("doprojectid");

		initLayout();

		changeView(0);

	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		citizenImgaeBtn = (ImageButton) findViewById(R.id.gpm_apply_citizen_imagebtn);

		legalPersonImageBtn = (ImageButton) findViewById(R.id.gpm_apply_legal_person_imagebtn);

		// 添加公民按钮的事件监听
		citizenImgaeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				legalPersonImageBtn
						.setBackgroundResource(R.drawable.legal_person_unclick);

				citizenImgaeBtn.setBackgroundResource(R.drawable.citizen_click);

				changeView(0);
			}
		});

		// 添加法人按钮的事件监听
		legalPersonImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				citizenImgaeBtn
						.setBackgroundResource(R.drawable.citizen_unclick);

				legalPersonImageBtn
						.setBackgroundResource(R.drawable.legal_person_click);

				changeView(1);
			}
		});

		backBtn = (ImageView) findViewById(R.id.gpm_apply_back_btn);
		// 添加返回按钮的事件监听
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainTabActivity.instance.pop();// 回退
			}
		});
	}

	/**
	 * @方法： bindFragmnet
	 * @描述： 绑定视图
	 * @param fragment
	 */
	private void bindFragmnet(BaseFragment fragment) {
		FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAGMENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * @方法： changeView
	 * @描述： 切换视图
	 * @param type
	 */
	private void changeView(int type) {
		switch (type) {
		// 公民申请界面
		case 0:
			GoverMsgApplyCitizenTableFragment applyCitizenTableFragment = new GoverMsgApplyCitizenTableFragment(
					doprojectid);
			bindFragmnet(applyCitizenTableFragment);
			break;

		// 法人申请界面
		case 1:
			GoverMsgApplyLePersonTableFragment applyLePersonTableFragment = new GoverMsgApplyLePersonTableFragment(
					doprojectid);
			bindFragmnet(applyLePersonTableFragment);
			break;
		}
	}

}

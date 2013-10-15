package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;

/**
 * 我的政民互动 主Fragment --12345来信办理平台 fragment
 * 
 * @author 杨宸 智佳
 * */
public class GIPMine12345Fragment extends RadioButtonChangeFragment {

	private static final int FRAGMENT_ID = R.id.goverinterpeople_mine_12345_fragment;
	
	private final int[] radioButtonIds = {
			R.id.gip_mine_12345_radioButton_backmail,
			R.id.gip_mine_12345_radioButton_mybackmail, };

	// 我要写信 按钮
	private ImageButton writeLetterImageBtn = null;

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {

		case R.id.gip_mine_12345_radioButton_backmail:
			init();
			break;

		case R.id.gip_mine_12345_radioButton_mybackmail:
			Toast.makeText(context, "该功能暂未实现！", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_mine_12345_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_12345_radioGroup;
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

		writeLetterImageBtn = (ImageButton) view
				.findViewById(R.id.gip_mine_12345_imageButton_writemail);
		writeLetterImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(),
						MainMineActivity.class);
				intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 5);

				intent.putExtra(Constants.CheckPositionKey.LEVEL_TWO__KEY, 1);// 这个意思让你选中左侧第二个菜单也就是12345办理平台
				intent.putExtra(Constants.CheckPositionKey.LEVEL_THREE_KEY, 6);// 这个意思让你选中我要写信
				MainTabActivity.instance.addView(intent);

			}
		});
		
		GIPMine12345MyReplyLetterFragment myReplyLetterFragment = new GIPMine12345MyReplyLetterFragment();
		bindFragment(myReplyLetterFragment);
	}
	
	/**
	 * @方法： bindFragment
	 * @描述： 绑定视图
	 * @param fragment
	 */
	private void bindFragment(BaseFragment fragment){
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAGMENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

}

package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动 主Fragment --公众论坛 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIPMinePublicForumFragment extends RadioButtonChangeFragment {

	private final int[] radioButtonIds = {
			R.id.gip_mine_publicforum_radioButton_myTheme,
			R.id.gip_mine_publicforum_radioButton_themeTJoin,
			R.id.gip_mine_publicforum_radioButton_collectTheme,
			R.id.gip_mine_radioButton_suggestionPlatform };

	// TAB的选项
	private RadioGroup mRadioGroup_publicforum;
	private RadioButton mRadioButton_myTheme;// 我的主题
	private RadioButton mRadioButton_themeTJoin;// 我参与的主题
	private RadioButton mRadioButton_collectTheme;// 我收藏的主题
	private RadioButton mRadioButton_suggestionPlatform;// 我参与的热点

	private ListView mListView;

	private boolean isFirstLoadApply = true;// 是不是首次加载数据
	private boolean isSwitchApply = false;// 切换

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		// 我的主题
		case R.id.gip_mine_publicforum_radioButton_myTheme:
			init();
			break;

		// 我参与的主题
		case R.id.gip_mine_publicforum_radioButton_themeTJoin:
			break;

		// 我收藏的主题
		case R.id.gip_mine_publicforum_radioButton_collectTheme:
			break;

		// 我参与的热点
		case R.id.gip_mine_radioButton_suggestionPlatform:
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_publicforum_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_publicforum_radioGroup;
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

		initlayout();

		loadData();
	}

	/**
	 * 初始化控件
	 * 
	 * @方法： initlayout
	 * @描述： TODO
	 */
	private void initlayout() {
		mRadioGroup_publicforum = (RadioGroup) view
				.findViewById(R.id.gip_mine_publicforum_radioGroup);
		mRadioButton_myTheme = (RadioButton) view
				.findViewById(R.id.gip_mine_publicforum_radioButton_myTheme);
		mRadioButton_themeTJoin = (RadioButton) view
				.findViewById(R.id.gip_mine_publicforum_radioButton_themeTJoin);
		mRadioButton_collectTheme = (RadioButton) view
				.findViewById(R.id.gip_mine_publicforum_radioButton_collectTheme);
		mRadioButton_suggestionPlatform = (RadioButton) view
				.findViewById(R.id.gip_mine_radioButton_suggestionPlatform);
		mListView = (ListView) view
				.findViewById(R.id.goverinterpeople_mine_12345_listview);
	}

	/**
	 * 
	 * 读取数据
	 * 
	 * @方法： loadData
	 * @描述： TODO
	 */
	private void loadData() {

	}

}

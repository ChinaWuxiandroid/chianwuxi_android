/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.ForumPostService;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.exception.NetException;

/**
 * 公众论坛发表帖子碎片布局
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumPostFragment extends BaseItemContentFragment {

	// 留言主题输入框
	private EditText postThemeEdit = null;
	// 留言内容输入框
	private EditText postContentEdit = null;

	// 提交按钮
	private ImageButton postSubmitImageBtn = null;

	private ForumPostService postService = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentLayoutId()
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.forum_post_layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentTitleText()
	 */
	@Override
	protected String getContentTitleText() {
		return "公众论坛";
	}

	@Override
	public void initUI() {
		super.initUI();
		initLayout();
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {

		postThemeEdit = (EditText) view.findViewById(R.id.forum_post_name_edit);
		postContentEdit = (EditText) view
				.findViewById(R.id.forum_post_content_edit);

		postSubmitImageBtn = (ImageButton) view
				.findViewById(R.id.forum_post_imagebtn);

		postSubmitImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ForumPostFragment forumPostFragment = new ForumPostFragment();
				forumPostFragment.setBaseSlideFragment(forumPostFragment.baseSlideFragment);

				BaseSlideFragment baseSlideFragment = ForumPostFragment.this.baseSlideFragment;
				LoginDialog loginDialog = new LoginDialog(getActivity(),
						baseSlideFragment);

				if (!loginDialog.checkLogin()) {
					loginDialog.showDialog();
				} else {
					String theme = postThemeEdit.getText().toString();
					String content = postContentEdit.getText().toString();
					try {
						postService = new ForumPostService(context);

						boolean issubmit = postService.submitPosts(
								SystemUtil.getAccessToken(context), theme,
								content);
						Toast.makeText(context, "提交成功，待审核...",
								Toast.LENGTH_SHORT).show();
					} catch (NetException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}

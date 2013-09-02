/**
 * 
 */
package com.wuxi.app.activity.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.ForumPostService;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.exception.NetException;

/**
 * 公众论坛发表帖子碎片布局
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumPostActivity extends BaseItemContentActivity {

	// 留言主题输入框
	private EditText postThemeEdit = null;
	// 留言内容输入框
	private EditText postContentEdit = null;

	// 提交按钮
	private ImageButton postSubmitImageBtn = null;

	private ForumPostService postService = null;

	private LoginDialog loginDialog = null;

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
	protected void findMainContentViews(View view) {
		super.findMainContentViews(view);
		initLayout(view);
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout(View view) {

		loginDialog = new LoginDialog(this);

		postThemeEdit = (EditText) view.findViewById(R.id.forum_post_name_edit);
		postContentEdit = (EditText) view
				.findViewById(R.id.forum_post_content_edit);

		postSubmitImageBtn = (ImageButton) view
				.findViewById(R.id.forum_post_imagebtn);

		postSubmitImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!loginDialog.checkLogin()) {
					loginDialog.showDialog();
				} else {
					String theme = postThemeEdit.getText().toString();
					String content = postContentEdit.getText().toString();
					try {
						postService = new ForumPostService(
								ForumPostActivity.this);

						if (theme.equals("")) {
							Toast.makeText(ForumPostActivity.this,
									"提交失败，留言主题不能为空！", Toast.LENGTH_SHORT)
									.show();
						} else if (content.equals("")) {
							Toast.makeText(ForumPostActivity.this,
									"提交失败，留言内容不能为空！", Toast.LENGTH_SHORT)
									.show();
						} else {
							postService.submitPosts(SystemUtil
									.getAccessToken(ForumPostActivity.this),
									theme, content);
							Toast.makeText(ForumPostActivity.this,
									"提交成功，待审核...", Toast.LENGTH_SHORT).show();
						}

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

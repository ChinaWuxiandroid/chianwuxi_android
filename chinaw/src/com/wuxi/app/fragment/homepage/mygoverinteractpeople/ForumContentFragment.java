/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.engine.ForumCommentService;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;

/**
 * 公众论坛帖子详细内容碎片
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumContentFragment extends BaseItemContentFragment {

	private View popview = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	private Button comment_btn = null;

	private TextView sentUser_text = null;
	private TextView begintime_text = null;
	private TextView endtime_text = null;
	private TextView readnum_text = null;
	private TextView resurltnum_text = null;
	private TextView title_text = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentLayoutId()
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.forum_content_layout;
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
		sentUser_text = (TextView) view
				.findViewById(R.id.forum_content_sentUser_text);
		begintime_text = (TextView) view
				.findViewById(R.id.forum_content_begintime_text);
		endtime_text = (TextView) view
				.findViewById(R.id.forum_content_endtime_text);
		readnum_text = (TextView) view
				.findViewById(R.id.forum_content_readnum_text);
		resurltnum_text = (TextView) view
				.findViewById(R.id.forum_content_resurltnum_text);
		title_text = (TextView) view
				.findViewById(R.id.forum_content_title_text);

		comment_btn = (Button) view
				.findViewById(R.id.forum_content_comment_btn);
		comment_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopWindow(context);
				int[] xy = new int[2];
				comment_btn.getLocationOnScreen(xy);
				popWindow.showAtLocation(comment_btn, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0,
						comment_btn.getHeight() * 2 + 20);
			}
		});
	}

	/**
	 * 创建弹出窗体
	 * 
	 * @param con
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private PopupWindow makePopWindow(Context con) {
		PopupWindow popupWindow = null;

		popview = LayoutInflater.from(con).inflate(
				R.layout.forum_content_popwindow_layout, null);

		ImageButton submitBtn = (ImageButton) popview
				.findViewById(R.id.forum_submit_imagebtn);
		final EditText submitContent = (EditText) popview
				.findViewById(R.id.forum_popwindow_content_edit);
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String id = "77e9a0ef-cba8-4ebd-9d7b-7ccfffd4cdfe";
				String access_token = Constants.SharepreferenceKey.TEST_ACCESSTOKEN;
				String type = "/HotReviewContent";
				String content = submitContent.getText().toString();

				ForumCommentService commentService = new ForumCommentService(
						context);

				try {
					boolean isSubnit = commentService.submitComment(id,
							access_token, type, content);

					if (isSubnit) {
						Toast.makeText(context, "提交成功！", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(context, "提交失败！", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (NetException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		popWindowManager = PopWindowManager.getInstance();

		popWindowManager.addPopWindow(popupWindow);

		popupWindow = new PopupWindow(con);

		popupWindow.setContentView(popview);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.naviga_leftitem_back));
		popupWindow.setWidth(LayoutParams.FILL_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);

		popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupWindow.setTouchable(true); // 设置PopupWindow可触摸
		popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		return popupWindow;
	}

}

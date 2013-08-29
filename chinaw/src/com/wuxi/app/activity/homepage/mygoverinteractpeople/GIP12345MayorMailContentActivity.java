/**
 * 
 */
package com.wuxi.app.activity.homepage.mygoverinteractpeople;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.engine.GIPMailInfoService;
import com.wuxi.app.engine.MailCommentService;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.GIPMailInfoWrapper;
import com.wuxi.domain.LetterWrapper.Letter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 12345来信办理平台 所有 最新信件列表 列表项点击响应页面
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class GIP12345MayorMailContentActivity extends BaseItemContentActivity {

	private static final String TAG = "GIP12345MayorMailContentFragment";

	private TextView queryNoText = null;
	private TextView titleText = null;
	private TextView contentText = null;
	private TextView begintimeText = null;
	private TextView depnameText = null;
	private TextView endtimeText = null;
	private TextView dodepnameText = null;

	private WebView resultText = null;

	private Button commentBtn = null;

	private RadioGroup radioGroup = null;
	private RadioButton satisfiedRadioBtn = null;
	private RadioButton lesserSatisfiedRadioBtn = null;
	private RadioButton improveRadioBtn = null;

	private ProgressBar progressBar = null;

	private LinearLayout layout = null;

	private View popview = null;

	// 声明弹出窗体变量
	private PopupWindow popWindow = null;

	private PopWindowManager popWindowManager = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private Letter letter = null;

	private GIPMailInfoWrapper wrapper = null;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				progressBar.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);
				commentBtn.setVisibility(View.VISIBLE);
				showData();
				break;

			case DATA_LOAD_ERROR:
				Toast.makeText(GIP12345MayorMailContentActivity.this, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentLayoutId()
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.gip_12345_mail_content_layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentTitleText()
	 */
	@Override
	protected String getContentTitleText() {
		return "12345来信办理平台";
	}
	
	@Override
	protected void findMainContentViews(View view) {
		progressBar = (ProgressBar) view
				.findViewById(R.id.mail_content_progress);
		progressBar.setVisibility(View.VISIBLE);

		layout = (LinearLayout) view.findViewById(R.id.mail_content_layout);
		layout.setVisibility(View.GONE);

		queryNoText = (TextView) view.findViewById(R.id.mail_content_queryno);
		titleText = (TextView) view.findViewById(R.id.mail_content_title);
		contentText = (TextView) view.findViewById(R.id.mail_content_content);
		begintimeText = (TextView) view
				.findViewById(R.id.mail_content_begintime);
		depnameText = (TextView) view.findViewById(R.id.mail_content_depname);
		endtimeText = (TextView) view.findViewById(R.id.mail_content_endtime);
		dodepnameText = (TextView) view
				.findViewById(R.id.mail_content_dodepname);

		resultText = (WebView) view.findViewById(R.id.mail_content_result);
		resultText.getSettings().setJavaScriptEnabled(true);
		resultText.getSettings().setDefaultTextEncodingName("utf-8");
		resultText.getSettings().setBuiltInZoomControls(true);
		resultText.getSettings().setTextSize(TextSize.SMALLER);

		commentBtn = (Button) view.findViewById(R.id.mail_content_comment_btn);
		commentBtn.setVisibility(View.GONE);
		commentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWindow = makePopupWindow(GIP12345MayorMailContentActivity.this);
				int[] xy = new int[2];
				commentBtn.getLocationOnScreen(xy);
				popWindow.showAtLocation(commentBtn, Gravity.BOTTOM
						| Gravity.RIGHT, 0, commentBtn.getHeight() * 2 + 49);
			}
		});

		loadData();
	}

	/**
	 * @方法： makePopupWindow
	 * @描述： 创建信件评价弹出窗口
	 * @param cont
	 * @return PopupWindow
	 */
	private PopupWindow makePopupWindow(Context cont) {
		PopupWindow popupWindow = null;

		popview = LayoutInflater.from(cont).inflate(
				R.layout.gip_12345_mail_estimate_pop_layout, null);

		radioGroup = (RadioGroup) popview
				.findViewById(R.id.mail_estimate_radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioBtnId = group.getCheckedRadioButtonId();
				RadioButton radioBtn = (RadioButton) popview
						.findViewById(radioBtnId);
				int rank = 0;
				if (radioBtn.getText().equals("满意")) {
					rank = 3;
					submitData(rank);
				} else if (radioBtn.getText().equals("比较满意")) {
					rank = 2;
					submitData(rank);
				} else if (radioBtn.getText().equals("有待改进")) {
					rank = 1;
					submitData(rank);
				}
			}
		});

		// satisfiedRadioBtn = (RadioButton)
		// popview.findViewById(R.id.mail_satisfied_radiobtn);
		// lesserSatisfiedRadioBtn = (RadioButton)
		// popview.findViewById(R.id.mail_lesser_satisfied_radiobtn);
		// improveRadioBtn = (RadioButton)
		// popview.findViewById(R.id.mail_improve_radiobtn);

		popWindowManager = PopWindowManager.getInstance();

		popWindowManager.addPopWindow(popupWindow);

		popupWindow = new PopupWindow(cont);

		popupWindow.setContentView(popview);

		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);

		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.naviga_leftitem_back));

		popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popupWindow.setTouchable(true); // 设置PopupWindow可触摸
		popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		return popupWindow;
	}

	/**
	 * @方法： showData
	 * @描述： 显示数据
	 */
	private void showData() {
		queryNoText.setText(wrapper.getCode());
		titleText.setText("[" + wrapper.getType() + "]" + wrapper.getTitle());
		contentText.setText(wrapper.getContent());
		begintimeText.setText(wrapper.getBegintime());
		depnameText.setText(wrapper.getDepname());
		endtimeText.setText(wrapper.getEndtime());
		dodepnameText.setText(wrapper.getDodepname());

		resultText.loadData(wrapper.getResult(), "text/html; charset=UTF-8",
				null);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				GIPMailInfoService service = new GIPMailInfoService(GIP12345MayorMailContentActivity.this);
				letter = (Letter) getIntent().getExtras().get("letter");
				try {
					wrapper = service.getGipMailInfoWrapper(letter.getId());
					if (wrapper != null) {
						handler.sendEmptyMessage(DATA__LOAD_SUCESS);
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

	private void submitData(final int rank) {

		MailCommentService commentService = new MailCommentService(this);
		letter = (Letter) getIntent().getExtras().get("letter");
		try {
			boolean issubmit = commentService.submitMailComment(letter.getId(),
					rank);

			Toast.makeText(this, "提交成功，正在审核...", Toast.LENGTH_SHORT).show();

		} catch (NetException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}

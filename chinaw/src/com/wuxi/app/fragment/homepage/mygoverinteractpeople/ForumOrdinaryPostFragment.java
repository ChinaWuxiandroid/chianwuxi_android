/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.HotPostService;
import com.wuxi.app.engine.NoticePostService;
import com.wuxi.app.engine.OpinionPostService;
import com.wuxi.app.engine.OrdinaryPostService;
import com.wuxi.app.engine.QuestionnairePostService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.ForumWrapper.Forum;
import com.wuxi.domain.HotPostWrapper;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper;
import com.wuxi.domain.NoticePostWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.OpinionPostWrapper;
import com.wuxi.domain.OpinionPostWrapper.OpinionPostReplyWrapper;
import com.wuxi.domain.OrdinaryPostWrapper;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireAnswerWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireQuestionWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireQuestionWrapper.QuestionnaireQuestion;
import com.wuxi.domain.QuestionnairePostWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公众论坛 帖子详细内容 普通帖子界面碎片
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressWarnings("deprecation")
@SuppressLint({ "HandlerLeak", "SetJavaScriptEnabled" })
public class ForumOrdinaryPostFragment extends BaseFragment {

	private static final String TAG = "ForumOrdinaryPostFragment";

	private View view = null;
	private Context context = null;

	private TextView sentpepole_text = null;
	private TextView begintime_text = null;
	private TextView endtime_text = null;
	private TextView readnum_text = null;
	private TextView replynum_text = null;
	private TextView content_text = null;
	private TextView title_text = null;
	private TextView post_bengintime = null;
	private TextView post_endtime = null;
	private TextView post_readnum = null;

	private WebView post_WebView = null;

	private ScrollView post_scrollview = null;

	private ProgressBar progressBar = null;

	private LinearLayout layout = null;

	private Forum forum;

	private OrdinaryPostWrapper postWrapper = null;
	private OrdinaryPostRaplyWrapper postRaplyWrappers = null;

	private HotPostWrapper hotPostWrapper = null;
	private HotPostReplyWrapper hotPostReplyWrapper = null;

	private NoticePostWrapper noticePostWrapper = null;
	private NoticePostReplyWrapper noticePostReplyWrapper = null;

	private OpinionPostWrapper opinionPostWrapper = null;
	private OpinionPostReplyWrapper opinionPostReplyWrapper = null;

	private QuestionnairePostWrapper questionnairePostWrapper = null;
	private QuestionnaireAnswerWrapper questionnaireAnswerWrapper = null;
	private List<QuestionnaireQuestionWrapper> questionnaireQuestionWrappers = null;
	private List<QuestionnaireQuestion> questionnaireQuestions = null; 
	
	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取帖子回复的起始坐标
	private int startIndex = 0;
	// 获取帖子回复的结束坐标
	private int endIndex = 60;

	// 调查问卷类帖子相关控件
	private ListView post_listview = null;
	private TextView post_summary_text = null;

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
				setLayoutValue();
				break;

			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	/**
	 * @param forum
	 *            the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forum_ordinary_content_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		layout = (LinearLayout) view.findViewById(R.id.ordinary_linearlayout);
		layout.setVisibility(View.GONE);

		progressBar = (ProgressBar) view
				.findViewById(R.id.ordinary_progressbar);
		progressBar.setVisibility(View.VISIBLE);

		sentpepole_text = (TextView) view
				.findViewById(R.id.ordinary_sentpepole_text);
		begintime_text = (TextView) view
				.findViewById(R.id.ordinary_begintime_text);
		endtime_text = (TextView) view.findViewById(R.id.ordinary_endtime_text);

		readnum_text = (TextView) view.findViewById(R.id.ordinary_readnum_text);
		post_readnum = (TextView) view.findViewById(R.id.post_readnum);

		replynum_text = (TextView) view
				.findViewById(R.id.ordinary_replynum_text);

		content_text = (TextView) view.findViewById(R.id.ordinary_content_text);
		title_text = (TextView) view.findViewById(R.id.ordinary_title_text);
		post_bengintime = (TextView) view.findViewById(R.id.post_bengintime);
		post_endtime = (TextView) view.findViewById(R.id.post_endtime);

		post_WebView = (WebView) view.findViewById(R.id.post_webview);
		post_WebView.getSettings().setJavaScriptEnabled(true);
		post_WebView.getSettings().setDefaultTextEncodingName("utf-8");
		post_WebView.getSettings().setBuiltInZoomControls(true);
		post_WebView.getSettings().setTextSize(TextSize.SMALLER);

		post_scrollview = (ScrollView) view.findViewById(R.id.post_scrollview);

		post_listview = (ListView) view.findViewById(R.id.post_listview);

		post_summary_text = (TextView) view
				.findViewById(R.id.post_summary_text);

		loadData();

	}

	/**
	 * 设置界面显示数据
	 */
	private void setLayoutValue() {
		// 普通帖子数据显示
		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			sentpepole_text.setText(postWrapper.getSentUser());
			begintime_text.setVisibility(View.GONE);
			post_bengintime.setVisibility(View.GONE);
			endtime_text.setVisibility(View.GONE);
			post_endtime.setVisibility(View.GONE);
			readnum_text.setText(postWrapper.getReadCount());
			replynum_text.setText(String.valueOf(postRaplyWrappers
					.getTotalRowsAmount()));
			post_summary_text.setVisibility(View.GONE);
			
			title_text.setText(postWrapper.getTitle());
			content_text.setText("\u3000\u3000" + postWrapper.getContent());
		}
		// 热点话题类帖子数据显示
		else if (forum.getViewpath().equals("/HotReviewContent")) {
			if (hotPostWrapper.getDepName().equals("null")) {
				sentpepole_text.setText("版主");
			} else {
				sentpepole_text.setText(hotPostWrapper.getDepName());
			}
			
			post_summary_text.setVisibility(View.GONE);

			begintime_text.setVisibility(View.GONE);
			post_bengintime.setVisibility(View.GONE);

			post_endtime.setVisibility(View.VISIBLE);
			endtime_text.setText(hotPostWrapper.getEndTime());

			readnum_text.setVisibility(View.GONE);
			post_readnum.setVisibility(View.GONE);

			replynum_text.setText(String.valueOf(hotPostReplyWrapper
					.getTotalRowsAmount()));
			title_text.setText(hotPostWrapper.getTitle());
			content_text.setVisibility(View.GONE);
			post_scrollview.setVisibility(View.GONE);

			post_WebView.setVisibility(View.VISIBLE);
			post_WebView.loadData(hotPostWrapper.getContent(),
					"text/html; charset=UTF-8", null);
		}
		// 公告类帖子数据显示
		else if (forum.getViewpath().equals("/LegislativeCommentsContent")) {
			sentpepole_text.setText("版主");
			post_summary_text.setText("\u3000\u3000"+noticePostWrapper.getSummary());
			
			begintime_text.setText(noticePostWrapper.getBegintime());
			endtime_text.setText(noticePostWrapper.getEndtime());
			readnum_text.setText(noticePostWrapper.getReadCount());
			replynum_text.setText(String.valueOf(noticePostReplyWrapper
					.getTotalRowsAmount()));
			title_text.setText(noticePostWrapper.getTitle());

			content_text.setVisibility(View.GONE);
			post_scrollview.setVisibility(View.GONE);

			post_WebView.setVisibility(View.VISIBLE);
			post_WebView.loadData(noticePostWrapper.getContent(),
					"text/html; charset=UTF-8", null);
		}
		// 征求意见类帖子数据显示
		else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
			sentpepole_text.setText("版主");
			post_summary_text.setText("\u3000\u3000"+opinionPostWrapper.getSummary());
			
			begintime_text.setText(opinionPostWrapper.getBegintime());
			endtime_text.setText(opinionPostWrapper.getEndtime());
			readnum_text.setText(opinionPostWrapper.getReadCount());
			replynum_text.setText(String.valueOf(opinionPostReplyWrapper
					.getTotalRowsAmount()));
			title_text.setText(opinionPostWrapper.getTitle());

			content_text.setVisibility(View.GONE);
			post_scrollview.setVisibility(View.GONE);

			post_WebView.setVisibility(View.VISIBLE);
			post_WebView.loadData(opinionPostWrapper.getContent(),
					"text/html; charset=UTF-8", null);
		}
		// 问卷调查类帖子数据显示
		else if (forum.getViewpath().equals("/SurveryContent")) {
			QuestionnaireListAdapter listAdapter = new QuestionnaireListAdapter();
			post_listview.setVisibility(View.VISIBLE);
			
			content_text.setVisibility(View.GONE);
			post_scrollview.setVisibility(View.GONE);

			sentpepole_text.setText(questionnairePostWrapper.getAuthor());
			begintime_text.setText(questionnairePostWrapper.getCreateDate());
			endtime_text.setText(questionnairePostWrapper.getEndDate());
			readnum_text.setText(questionnairePostWrapper.getReadCount());
			replynum_text.setText(String.valueOf(questionnaireAnswerWrapper.getTotalRowsAmount()));
			
			title_text.setText(questionnairePostWrapper.getTitle());
			post_summary_text.setText("\u3000\u3000"+questionnairePostWrapper.getSummary());
			
			post_listview.setAdapter(listAdapter);
		}
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// 普通帖子数据加载
					if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
						OrdinaryPostService postService = new OrdinaryPostService(
								context);

						postWrapper = postService.getOrdinaryPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								endIndex);

						if (postWrapper != null) {
							postRaplyWrappers = postWrapper.getRaplyWrapper();

							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 热点话题类帖子数据加载
					else if (forum.getViewpath().equals("/HotReviewContent")) {
						HotPostService hotPostService = new HotPostService(
								context);
						hotPostWrapper = hotPostService.getHotPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								endIndex);

						if (hotPostWrapper != null) {
							hotPostReplyWrapper = hotPostWrapper
									.getHotPostReplyWrapper();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 公告类帖子数据加载
					else if (forum.getViewpath().equals(
							"/LegislativeCommentsContent")) {
						NoticePostService noticePostService = new NoticePostService(
								context);
						noticePostWrapper = noticePostService
								.getNoticePostWrapper(forum.getId(),
										forum.getViewpath(), startIndex,
										endIndex);

						if (noticePostWrapper != null) {
							noticePostReplyWrapper = noticePostWrapper
									.getNoticePostReplyWrapper();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 征求意见类帖子数据加载
					else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
						OpinionPostService opinionPostService = new OpinionPostService(
								context);
						opinionPostWrapper = opinionPostService
								.getOpinionPostWrapper(forum.getId(),
										forum.getViewpath(), startIndex,
										endIndex);
						if (opinionPostWrapper != null) {
							opinionPostReplyWrapper = opinionPostWrapper
									.getOpinionPostReplyWrapper();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					//调查问卷类帖子数据加载
					else if (forum.getViewpath().equals("/SurveryContent")) {
						QuestionnairePostService questionnairePostService = new QuestionnairePostService(context);
						questionnairePostWrapper = questionnairePostService.getQuestionnairePostWrapper(forum.getId(), forum.getViewpath(), startIndex, endIndex);
						if (questionnairePostWrapper != null) {
							questionnaireQuestionWrappers = questionnairePostWrapper.getQuestionnaireQuestionWrappers();
							questionnaireQuestions = questionnaireQuestionWrappers.get(12).getQuestionnaireQuestions();
							questionnaireAnswerWrapper = questionnairePostWrapper.getQuestionnaireAnswerWrapper();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						}else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
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
	 * 调查问卷类帖子列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class QuestionnaireListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return questionnaireQuestionWrappers.size();
		}

		@Override
		public Object getItem(int position) {
			return questionnaireQuestionWrappers.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView question_text;

			public RadioGroup answer_radiogroup;

			public LinearLayout checkbox_layout;

			public EditText answer_edittext;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();

				convertView = LayoutInflater.from(context).inflate(
						R.layout.forum_post_list_layout, null);

				holder.question_text = (TextView) convertView
						.findViewById(R.id.questionnaire_question_text);

				holder.answer_radiogroup = (RadioGroup) convertView
						.findViewById(R.id.questionnaire_answer_radiogroup);
				
				holder.checkbox_layout = (LinearLayout) convertView
						.findViewById(R.id.checkbox_layout);


				holder.answer_edittext = (EditText) convertView
						.findViewById(R.id.answer_edittext);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			
			holder.question_text.setText(questionnaireQuestionWrappers.get(position).getDescription());
			
			if (questionnaireQuestionWrappers.get(position).getQuestionType().equals("RADIO")) {
				for (int i = 0; i < Integer.valueOf(questionnaireQuestionWrappers.get(position).getOptionCount())+1; i++) {
					RadioButton radioButton = new RadioButton(context);
					
					radioButton.setText(questionnaireQuestions.get(i).getSerialNumber()+"、"+questionnaireQuestions.get(i).getOptionValue());
					radioButton.setTextSize(10);
					holder.answer_radiogroup.addView(radioButton);
				}
			}
			
			if (questionnaireQuestionWrappers.get(position).getQuestionType().equals("CHECK")) {
				for (int j = 0; j < Integer.valueOf(questionnaireQuestionWrappers.get(position).getOptionCount())+1; j++) {
					CheckBox checkBox = new CheckBox(context);
					checkBox.setText(questionnaireQuestions.get(j).getSerialNumber()+"、"+questionnaireQuestions.get(j).getOptionValue());
					checkBox.setTextSize(10);
					holder.answer_radiogroup.setVisibility(View.GONE);
					
					holder.checkbox_layout.addView(checkBox);
				}
			}
			
			if (questionnaireQuestionWrappers.get(position).getQuestionType().equals("TEXT")) {
				holder.answer_radiogroup.setVisibility(View.GONE);
				holder.checkbox_layout.setVisibility(View.GONE);
			}
			
			return convertView;
		}

	}
}

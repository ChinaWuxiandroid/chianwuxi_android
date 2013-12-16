package com.wuxi.app.activity.homepage.mygoverinteractpeople;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.net.HttpUtils;
import com.wuxi.app.net.NetworkUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.OnlineSurveyDetailsInfo;
import com.wuxi.domain.OnlineSurveyInfo;
import com.wuxi.domain.OnlineSurveyOptionesInfo;
import com.wuxi.domain.OnlineSurveyQuestionsInfo;

/**
 * 
 * 网上调查详细
 * 
 * @类名： GIPSuggestSurveyDetailActivity
 * @作者： 陈彬
 * @创建时间： 2013 2013-11-18 下午5:33:20
 * @修改时间：
 * @修改描述：
 */
public class GIPSuggestSurveyDetailActivity extends BaseItemContentActivity {

	private String surveryId = null;
	private OnlineSurveyInfo mSurveyInfo = null;
	private ArrayList<OnlineSurveyOptionesInfo> mOptionesList = null;
	private ArrayList<OnlineSurveyDetailsInfo> mDetailsList = null;
	private ArrayList<OnlineSurveyQuestionsInfo> mQuestionsList = null;
	private TextView mTextTitle;
	private TextView mTextContent;
	private LinearLayout mLayoutControls;
	private Button mButtonSubmit;
	private ProgressDialog mDialog = null;

	private ArrayList<SubmitData> mSubmitDataList = new ArrayList<GIPSuggestSurveyDetailActivity.SubmitData>();

	private ArrayList<ChooseData> chooesList = new ArrayList<ChooseData>();;

	private LoginDialog loginDialog;

	/**
	 * 引用布局
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.online_survey_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "网上调查";
	}

	/**
	 * 初始化控件
	 */
	@Override
	protected void findMainContentViews(View view) {
		super.findMainContentViews(view);
		initlayout(view);
		surveryId = getIntent().getStringExtra("surveryId");
		new GetSurveyDetail().execute();
		loginDialog = new LoginDialog(GIPSuggestSurveyDetailActivity.this);

	}

	/**
	 * 初始化控件
	 * 
	 * @方法： initlayout
	 * @param view
	 */
	private void initlayout(View view) {
		mTextTitle = (TextView) view.findViewById(R.id.online_survey_title);
		mTextContent = (TextView) view.findViewById(R.id.online_survey_content);
		mLayoutControls = (LinearLayout) view
				.findViewById(R.id.online_survey_controls_layout);
		mButtonSubmit = (Button) view.findViewById(R.id.online_survey_submit);
		mButtonSubmit.setVisibility(View.GONE);
		// 提交
		mButtonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				JSONArray array = null;
				try {
					array = new JSONArray();
					for (int i = 0; i < mSubmitDataList.size(); i++) {

						JSONObject object = new JSONObject();

						SubmitData data = mSubmitDataList.get(i);

						object.put("index", data.getIndex().toString());
						object.put("chooes", data.getChoose().toString());
						object.put("otherAnswer", data.getOtherAnswer() + "");
						object.put("answer", data.getAnswer().toString());
						object.put("outSelect", data.getOutSelect().toString());
						object.put("minSelect", data.getMinSelect().toString());
						object.put("maxSelect", data.getMaxSelect().toString());
						object.put("questionId", data.getQuestionId()
								.toString());

						JSONArray array2 = new JSONArray();
						for (int j = 0; j < chooesList.size(); j++) {

							if (chooesList.get(j).getQuestionId()
									.equals(data.getQuestionId())) {
								array2.put(chooesList.get(j).getResult() + "");
							}
						}

						object.put("results", array2);

						array.put(object);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(GIPSuggestSurveyDetailActivity.this,
							"获取数据失败", Toast.LENGTH_SHORT).show();
				}

				if (array != null) {
					if (!loginDialog.checkLogin()) {
						loginDialog.showDialog();
					} else {
						String token = SystemUtil
								.getAccessToken(GIPSuggestSurveyDetailActivity.this);
						new SendSurveyData(array, token).execute();
					}
				} else {
					Toast.makeText(GIPSuggestSurveyDetailActivity.this,
							"获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * 获取网上调查的详细信息
	 * 
	 * @类名： GetSurveyDetail
	 * @作者： 陈彬
	 * @创建时间： 2013 2013-11-19 上午9:04:19
	 * @修改时间：
	 * @修改描述：
	 */
	private class GetSurveyDetail extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// 组合路径
			String url = Constants.Urls.DOMAIN_URL
					+ "/api/publicbbs/"
					+ surveryId
					+ "/details.json?type=/SurveryContent&replystart=0&replyend=1";

			NetworkUtil mUtil = NetworkUtil.getInstance();
			String data = null;
			if (mUtil.isConnet(GIPSuggestSurveyDetailActivity.this)) {
				HttpUtils mHttpUtils = HttpUtils.getInstance();
				data = mHttpUtils.executeGetToString(url, 5000);
			} else {
				Toast.makeText(GIPSuggestSurveyDetailActivity.this, "连接网络失败",
						Toast.LENGTH_SHORT).show();
				if (mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (isNULL(result)) {
				// 解析数据，并获取
				if (mSurveyInfo == null) {
					mSurveyInfo = new OnlineSurveyInfo(result,
							GIPSuggestSurveyDetailActivity.this);
				}
				mSurveyInfo = mSurveyInfo.getData();
				if (mSurveyInfo != null) {
					// 设置调查的标题和内容
					mHandler.sendEmptyMessage(100);
				} else {
					Toast.makeText(GIPSuggestSurveyDetailActivity.this,
							"解析数据失败", Toast.LENGTH_SHORT).show();
					if (mDialog.isShowing()) {
						mDialog.cancel();
					}
				}
			} else {
				Toast.makeText(GIPSuggestSurveyDetailActivity.this, "获取数据失败",
						Toast.LENGTH_SHORT).show();
				if (mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mDialog == null) {
				mDialog = new ProgressDialog(
						GIPSuggestSurveyDetailActivity.this);
			}
			mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mDialog.setMessage("读取数据...");
			mDialog.show();
		}

	}

	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				mDialog.setMessage("设置标题和内容...");
				mTextTitle.setText(mSurveyInfo.getRes_title() + "（填写对象为"
						+ mSurveyInfo.getRes_author() + "）");
				mTextContent.setText(mSurveyInfo.getRes_summary());
				// 动态设置布局
				mHandler.sendEmptyMessage(200);
				break;

			case 200:
				mDialog.setMessage("设置布局...");
				setControlsLayout();
				if (mDialog.isShowing()) {
					mDialog.cancel();
				}
				break;

			default:
				break;
			}
		};

	};

	/**
	 * 获取问题的数据，判断是CheckBox还是RadioButton
	 * 
	 * @方法： setControlsLayout
	 */
	private void setControlsLayout() {
		String questions = mSurveyInfo.getRes_questions();

		// 解析数据，获取问题
		mQuestionsList = new OnlineSurveyQuestionsInfo(
				GIPSuggestSurveyDetailActivity.this).resolveData(questions);
		try {

			if (mQuestionsList.size() > 0) {

				for (int i = 0; i < mQuestionsList.size(); i++) {
					// 设置问题的题目
					TextView mTextView = new TextView(
							GIPSuggestSurveyDetailActivity.this);
					LayoutParams params = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					mTextView.setLayoutParams(params);
					// 问题的题目
					String title = mQuestionsList.get(i).getDescription()
							.toString();
					mTextView.setText((i + 1) + "：" + title);
					mTextView.setPadding(20, 10, 0, 0);
					mLayoutControls.addView(mTextView);

					// 获取控件
					String controls = mQuestionsList.get(i).getQuestionType()
							.toString();
					if (!controls.equals("TEXT")) {
						// 获取一个具体问题
						String optiones = mQuestionsList.get(i).getOptiones()
								.toString();
						if (isNULL(optiones)) {
							mOptionesList = new OnlineSurveyOptionesInfo()
									.resolveData(optiones);
						}
					}

					String questionId = mQuestionsList.get(i).getQuestionId()
							.toString();
					if (controls.equals("CHECK")) {
						setControlsLayoutCheckBox(mOptionesList, questionId);
					} else if (controls.equals("RADIO")) {
						setControlsLayoutRadioButton(mOptionesList, questionId);
					} else {
						setControlsLayoutEditText(questionId);
					}
				}
				mButtonSubmit.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(GIPSuggestSurveyDetailActivity.this, "解析数据失败",
						Toast.LENGTH_SHORT).show();
				if (mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(GIPSuggestSurveyDetailActivity.this, "界面生成出错了",
					Toast.LENGTH_SHORT).show();
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
		}
	}

	/**
	 * 设置CheckBox
	 * 
	 * @方法： setControlsLayoutCheckBox
	 * @param list
	 */
	private void setControlsLayoutCheckBox(
			ArrayList<OnlineSurveyOptionesInfo> list, final String questionId) {

		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {

				View view = View.inflate(GIPSuggestSurveyDetailActivity.this,
						R.layout.online_survey_checkbox_layout, null);
				CheckBox mBox = (CheckBox) view
						.findViewById(R.id.online_survey_check_check);
				mBox.setText("" + list.get(j).getSerialNumber());
				final String zimu = list.get(j).getSerialNumber().toString();
				mBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {

							SubmitData data = new SubmitData();
							data.setIndex("");
							data.setChoose("true");
							data.setQuestionId(questionId);
							data.setOtherAnswer("");
							data.setAnswer("true");
							data.setOutSelect("false");
							data.setMaxSelect("1");
							data.setMinSelect("1");

							ChooseData chooseData = new ChooseData();
							chooseData.setQuestionId(questionId);
							chooseData.setResult(zimu);
							chooesList.add(chooseData);

							mSubmitDataList = removeData(mSubmitDataList,
									questionId);
							mSubmitDataList.add(data);
						} else {
							for (int i = 0; i < chooesList.size(); i++) {
								if (chooesList.get(i).getResult().equals(zimu)) {
									chooesList.remove(i);
								}
							}
						}
					}
				});

				TextView mText = (TextView) view
						.findViewById(R.id.online_survey_check_text);
				mText.setText(list.get(j).getOptionValue() + "");
				mLayoutControls.addView(view);
			}
		}
	}

	/**
	 * 设置RadioButton
	 * 
	 * @方法： setControlsLayoutRadioButton
	 * @param list
	 */
	private void setControlsLayoutRadioButton(
			ArrayList<OnlineSurveyOptionesInfo> list, final String questionId) {
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		View mView = View.inflate(GIPSuggestSurveyDetailActivity.this,
				R.layout.online_survey_radiogroup_layout, null);
		RadioGroup mGroup = (RadioGroup) mView
				.findViewById(R.id.online_survey_group);
		mGroup.removeAllViews();
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				RadioButton mRadioButton = new RadioButton(
						GIPSuggestSurveyDetailActivity.this);
				mRadioButton.setLayoutParams(params);
				mRadioButton.setText(list.get(j).getSerialNumber() + ""
						+ list.get(j).getOptionValue());
				final String zimu = list.get(j).getSerialNumber().toString();
				mRadioButton
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {

								SubmitData data = new SubmitData();
								data.setIndex("");
								data.setChoose("true");
								data.setQuestionId(questionId);
								data.setOtherAnswer("");
								data.setAnswer("true");
								data.setOutSelect("false");
								data.setMaxSelect("1");
								data.setMinSelect("1");

								ChooseData chooseData = new ChooseData();
								chooseData.setQuestionId(questionId);
								chooseData.setResult(zimu);
								chooesList = remove(chooesList, questionId);
								chooesList.add(chooseData);

								mSubmitDataList = removeData(mSubmitDataList,
										questionId);
								mSubmitDataList.add(data);

							}
						});
				mGroup.addView(mRadioButton);
			}
			mLayoutControls.addView(mView);
		} else {
			Toast.makeText(GIPSuggestSurveyDetailActivity.this, "解析数据失败",
					Toast.LENGTH_SHORT).show();
			if (mDialog.isShowing()) {
				mDialog.cancel();
			}
		}
	}

	/**
	 * 设置EditText
	 * 
	 * @方法： setControlsLayoutEditText
	 */

	private void setControlsLayoutEditText(final String questionId) {

		final EditText mEditText = new EditText(
				GIPSuggestSurveyDetailActivity.this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = mEditText.getText().toString();
				SubmitData data = new SubmitData();
				data.setIndex("");
				data.setChoose("false");
				data.setQuestionId(questionId);
				data.setResults(text);
				data.setOtherAnswer("");
				data.setAnswer("true");
				data.setOutSelect("false");
				data.setMaxSelect("1");
				data.setMinSelect("1");
				mSubmitDataList = removeData(mSubmitDataList, questionId);
				mSubmitDataList.add(data);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				String text = mEditText.getText().toString();
				SubmitData data = new SubmitData();
				data.setIndex("");
				data.setChoose("false");
				data.setQuestionId(questionId);
				data.setResults(text);
				data.setOtherAnswer("");
				data.setAnswer("true");
				data.setOutSelect("false");
				data.setMaxSelect("1");
				data.setMinSelect("1");
				mSubmitDataList = removeData(mSubmitDataList, questionId);
				mSubmitDataList.add(data);
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = mEditText.getText().toString();
				SubmitData data = new SubmitData();
				data.setIndex("");
				data.setChoose("false");
				data.setQuestionId(questionId);
				data.setResults(text);
				data.setOtherAnswer("");
				data.setAnswer("true");
				data.setOutSelect("false");
				data.setMaxSelect("1");
				data.setMinSelect("1");
				mSubmitDataList = removeData(mSubmitDataList, questionId);
				mSubmitDataList.add(data);
			}
		});

		mEditText.setLayoutParams(params);
		mLayoutControls.addView(mEditText);

	}

	/**
	 * 移除相同的数据
	 * 
	 * @方法： removeData
	 * @param list
	 * @return
	 */
	private ArrayList<SubmitData> removeData(ArrayList<SubmitData> list,
			String questionId) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getQuestionId().equals(questionId)) {
				list.remove(list.get(i));
			}
		}
		return list;
	}

	private ArrayList<ChooseData> remove(ArrayList<ChooseData> list,
			String questionId) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getQuestionId().equals(questionId)) {
				list.remove(list.get(i));
			}
		}
		return list;
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @方法： isNULL
	 * @param str
	 * @return
	 */
	private boolean isNULL(String str) {
		if (str == null || str == "" || str.equals(null) || str.equals("")
				|| str.length() < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 提交数据的结果集
	 * 
	 * @类名： SubmitData
	 * @作者： 陈彬
	 * @创建时间： 2013 2013-11-20 下午5:42:17
	 * @修改时间：
	 * @修改描述：
	 */
	private class SubmitData {

		private String index;
		private String choose;
		private String questionId;
		private String results;
		private String otherAnswer;
		private String answer;
		private String outSelect;
		private String minSelect;
		private String maxSelect;

		/**
		 * @return index
		 */
		public String getIndex() {
			return index;
		}

		/**
		 * @param index
		 *            要设置的 index
		 */
		public void setIndex(String index) {
			this.index = index;
		}

		/**
		 * @return choose
		 */
		public String getChoose() {
			return choose;
		}

		/**
		 * @param choose
		 *            要设置的 choose
		 */
		public void setChoose(String choose) {
			this.choose = choose;
		}

		/**
		 * @return questionId
		 */
		public String getQuestionId() {
			return questionId;
		}

		/**
		 * @param questionId
		 *            要设置的 questionId
		 */
		public void setQuestionId(String questionId) {
			this.questionId = questionId;
		}

		/**
		 * @return results
		 */
		public String getResults() {
			return results;
		}

		/**
		 * @param results
		 *            要设置的 results
		 */
		public void setResults(String results) {
			this.results = results;
		}

		/**
		 * @return otherAnswer
		 */
		public String getOtherAnswer() {
			return otherAnswer;
		}

		/**
		 * @param otherAnswer
		 *            要设置的 otherAnswer
		 */
		public void setOtherAnswer(String otherAnswer) {
			this.otherAnswer = otherAnswer;
		}

		/**
		 * @return answer
		 */
		public String getAnswer() {
			return answer;
		}

		/**
		 * @param answer
		 *            要设置的 answer
		 */
		public void setAnswer(String answer) {
			this.answer = answer;
		}

		/**
		 * @return outSelect
		 */
		public String getOutSelect() {
			return outSelect;
		}

		/**
		 * @param outSelect
		 *            要设置的 outSelect
		 */
		public void setOutSelect(String outSelect) {
			this.outSelect = outSelect;
		}

		/**
		 * @return minSelect
		 */
		public String getMinSelect() {
			return minSelect;
		}

		/**
		 * @param minSelect
		 *            要设置的 minSelect
		 */
		public void setMinSelect(String minSelect) {
			this.minSelect = minSelect;
		}

		/**
		 * @return maxSelect
		 */
		public String getMaxSelect() {
			return maxSelect;
		}

		/**
		 * @param maxSelect
		 *            要设置的 maxSelect
		 */
		public void setMaxSelect(String maxSelect) {
			this.maxSelect = maxSelect;
		}
	}

	/**
	 * 用于多选
	 * 
	 * @类名： ChooseData
	 * @创建时间： 2013 2013-11-21 上午11:41:26
	 * @修改时间：
	 * @修改描述：
	 */
	private class ChooseData {
		private String result;
		private String questionId;

		/**
		 * @return result
		 */
		public String getResult() {
			return result;
		}

		/**
		 * @param result
		 *            要设置的 result
		 */
		public void setResult(String result) {
			this.result = result;
		}

		/**
		 * @return questionId
		 */
		public String getQuestionId() {
			return questionId;
		}

		/**
		 * @param questionId
		 *            要设置的 questionId
		 */
		public void setQuestionId(String questionId) {
			this.questionId = questionId;
		}
	}

	private class SendSurveyData extends AsyncTask<String, Integer, String> {

		private JSONArray array;
		private String token;

		public SendSurveyData(JSONArray array, String token) {
			this.array = array;
			this.token = token;
		}

		@Override
		protected String doInBackground(String... params) {
			String httpUrl = Constants.Urls.DOMAIN_URL + "/api/publicbbs/"
					+ surveryId + "/survery_submit.json";

			NetworkUtil mUtil = NetworkUtil.getInstance();
			String data = null;
			if (mUtil.isConnet(GIPSuggestSurveyDetailActivity.this)) {

				try {

					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("result", array + ""));
					data = HttpConnPost(httpUrl, param);

					// httpUrl = URLEncoder.encode(httpUrl, "UTF-8");
					//
					// System.out.println("httpUrl====>" + httpUrl);
					//
					// data = HttpConnGet(httpUrl);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				Toast.makeText(GIPSuggestSurveyDetailActivity.this, "连接网络失败",
						Toast.LENGTH_SHORT).show();
				if (mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (isNULL(result)) {

				try {
					JSONObject object = new JSONObject(result);
					boolean success = object.getBoolean("success");
					if (success) {
						Toast.makeText(GIPSuggestSurveyDetailActivity.this,
								"提交成功", Toast.LENGTH_SHORT).show();
						if (mDialog.isShowing()) {
							mDialog.cancel();
						}
					} else {
						Toast.makeText(GIPSuggestSurveyDetailActivity.this,
								"提交失败", Toast.LENGTH_SHORT).show();
						if (mDialog.isShowing()) {
							mDialog.cancel();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(GIPSuggestSurveyDetailActivity.this, "数据返回失败",
						Toast.LENGTH_SHORT).show();
				if (mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mDialog == null) {
				mDialog = new ProgressDialog(
						GIPSuggestSurveyDetailActivity.this);
			}
			mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mDialog.setMessage("正在提交...");
			mDialog.show();
		}

	}

	public String HttpConnGet(String httpUrl) {

		String cont = "";
		// 生成一个请求对象
		HttpGet httpGet = new HttpGet(httpUrl);
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		// 生成一个Http客户端对象
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		// 使用Http客户端发送请求对象
		InputStream inputStream = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		// 判断是否有网络
		try {
			httpResponse = httpClient.execute(httpGet);
			httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
			httpEntity.getContentLength();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				cont = cont + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cont;

	}

	public String HttpConnPost(String url, List<NameValuePair> param) {
		String cont = "";
		HttpEntity httpEntity = null;
		try {
			HttpEntity requestHttpEntity = new UrlEncodedFormEntity(param,
					HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(requestHttpEntity);
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			InputStream inputStream = null;
			HttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpPost);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					httpEntity = httpResponse.getEntity();
					inputStream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream, "utf-8"));
					String line = "";
					while ((line = reader.readLine()) != null) {
						cont = cont + line;
					}
				} else {
					cont = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return cont;
	}

}

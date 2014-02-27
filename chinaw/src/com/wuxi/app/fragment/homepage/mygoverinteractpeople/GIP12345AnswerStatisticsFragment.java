package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.AnswerStatisticsListAdapter;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.StatisticsLetter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 12345来信办理平台 主Fragment --答复率统计 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345AnswerStatisticsFragment extends RadioButtonChangeFragment {

	private static final String TAG = "GIP12345AnswerStatisticsFragment";

	private TextView complaint_txtView; // 咨询投诉
	private TextView mayorbox_txtView; // 市长信箱
	private TextView leaderbox_txtView; // 领导信箱

	// 年份查询下拉框
	private Spinner yearAlone_Spinnner;

	// 月份查询下拉框
	private Spinner year_Spinnner;
	private Spinner month_Spinnner;

	private ImageButton startStatic_imgBtn; // 统计

	private RadioGroup radioGroup = null;
	private RadioButton yearRadioBtn = null;
	private RadioButton monthRadioBtn = null;

	private ProgressBar list_pb;

	private ListView mListView;

	private AnswerStatisticsListAdapter adapter;

	private static final int ALLCOUNT_LOAD_SUCESS = 0; // 答复率总数统计
	private static final int LETTERSTATISTICS_LOAD_SUCESS = 1; // 各部门答复率统计
	private static final int DATA_LOAD_ERROR = 2;
	public static final int REPLY_LETTER_TYPE_MAYORBOX = 1; // 市长信箱
	public static final int REPLY_LETTER_TYPE_COMPLAINT = 2; // 咨询投诉
	public static final int REPLY_LETTER_TYPE_LEADERBOX = 3; // 领导信箱

	private List<AllCount> allCounts;

	private List<StatisticsLetter> letters;

	private int letter_type = REPLY_LETTER_TYPE_MAYORBOX; // 默认为市长信箱

	private int year = 2012; // 年份
	private int month = 1; // 月份

	private final int[] radioButtonIds = {
			R.id.gip_12345_answerstati_radioButton_mayorBox,
			R.id.gip_12345_answerstati_radioButton_complaint,
			R.id.gip_12345_answerstati_radioButton_leaderBox };

	private String[] yearAlone = { "请选择年", "2013", "2012", "2011", "2010" };
	private String[] spinnerMonth = { "请选择月", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12" };

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ALLCOUNT_LOAD_SUCESS:
				showAllCounts();
				break;
			case LETTERSTATISTICS_LOAD_SUCESS:
				list_pb.setVisibility(View.INVISIBLE);
				showReplyLettersList();
				break;
			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.GONE);
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_answerstati_radioButton_mayorBox:
			letter_type = REPLY_LETTER_TYPE_MAYORBOX;
			break;

		case R.id.gip_12345_answerstati_radioButton_complaint:
			letter_type = REPLY_LETTER_TYPE_COMPLAINT;
			break;

		case R.id.gip_12345_answerstati_radioButton_leaderBox:
			letter_type = REPLY_LETTER_TYPE_LEADERBOX;
			break;

		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_answerstati_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_answerstati_radioGroup;
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
		complaint_txtView = (TextView) view
				.findViewById(R.id.gip_12345_answerstati_textview_complaintcount);
		mayorbox_txtView = (TextView) view
				.findViewById(R.id.gip_12345_answerstati_textview_mayorbox);
		leaderbox_txtView = (TextView) view
				.findViewById(R.id.gip_12345_answerstati_textview_leaderbox);

		radioGroup = (RadioGroup) view
				.findViewById(R.id.gip_12345_answerstati_radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

			}
		});

		// 年份单选按钮
		yearRadioBtn = (RadioButton) view
				.findViewById(R.id.gip_12345_answerstati_radiobutton_year);
		// 年份下拉框
		yearAlone_Spinnner = (Spinner) view
				.findViewById(R.id.gip_12345_answerstati_spinner_yearalone);

		MyAryAdapter yearAlone_Spinner_adapter = new MyAryAdapter(context,
				android.R.layout.simple_spinner_item, yearAlone);

		yearAlone_Spinner_adapter
				.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);
		yearAlone_Spinnner.setAdapter(yearAlone_Spinner_adapter);

		yearAlone_Spinnner.setVisibility(View.VISIBLE);

		// 月份单选按钮
		monthRadioBtn = (RadioButton) view
				.findViewById(R.id.gip_12345_answerstati_radiobutton_month);
		// 月份下拉框
		year_Spinnner = (Spinner) view
				.findViewById(R.id.gip_12345_answerstati_spinner_yeara);

		MyAryAdapter year_Spinner_adapter = new MyAryAdapter(context,
				android.R.layout.simple_spinner_item, yearAlone);
		year_Spinner_adapter
				.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);
		year_Spinnner.setAdapter(year_Spinner_adapter);

		year_Spinnner.setVisibility(View.VISIBLE);

		month_Spinnner = (Spinner) view
				.findViewById(R.id.gip_12345_answerstati_spinner_month);

		MyAryAdapter month_Spinner_adapter = new MyAryAdapter(context,
				android.R.layout.simple_spinner_item, spinnerMonth);
		month_Spinner_adapter
				.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);
		month_Spinnner.setAdapter(month_Spinner_adapter);

		month_Spinnner.setVisibility(View.VISIBLE);

		// 统计按钮
		startStatic_imgBtn = (ImageButton) view
				.findViewById(R.id.gip_12345_answerstati_imagebutton_startstati);
		startStatic_imgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				list_pb.setVisibility(View.VISIBLE);

				// 选中年份单选按钮
				if (yearRadioBtn.isChecked()) {

					yearAlone_Spinnner
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(
										AdapterView<?> adapterView, View view,
										int position, long arg3) {
									if (yearAlone[position].equals("请选择年")) {
										year = 2012;
										month = -1;
									} else {
										year = Integer
												.valueOf(yearAlone[position]);
										month = -1;
									}

								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {

								}
							});
					loadLettersReplyCountData(letter_type, year, month);
				}
				// 选中月份单选按钮
				else if (monthRadioBtn.isChecked()) {

					year_Spinnner
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(
										AdapterView<?> adapterView, View view,
										int position, long arg3) {
									if (yearAlone[position].equals("请选择年")) {
										year = 2012;
									} else {
										year = Integer
												.valueOf(yearAlone[position]);
									}
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {

								}
							});

					month_Spinnner
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(
										AdapterView<?> adapterView, View view,
										int position, long arg3) {
									if (spinnerMonth[position].equals("")) {
										month = 1;
									} else {
										month = Integer
												.valueOf(spinnerMonth[position]);
									}

								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {

								}
							});

					loadLettersReplyCountData(letter_type, year, month);
				}
				// 没有选择统计方式
				else {
					Toast.makeText(context, "请选择一种统计方式！", Toast.LENGTH_LONG)
							.show();
					list_pb.setVisibility(View.GONE);
				}

			}
		});

		mListView = (ListView) view
				.findViewById(R.id.gip_12345_answerstati_listview);
		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_12345_answerstati_listview_pb);

		loadLettersReplyCountData(letter_type, 2013, 1);
		loadAllCountData();
	}

	/**
	 * @类名： MyAryAdapter
	 * @描述： 年、月下拉框适配器类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-2 下午2:52:14
	 * @修改时间：
	 * @修改描述：
	 * 
	 */
	public class MyAryAdapter extends ArrayAdapter<String> {

		Context context;
		String[] items = new String[] {};

		public MyAryAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);

			this.items = objects;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);
			tv.setPadding(2, 5, 2, 5);

			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.LEFT);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(12);

			return convertView;
		}

	}

	/**
	 * 加载所有统计信息
	 * */
	private void loadAllCountData() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(
						context);
				try {
					allCounts = replyStatisticsService
							.getAllCount(Constants.Urls.LETTERS_ALLCOUNT_URL);

					if (null != allCounts) {
						handler.sendEmptyMessage(ALLCOUNT_LOAD_SUCESS);
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

	/**
	 * 统计按钮按后加载 各信箱部门回复统计
	 */
	private void loadLettersReplyCountData(final int letter_type,
			final int year, final int month) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(
						context);
				try {
					letters = replyStatisticsService.getLettersStatistics(
							letter_type, year, month);

					if (null != letters) {
						handler.sendEmptyMessage(LETTERSTATISTICS_LOAD_SUCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);

						Looper.prepare();
						if (month == -1) {
							Toast.makeText(context, "没有" + year + "年的答复率统计数据",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context,
									"没有" + year + "年" + month + "月的答复率统计数据",
									Toast.LENGTH_SHORT).show();
						}
						Looper.loop();

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
	 * 显示所有回复统计信息
	 */
	private void showAllCounts() {

		for (AllCount count : allCounts) {
			if (count.getName().equals("领导信箱"))
				complaint_txtView.setText(String.valueOf(count.getCount())
						+ "封");
			else if (count.getName().equals("咨询投诉"))
				mayorbox_txtView
						.setText(String.valueOf(count.getCount()) + "封");
			else if (count.getName().equals("市长信箱"))
				leaderbox_txtView.setText(String.valueOf(count.getCount())
						+ "封");
		}
	}

	/**
	 * @方法： showReplyLettersList
	 * @描述： 显示回复统计及列表
	 */
	private void showReplyLettersList() {
		adapter = new AnswerStatisticsListAdapter(letters, context);

		if (letters == null || letters.size() == 0) {
			Toast.makeText(context, "对不起，暂无信息", Toast.LENGTH_SHORT).show();
		} else {
			mListView.setAdapter(adapter);
		}
	}
}

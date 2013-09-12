package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.LegislationContentActivity;
import com.wuxi.app.engine.PoliticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment 之 征求意见平台 子fragment --立法征求意见
 * 
 * @author 杨宸 智佳
 * */

public class GIPSuggestLawSuggestionFragment extends RadioButtonChangeFragment {

	private Spinner chooseYear_spinner;

	private ListView mListView;
	private ProgressBar list_pb;
	private PoliticsWrapper politicsWrapper;
	private List<PoliticsWrapper.Politics> politics;
	protected static final String TAG = "GIPSuggestLawSuggestionFragment";
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	public final int POLITICS_TYPE = 0; // politics类型，接口里0 为立法征集，1 为民意征集
	private int startIndex = 0; // 获取话题的起始坐标
	private int endIndex = 20; // 获取话题的结束坐标
	private int year = 2013; // 可选参数，用于立法征求的年份查询，默认为当前年份

	private String[] yearAlone = { "2013", "2012", "2011", "2010" };

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				list_pb.setVisibility(View.INVISIBLE);
				showPoloticsList();
				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected int getLayoutId() {
		return R.layout.gip_suggest_lawsuggestion_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return 0;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return null;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		// 初始化年份下拉框控件变量
		chooseYear_spinner = (Spinner) view
				.findViewById(R.id.gip_suggest_lawsuggest_spinner_chooseYear);

		// 年份下拉框适配器实例
		MyAryAdapter spinner_adapter = new MyAryAdapter(context,
				android.R.layout.simple_spinner_item, yearAlone);
		spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 设置年份下拉框适配器
		chooseYear_spinner.setAdapter(spinner_adapter);
		// 设置年份下拉框选项事件监听
		chooseYear_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long arg3) {
						// 构建征集主题URL
						String url = Constants.Urls.POLITICS_LIST_URL
								+ "?type=" + POLITICS_TYPE + "&start="
								+ startIndex + "&end=" + endIndex + "&year="
								+ yearAlone[position];
						// 加载数据
						loadData(url);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		chooseYear_spinner.setVisibility(View.VISIBLE);

		mListView = (ListView) view
				.findViewById(R.id.gip_suggest_lawsuggest_listView_poloticsList);
		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_suggest_lawsuggest_listView_poloticsList_pb);

		list_pb.setVisibility(View.VISIBLE);

		// 构建立法征求意见数据查询URL
		String url = Constants.Urls.POLITICS_LIST_URL + "?type="
				+ POLITICS_TYPE + "&start=" + startIndex + "&end=" + endIndex
				+ "&year=" + year;

		loadData(url);

	}

	/**
	 * @方法： loadData
	 * @描述： 加载立法征求意见数据
	 * @param url
	 */
	public void loadData(final String url) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				PoliticsService politicsService = new PoliticsService(context);
				try {

					politicsWrapper = politicsService.getPoliticsWrapper(url);
					if (null != politicsWrapper) {
						politics = politicsWrapper.getData();
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

	/**
	 * @方法： showPoloticsList
	 * @描述： 显示立法征求意见列表
	 */
	public void showPoloticsList() {
		PoliticsListViewAdapter adapter = new PoliticsListViewAdapter();
		if (politics == null || politics.size() == 0) {
			Toast.makeText(context, "对不起，暂无立法征求意见信息", Toast.LENGTH_SHORT)
					.show();
		} else {
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(adapter);
		}
	}

	/**
	 * @类名： MyAryAdapter
	 * @描述： 按年份筛选征集主题下拉框适配器类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-2 下午4:48:31
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

			return convertView;
		}

	}

	/**
	 * @类名： PoliticsListViewAdapter
	 * @描述： 立法征求意见列表适配器
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-2 下午4:44:23
	 * @修改时间：
	 * @修改描述：
	 * 
	 */
	public class PoliticsListViewAdapter extends BaseAdapter implements
			OnItemClickListener {

		@Override
		public int getCount() {
			return politics.size();
		}

		@Override
		public Object getItem(int position) {
			return politics.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView title_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gip_suggest_law_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.gip_suggest_textview_title);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title_text.setText(politics.get(position).getTitle());

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View arg1,
				int position, long arg3) {
			Politics politics = (Politics) adapterView
					.getItemAtPosition(position);

			Intent intent = new Intent(getActivity(),
					LegislationContentActivity.class);
			intent.putExtra("politics", politics);

			MainTabActivity.instance.addView(intent);
		}
	}
}

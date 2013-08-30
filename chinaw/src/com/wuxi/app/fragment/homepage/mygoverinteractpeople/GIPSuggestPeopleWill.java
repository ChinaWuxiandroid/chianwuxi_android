package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.PepoleIdeaCollectActivity;
import com.wuxi.app.engine.PoliticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment 之 征求意见平台 子fragment --民意征集
 * 
 * @author 杨宸 智佳
 * */

@SuppressLint("ShowToast")
public class GIPSuggestPeopleWill extends RadioButtonChangeFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ListView mListView;
	private ProgressBar list_pb;
	private PoliticsWrapper politicsWrapper;
	private List<PoliticsWrapper.Politics> politics;
	protected static final String TAG = "GIPSuggestPeopleWill";
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	public final int POLITICS_TYPE = 1; // politics类型，接口里0 为立法征集，1 为民意征集
	private int startIndex = 0; // 获取话题的起始坐标
	private int endIndex = 5; // 获取话题的结束坐标
	private int passed = 0; // 是否过期，可选参数，默认值是0 0: 当前 1:以往

	private final int[] radioButtonIds = {
			R.id.gip_suggest_peoplewill_radioButton_now,
			R.id.gip_suggest_peoplewill_radioButton_before };

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
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_suggest_peoplewill_radioButton_now:
			passed = 0;
			init();
			break;

		case R.id.gip_suggest_peoplewill_radioButton_before:
			passed = 1;
			init();
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_suggest_peoplewill_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_suggest_peoplewill_radioGroup;
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

		mListView = (ListView) view
				.findViewById(R.id.gip_suggest_peoplewill_listview);
		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_suggest_peoplewill_listview_pb);

		list_pb.setVisibility(View.VISIBLE);
		loadData();

	}

	public void loadData() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				PoliticsService politicsService = new PoliticsService(context);
				try {
					politicsWrapper = politicsService.getPoliticsWrapper(
							Constants.Urls.POLITICS_LIST_URL, POLITICS_TYPE,
							startIndex, endIndex, passed);
					if (null != politicsWrapper) {
						politics = politicsWrapper.getData();
						System.out.println("获取列表成功");
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

	public void showPoloticsList() {

		PoliticsListViewAdapter adapter = new PoliticsListViewAdapter();
		if (politics == null || politics.size() == 0) {
			Toast.makeText(context, "对不起，暂无热点话题信息", 2000).show();
		} else {
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(adapter);
		}
	}

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
			public TextView beginTime_text;
			public TextView endTime_text;
			public TextView depName_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gip_suggest_peopelwill_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.gip_suggest_peoplewill_listitem_tile);
				viewHolder.beginTime_text = (TextView) convertView
						.findViewById(R.id.gip_suggest_peoplewill_textview_begintime);
				viewHolder.endTime_text = (TextView) convertView
						.findViewById(R.id.gip_suggest_peoplewill_textview_endtime);
				viewHolder.depName_text = (TextView) convertView
						.findViewById(R.id.gip_suggest_peoplewill_textview_depname);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title_text.setText(politics.get(position).getTitle());
			viewHolder.beginTime_text.setText(politics.get(position)
					.getBeginTime());
			viewHolder.endTime_text
					.setText(politics.get(position).getEndTime());
			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View arg1,
				int position, long arg3) {
			Politics politics = (Politics) adapterView
					.getItemAtPosition(position);
			
			Intent intent = new Intent(getActivity(), PepoleIdeaCollectActivity.class);
			intent.putExtra("politics", politics);
			
			MainTabActivity.instance.addView(intent);
		}

	}

}

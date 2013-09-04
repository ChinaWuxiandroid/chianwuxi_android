package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.InternetSurveySerivce;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.InternetSurveyWrapper;
import com.wuxi.domain.InternetSurveyWrapper.InternetSurvey;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;


/**
 * 我的政民互动  主Fragment  之 征求意见平台  子fragment  --网上调查
 * @author 杨宸 智佳
 * */
public class GIPSuggestSurveyFragment extends RadioButtonChangeFragment{
	
	protected static final String TAG = "GIPSuggestSurveyFragment";
	
	private final  int[] radioButtonIds={
			R.id.gip_suggest_survey_radioButton_now,
			R.id.gip_suggest_survey_radioButton_before
	};
	
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;
	
	private ListView mListView;
	private ProgressBar list_pb;
	
	private int startIndex=0;         //获取话题的起始坐标
	private int endIndex=8;			//获取话题的结束坐标
	private int type=1; 
	
	private InternetSurveyWrapper internetSurveyWrapper= null;
	private List<InternetSurvey> internetSurveys = null; 
	  

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

		case R.id.gip_suggest_survey_radioButton_now:
			type = 1;
			init();
			break;

		case R.id.gip_suggest_survey_radioButton_before:	
			type = 0;
			init();
			break;
		}
	}

	
	
	@Override
	protected int getLayoutId() {
		return R.layout.gip_suggest_survey_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_suggest_survey_radioGroup;
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
		mListView=(ListView) view.findViewById(R.id.gip_suggest_survey_listview);
		list_pb=(ProgressBar)view.findViewById(R.id.gip_suggest_survey_progress);

		list_pb.setVisibility(View.VISIBLE);
		
		loadData();
	}
	
	public void loadData(){

		new Thread(new Runnable() {

			@Override
			public void run() {

				InternetSurveySerivce internetSurveySerivce = new InternetSurveySerivce(context);
				try {
					internetSurveyWrapper = internetSurveySerivce.getInternetSurveyWrapper(type, startIndex, endIndex);
					if (null != internetSurveyWrapper) {
						internetSurveys=internetSurveyWrapper.getInternetSurveys();
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
		}
				).start();
	}
	
	public void showPoloticsList(){
		PoliticsListViewAdapter adapter=new PoliticsListViewAdapter();
		if(internetSurveys==null||internetSurveys.size()==0){
			Toast.makeText(context, "对不起，暂无热点话题信息", 2000).show();
		}
		else{
			mListView.setAdapter(adapter);
		}
	}
	
	public class PoliticsListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return internetSurveys.size();
		}

		@Override
		public Object getItem(int position) {
			return internetSurveys.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView title_text;
			public TextView beginTime_text;
			public TextView endTime_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.internetsurvey_list_layout, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.internet_content_text);
				viewHolder.beginTime_text = (TextView) convertView
						.findViewById(R.id.internet_begintime_text);
				viewHolder.endTime_text = (TextView) convertView
						.findViewById(R.id.internet_endtime_text);

				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			
			viewHolder.title_text.setText(internetSurveys.get(position).getTitle());	
			viewHolder.beginTime_text.setText(internetSurveys.get(position).getCreateDate());
			viewHolder.endTime_text.setText(internetSurveys.get(position).getEndDate());
			
			return convertView;
		}

	}


}

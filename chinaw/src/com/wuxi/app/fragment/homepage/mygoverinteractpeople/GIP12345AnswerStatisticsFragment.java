package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.AllCount;
import com.wuxi.domain.StatisticsLetter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 *12345来信办理平台  主Fragment --答复率统计  fragment
 * @author 杨宸 智佳
 * */

public class GIP12345AnswerStatisticsFragment extends RadioButtonChangeFragment{

	private TextView complaint_txtView;    //咨询投诉
	private TextView mayorbox_txtView;    //市长信箱
	private TextView leaderbox_txtView;     //领导信箱

	private Spinner yearAlone_Spinnner;
	private Spinner year_Spinnner;
	private Spinner month_Spinnner;

	private ImageButton startStatic_imgBtn;   //统计

	private ListView mListView;
	protected static final String TAG = "GIP12345AnswerStatisticsFragment";
	private static final int ALLCOUNT_LOAD_SUCESS = 0;   //答复率总数统计
	private static final int LETTERSTATISTICS_LOAD_SUCESS = 1;   //各部门答复率统计
	private static final int DATA_LOAD_ERROR = 2;
	public static final int REPLY_LETTER_TYPE_MAYORBOX=1;   //市长信箱
	public static final int REPLY_LETTER_TYPE_COMPLAINT=2;   //咨询投诉
	public static final int REPLY_LETTER_TYPE_LEADERBOX=3;   //领导信箱

	List<AllCount> allCounts;
	List<StatisticsLetter> letters;

	private int letter_type=REPLY_LETTER_TYPE_MAYORBOX;   //默认为市长信箱

	private int year=2012;         //年份
	private int month=1;			//月份

	private final  int[] radioButtonIds={
			R.id.gip_12345_answerstati_radioButton_mayorBox,
			R.id.gip_12345_answerstati_radioButton_complaint,
			R.id.gip_12345_answerstati_radioButton_leaderBox	
	};	

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case ALLCOUNT_LOAD_SUCESS:
				showAllCounts();
				break;
			case LETTERSTATISTICS_LOAD_SUCESS:
				showReplyLettersList();
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

		case R.id.gip_12345_answerstati_radioButton_mayorBox:
			letter_type=REPLY_LETTER_TYPE_MAYORBOX;
			break;

		case R.id.gip_12345_answerstati_radioButton_complaint:	
			letter_type=REPLY_LETTER_TYPE_COMPLAINT;
			break;

		case R.id.gip_12345_answerstati_radioButton_leaderBox:	
			letter_type=REPLY_LETTER_TYPE_LEADERBOX;
			break;


		}
	}
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_12345_answerstati_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_12345_answerstati_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		// TODO Auto-generated method stub
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected void init() {
		complaint_txtView=(TextView)view.findViewById(R.id.gip_12345_answerstati_textview_complaintcount);
		mayorbox_txtView=(TextView)view.findViewById(R.id.gip_12345_answerstati_textview_mayorbox);
		leaderbox_txtView=(TextView)view.findViewById(R.id.gip_12345_answerstati_textview_leaderbox);

		yearAlone_Spinnner=(Spinner)view.findViewById(R.id.gip_12345_answerstati_spinner_yearalone);
		ArrayAdapter yearAlone_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.spinnerYear,R.layout.my_spinner_small_item);  
		yearAlone_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);  
		yearAlone_Spinnner.setAdapter(yearAlone_Spinner_adapter);  
		yearAlone_Spinnner.setVisibility(View.VISIBLE); 

		year_Spinnner=(Spinner)view.findViewById(R.id.gip_12345_answerstati_spinner_yeara);

		ArrayAdapter year_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.spinnerYear, R.layout.my_spinner_small_item);  
		year_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);  
		year_Spinnner.setAdapter(year_Spinner_adapter);  
		year_Spinnner.setVisibility(View.VISIBLE); 


		month_Spinnner=(Spinner)view.findViewById(R.id.gip_12345_answerstati_spinner_month);
		ArrayAdapter month_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.spinnerMonth, R.layout.my_spinner_small_item);  
		month_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_small_dropdown_item);  
		month_Spinnner.setAdapter(month_Spinner_adapter);  
		month_Spinnner.setVisibility(View.VISIBLE); 

		startStatic_imgBtn=(ImageButton)view.findViewById(R.id.gip_12345_answerstati_imagebutton_startstati);
		startStatic_imgBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadLettersReplyCountData();
			}
		});
		
		mListView=(ListView) view.findViewById(R.id.gip_12345_answerstati_listview);

		loadAllCountData();	
	}

	/**
	 * 先加载所有统计信息，下面的信息，按 统计按钮后加载
	 * */
	public void loadAllCountData(){
		//		if (CacheUtil.get(menuItem.getChannelId()) != null) {// 从缓存获取
		//
		//			titleChannels = (List<Channel>) CacheUtil.get(menuItem
		//					.getChannelId());
		//			showTitleData();
		//			return;
		//		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(context);
				try {
					allCounts= replyStatisticsService.getAllCount
							(Constants.Urls.LETTERS_ALLCOUNT_URL);
					if (null != allCounts) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				).start();
	}

	/**
	 * 统计按钮按后加载 各信箱部门回复统计
	 * */
	public void loadLettersReplyCountData(){
		//		if (CacheUtil.get(menuItem.getChannelId()) != null) {// 从缓存获取
		//
		//			titleChannels = (List<Channel>) CacheUtil.get(menuItem
		//					.getChannelId());
		//			showTitleData();
		//			return;
		//		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(context);
				try {
					letters= replyStatisticsService.getLettersStatistics
							(Constants.Urls.LETTERS_STATISTICS_URL,letter_type,year,month);
					if (null != letters) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来

						handler.sendEmptyMessage(LETTERSTATISTICS_LOAD_SUCESS);

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				).start();
	}


	/*
	 * 显示所有回复统计信息
	 * */
	public void showAllCounts(){

		for(AllCount count:allCounts){
			if(count.getName().equals("领导信箱"))
				complaint_txtView.setText(String.valueOf(count.getCount())+"封");
			else if(count.getName().equals("咨询投诉"))
				mayorbox_txtView.setText(String.valueOf(count.getCount())+"封");
			else if(count.getName().equals("市长信箱"))
				leaderbox_txtView.setText(String.valueOf(count.getCount())+"封");
		}
	}

	public void showReplyLettersList(){
		LettersListViewAdapter adapter=new LettersListViewAdapter();
	
		if(letters==null||letters.size()==0){
			Toast.makeText(context, "对不起，暂无信息", 2000).show();
		}
		else{
			mListView.setAdapter(adapter);
		}
	}

	public class LettersListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return letters.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return letters.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			public TextView depName_text;
			public TextView acceptedNum_text;
			public TextView replyNum_text;
			public TextView replyRate_text;
			public TextView replyDay_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.gip_12345_answerstati_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.depName_text = (TextView) convertView
						.findViewById(R.id.gip_12345_answerstati_depname);
				viewHolder.acceptedNum_text = (TextView) convertView
						.findViewById(R.id.gip_12345_answerstati_acceptedNum);
				viewHolder.replyNum_text = (TextView) convertView
						.findViewById(R.id.gip_12345_answerstati_replyNum);
				viewHolder.replyRate_text = (TextView) convertView
						.findViewById(R.id.gip_12345_answerstati_replyRate);
				viewHolder.replyDay_text = (TextView) convertView
						.findViewById(R.id.gip_12345_answerstati_replyDay);

				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.depName_text.setText(letters.get(position).getDepname());
			viewHolder.acceptedNum_text.setText(String.valueOf(letters.get(position).getAcceptedNum()));
			viewHolder.replyNum_text.setText(String.valueOf(letters.get(position).getReplyNum()));
			viewHolder.replyRate_text.setText(String.valueOf(letters.get(position).getReplyRate()));
			viewHolder.replyDay_text.setText(letters.get(position).getReplyDay());

			return convertView;
		}

	}

}

package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
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

import com.wuxi.app.R;
import com.wuxi.app.engine.HotReviewService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.GIPRadioButtonStyleChange;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.HotReviewWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 热点话题
 * @author 杨宸 智佳
 * */
public class GoverInterPeopleHotReviewFragment extends RadioButtonChangeFragment{

	
	
	private ListView mListView;
	private ProgressBar list_pb;
	private HotReviewWrapper hotReviewWrapper;
	private List<HotReviewWrapper.HotReview> hotReviews;
	protected static final String TAG = "GoverInterPeopleHotReviewFragment";
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;
	
	public final int HOTREVIEW_TYPE_NOW=0;    //话题类型  当前话题
	public final int HOTREVIEW_TYPE_BEFORE=1;//话题类型  以往话题
	private int reviewType=HOTREVIEW_TYPE_NOW;   //默认为当前话题 
	private int startIndex=0;         //获取话题的起始坐标
	private int endIndex=5;			//获取话题的结束坐标
	
	private final  int[] radioButtonIds={
			R.id.goverinterpeople_hottopic_radioButton_now,
			R.id.goverinterpeople_hottopic_radioButton_before
	};


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
				showHotReviews();
				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_hottopic_radioButton_now:
			reviewType=HOTREVIEW_TYPE_NOW;
			init();
			break;

		case R.id.goverinterpeople_hottopic_radioButton_before:			
			reviewType=HOTREVIEW_TYPE_BEFORE;
			init();
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.goverinterpeople_hotreview_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.goverinterpeople_hottopic_radioGroup;
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
		// TODO Auto-generated method stub
		mListView=(ListView) view.findViewById(R.id.gip_suggest_people_listview);
		list_pb=(ProgressBar)view.findViewById(R.id.gip_suggest_people_listview_pb);

		list_pb.setVisibility(View.VISIBLE);
		loadData();
	}
	
	public void loadData(){
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

				HotReviewService hotReviewService = new HotReviewService(context);
				try {
					hotReviewWrapper = hotReviewService.getHotReviewWrapper(Constants.Urls.HOTREVIEW_LIST_URL,reviewType,startIndex,endIndex);
					if (null != hotReviewWrapper) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来
						hotReviews=hotReviewWrapper.getData();
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
	
	public void showHotReviews(){
		BaseSlideFragment baseSlideFragment=this.baseSlideFragment;
		HotReviewListViewAdapter adapter=new HotReviewListViewAdapter(baseSlideFragment);
		
		if(hotReviews==null||hotReviews.size()==0){
			Toast.makeText(context, "对不起，暂无热点话题信息", 2000).show();
		}
		else{
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(adapter);
		}
	}

	public class HotReviewListViewAdapter extends BaseAdapter implements OnItemClickListener{
		BaseSlideFragment baseSlideFragment;
		public HotReviewListViewAdapter(BaseSlideFragment baseSlideFragment){
			this.baseSlideFragment=baseSlideFragment;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return hotReviews.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return hotReviews.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		class ViewHolder {
			public TextView title_text;
			public TextView startTime_text;
			public TextView endTime_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.gip_hotreview_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.gip_hottopic_listview_title);
				viewHolder.startTime_text = (TextView) convertView
						.findViewById(R.id.gip_hottopic_listview_beginTime);
				viewHolder.endTime_text = (TextView) convertView
						.findViewById(R.id.gip_hottopic_listview_endTime);
				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title_text.setText(hotReviews.get(position).getTitle());
			viewHolder.startTime_text.setText(hotReviews.get(position).getStartTime());
			viewHolder.endTime_text.setText(hotReviews.get(position).getEndTime());

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			baseSlideFragment.slideLinstener.replaceFragment(null, position,
					Constants.FragmentName.HOTREVIEW_CONTENT_FRAGMENT, null);

		}

	
	}
}

package com.wuxi.app.fragment.homepage.goverpublicmsg;

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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ReplyStatisticsService;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.StatisticsLetter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： GoverMsgInterMailStatisticsFragment
 * @描述： 政府信息公开 依申请公开 网上办件统计 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-22 上午11:16:57
 * @修改时间： 
 * @修改描述：
 */
public class GoverMsgInterMailStatisticsFragment extends BaseFragment{
	
	private View view;
	private LayoutInflater mInflater;
	private Context context;
	private static final int LETTERSTATISTICS_LOAD_SUCESS = 1;   //各部门答复率统计
	private static final int DATA_LOAD_ERROR = 2;
	
	private ListView mListView;// ListView
	private ProgressBar listview_pb;
	
	private List<StatisticsLetter> letters;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case LETTERSTATISTICS_LOAD_SUCESS:
				listview_pb.setVisibility(View.INVISIBLE);
				showReplyLettersList();
				break;
			case DATA_LOAD_ERROR:
				listview_pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.govermsg_internetstatistics_layout, null);
		mInflater = inflater;
		context=getActivity();
		
		initView();
		return view;
	}

	/**
	 * @方法： initView
	 * @描述： 初始化视图
	 */
	private void initView(){
		mListView = (ListView) view.findViewById(R.id.govermsg_internetstatistics_listview);
		listview_pb=(ProgressBar)view.findViewById(R.id.govermsg_internetstatistics_progressbar);
		
		listview_pb.setVisibility(View.VISIBLE);
		loadData();
	}
	
	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 */
	private void loadData(){
		new Thread(new Runnable() {

			@Override
			public void run() {

				ReplyStatisticsService replyStatisticsService = new ReplyStatisticsService(context);
				try {
					letters= replyStatisticsService.getAllLettersStatistics
							(Constants.Urls.INTERNET_LETTERS_STATISTICS_URL);
					if (null != letters) {
						handler.sendEmptyMessage(LETTERSTATISTICS_LOAD_SUCESS);

					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}

				} catch (NetException e) {
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
	
	/**
	 * @方法： showReplyLettersList
	 * @描述： 显示回复信件列表
	 */
	private void showReplyLettersList(){
		mListView.setAdapter(new LettersListViewAdapter());
	}
	
	/**
	 * @类名： LettersListViewAdapter
	 * @描述： 信件列表适配器
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-11 下午5:43:35
	 * @修改时间： 
	 * @修改描述：
	 */
	public class LettersListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return letters.size();
		}

		@Override
		public Object getItem(int position) {
			return letters.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView depName_text;
			public TextView acceptedNum_text;
			public TextView replyNum_text;
			public TextView replyRate_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.govermsg_internet_statistics_list_item, null);

				viewHolder = new ViewHolder();

				viewHolder.depName_text = (TextView) convertView
						.findViewById(R.id.govermsg_answerstati_depname);
				viewHolder.acceptedNum_text = (TextView) convertView
						.findViewById(R.id.govermsg_answerstati_acceptedNum);
				viewHolder.replyNum_text = (TextView) convertView
						.findViewById(R.id.govermsg_answerstati_replyNum);
				viewHolder.replyRate_text = (TextView) convertView
						.findViewById(R.id.govermsg_answerstati_replyRate);

				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.depName_text.setText(letters.get(position).getDepname());
			viewHolder.acceptedNum_text.setText(String.valueOf(letters.get(position).getAcceptedNum()));
			viewHolder.replyNum_text.setText(String.valueOf(letters.get(position).getReplyNum()));
			viewHolder.replyRate_text.setText(String.valueOf(letters.get(position).getReplyRate()));

			return convertView;
		}

	}
}

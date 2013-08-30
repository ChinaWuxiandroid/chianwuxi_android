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

public class GoverMsgInterMailStatisticsFragment extends BaseFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;
	private static final int LETTERSTATISTICS_LOAD_SUCESS = 1;   //各部门答复率统计
	private static final int DATA_LOAD_ERROR = 2;
	
	private ListView mListView;// ListView
	private ProgressBar listview_pb;
	
	List<StatisticsLetter> letters;
	
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

	public void initView(){
		mListView = (ListView) view.findViewById(R.id.govermsg_internetstatistics_listview);
		listview_pb=(ProgressBar)view.findViewById(R.id.govermsg_internetstatistics_progressbar);
		
		listview_pb.setVisibility(View.VISIBLE);
		loadData();
	}
	
	public void loadData(){
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
	
	public void showReplyLettersList(){
		mListView.setAdapter(new LettersListViewAdapter());
	}
	
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

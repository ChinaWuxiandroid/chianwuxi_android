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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;


/**
 * 12345来信办理平台 主Fragment --市长信箱  fragment
 * @author 杨宸 智佳
 * */


public class GIP12345MayorMaiBoxFragment  extends RadioButtonChangeFragment{

	private ListView mListView;
	private LetterWrapper letterWrapper;
	private List<LetterWrapper.Letter> letters;
	protected static final String TAG = "GIP12345MayorMaiBoxFragment";
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	private int startIndex=0;         //获取话题的起始坐标
	private int endIndex=5;			//获取话题的结束坐标

	private final  int[] radioButtonIds={
			R.id.gip_12345_mayorbox_radioButton_mailList,
			R.id.gip_12345_mayorbox_radioButton_mustKonwMail,
			R.id.gip_12345_mayorbox_radioButton_mayorBoxRule
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
				showLettersList();
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

		case R.id.gip_12345_mayorbox_radioButton_mailList:
			//			init();
			break;

		case R.id.gip_12345_mayorbox_radioButton_mustKonwMail:	
			break;


		case R.id.gip_12345_mayorbox_radioButton_mayorBoxRule:	
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_12345_mayorbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_12345_mayorbox_radioGroup;
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
		mListView=(ListView) view.findViewById(R.id.gip_12345_mayorbox_listView);
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

				LetterService letterService = new LetterService(context);
				try {
					letterWrapper = letterService.getLetterLitstWrapper
							(Constants.Urls.MAYOR_MAILBOX_URL,startIndex,endIndex);
					if (null != letterWrapper) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来
						letters=letterWrapper.getData();

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


	public void showLettersList(){
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
			public TextView title_text;
			public TextView code_text;
			public TextView type_text;
			public TextView answerDate_text;
			public TextView readCount_text;
			public TextView appraise_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.gip_12345_mayorbox_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.gip_12345_mayorbox_tilte);
				viewHolder.code_text = (TextView) convertView
						.findViewById(R.id.gip_12345_mayorbox_code);
				viewHolder.type_text = (TextView) convertView
						.findViewById(R.id.gip_12345_mayorbox_type);
				viewHolder.answerDate_text = (TextView) convertView
						.findViewById(R.id.gip_12345_mayorbox_answerDate);
				viewHolder.readCount_text = (TextView) convertView
						.findViewById(R.id.gip_12345_mayorbox_readcount);
				viewHolder.appraise_text = (TextView) convertView
						.findViewById(R.id.gip_12345_mayorbox_appraise);

				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title_text.setText(letters.get(position).getTitle());
			viewHolder.code_text.setText(letters.get(position).getCode());
			viewHolder.type_text.setText(letters.get(position).getType());
//			viewHolder.answerDate_text.setText(letters.get(position).getAnswerdate());
			viewHolder.readCount_text.setText(String.valueOf(letters.get(position).getReadcount()));
			viewHolder.appraise_text.setText(letters.get(position).getAppraise());

			return convertView;
		}

	}
}

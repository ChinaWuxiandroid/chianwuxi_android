package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.OpenTelService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.OpenTel;
import com.wuxi.domain.OpenTelWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 之  公开电话模块
 * 
 * @author 杨宸 智佳
 * */

public class GoverInterPeopleOpenTelFragment extends RadioButtonChangeFragment implements OnItemClickListener{
	private ListView mListView;
	private ProgressBar list_pb;
	private OpenTelWrapper openTelWrapper;
	private List<OpenTel> tels;
	protected static final String TAG = "GoverInterPeopleOpenTelFragment";
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;


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
				showTels();
				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};


	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.goverinterpeople_opentel_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int[] getRadioButtonIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getContentFragmentId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void init() {
		mListView=(ListView) view.findViewById(R.id.gip_opentel_listview);
		list_pb=(ProgressBar)view.findViewById(R.id.gip_opentel_listview_pb);

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

				OpenTelService openTelService = new OpenTelService(context);
				try {
					openTelWrapper = openTelService.getOpenTelWrapper(Constants.Urls.OPENTEL_URL);
					if (null != openTelWrapper) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来
						tels=openTelWrapper.getData();
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

	public void showTels(){
		OpenTelListViewAdapter adapter=new OpenTelListViewAdapter();
		if(tels==null||tels.size()==0){
			Toast.makeText(context, "对不起，暂无公开电话信息", 2000).show();
		}
		else{
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(this);
		}
	}




	public class OpenTelListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tels.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tels.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			public TextView depname_text;
			public TextView tel_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(convertView==null){
				convertView = mInflater.inflate(
						R.layout.gip_opentel_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.depname_text = (TextView) convertView
						.findViewById(R.id.gip_opentel_listitem_depname);
				viewHolder.tel_text = (TextView) convertView
						.findViewById(R.id.gip_opentel_listitem_tel);
				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.depname_text.setText(tels.get(position).getDepname());
			viewHolder.tel_text.setText(tels.get(position).getTel());

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		dial(tels.get(position).getTel());	
	}
	
	public void dial(final String phoneNumber){
		new AlertDialog.Builder(context)  
		                .setTitle("提示")
		                .setMessage("是否确定拨号\n"+phoneNumber+"  ?")
		                .setPositiveButton("确定", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Uri telUri = Uri.parse("tel:"+phoneNumber);
								Intent intent= new Intent(Intent.ACTION_DIAL, telUri);
								getActivity().startActivity(intent);
							}
						})
		                .setNegativeButton("取消", new OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								 dialog.dismiss();
							}})
		                .show();
	} 

}

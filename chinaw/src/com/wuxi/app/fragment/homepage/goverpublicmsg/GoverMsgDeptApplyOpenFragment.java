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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ApplyDeptService;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.ApplyDept;
import com.wuxi.exception.NetException;

public class GoverMsgDeptApplyOpenFragment extends BaseFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;
	
	private List<ApplyDept> depts;
	private final static int LOAD_SUCCESS=1;
	private final static int LOAD_FAILED=0;
	
	private ListView content_listView;
	private ProgressBar progressBar;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case LOAD_SUCCESS:
				progressBar.setVisibility(ProgressBar.INVISIBLE);
				showDeptList();		
				break;
			case LOAD_FAILED:
				progressBar.setVisibility(ProgressBar.INVISIBLE);
				Toast.makeText(context, "提交失败！", 2000).show();
				break;
			}
		};
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.govermsg_deptapply_layout, null);
		mInflater = inflater;
		context = getActivity();
		
		initView();
		return view;
	}

	public void initView(){
		content_listView=(ListView)view.findViewById(R.id.govermsg_deptapply_listview);
		progressBar=(ProgressBar)view.findViewById(R.id.govermsg_deptapply_progressbar);
		
		progressBar.setVisibility(ProgressBar.VISIBLE);
		loadData();
	}
	
	public void loadData(){
		new Thread(new Runnable() {

			@Override
			public void run() {

				ApplyDeptService applyDeptService = new ApplyDeptService(context);
				try {
					depts = applyDeptService.getDepts(Constants.Urls.APPLYDEPT_URL);
					if (null != depts) {
						//						CacheUtil.put(menuItem.getChannelId(), titleChannels);// 缓存起来
						handler.sendEmptyMessage(LOAD_SUCCESS);
					} else {
						Message message = handler.obtainMessage();
						message.obj = "error";
						handler.sendEmptyMessage(LOAD_FAILED);
					}

				} catch (NetException e) {
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(LOAD_FAILED);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				).start();
	}
	
	public void showDeptList(){
		content_listView.setAdapter(new ApplyDeptAdapter());
	}
	
	
	public class ApplyDeptAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return depts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return depts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			public TextView title_text;
			public ImageButton download_imgbtn;
			public ImageButton guide_imgbtn;
			public ImageButton apply_imgbtn;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.govermsg_deptapply_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.govermsg_deptapply_item_title);
				viewHolder.download_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_download);
				viewHolder.guide_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_guide);
				viewHolder.apply_imgbtn = (ImageButton) convertView
						.findViewById(R.id.govermsg_deptapply_item_apply);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.title_text.setText(depts.get(position).getDepName());
			return convertView;

		}
		
	}
}

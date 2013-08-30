/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
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

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.PartLeaderMailService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 12345来信办理平台 部门领导信箱 各部门领导信箱 列表碎片
 * 
 * @author 智佳 罗森
 * 
 */
public class PartLeaderBoxListFragment extends BaseFragment {

	protected static final String TAG = "PartLeaderMailListFragment";
	
	private static final int HIDEN_CONTENT_ID = R.id.gip_12345_leaderbox_fragment;

	private View view = null;
	private Context context = null;

	private ListView mListView;
	private ProgressBar list_pb;

	private PartLeaderMailWrapper leaderMailWrapper = null;
	private List<PartLeaderMail> leaderMails = null;

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
				showLettersList();
				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.part_leader_mail_list_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {

		
		
		mListView = (ListView) view
				.findViewById(R.id.gip_12345_leaderbox_listview);
		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_12345_leaderbox_progress);

		list_pb.setVisibility(View.VISIBLE);
		loadData();
	}

	/**
	 * 加载数据
	 */
	public void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				PartLeaderMailService mailService = new PartLeaderMailService(
						context);
				try {
					leaderMailWrapper = mailService.getPartLeaderMailWrapper();
					if (null != leaderMailWrapper) {
						leaderMails = leaderMailWrapper.getPartLeaderMails();
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
	 * 显示列表
	 */
	public void showLettersList() {
		PortLeaderListAdapter adapter = new PortLeaderListAdapter();
		if (leaderMails == null || leaderMails.size() == 0) {
			Toast.makeText(context, "对不起，暂无信息", 2000).show();
		} else {
			
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(adapter);
		}
	}

	/**
	 * 各部门领导信箱列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	private class PortLeaderListAdapter extends BaseAdapter implements
			OnItemClickListener {

		@Override
		public int getCount() {
			return leaderMails.size();
		}

		@Override
		public Object getItem(int position) {
			return leaderMails.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView portNameText;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.leader_mail_box_list_layout, null);

				holder = new ViewHolder();

				holder.portNameText = (TextView) convertView
						.findViewById(R.id.port_name_text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.portNameText.setText(leaderMails.get(position).getDepname());

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View arg1,
				int position, long arg3) {
			
			PartLeaderMailFragment listFragment = new PartLeaderMailFragment();
			listFragment.setBaseSlideFragment(PartLeaderBoxListFragment.this.baseSlideFragment);
			listFragment.setLeaderMail(leaderMails.get(position));
			bindFragment(listFragment);
		}
		
		/**
		 * 绑定碎片
		 * @param fragment
		 */
		private void bindFragment(Fragment fragment) {
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(HIDEN_CONTENT_ID, fragment);
			ft.commitAllowingStateLoss();
		}

	}

}

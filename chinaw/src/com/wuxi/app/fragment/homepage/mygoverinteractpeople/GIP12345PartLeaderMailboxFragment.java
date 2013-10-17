package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.PartLeaderMailService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.PartLeaderMailWrapper;
import com.wuxi.domain.PartLeaderMailWrapper.PartLeaderMail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 12345来信办理平台 主Fragment --部门领导信箱 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345PartLeaderMailboxFragment extends
		RadioButtonChangeFragment {

	private static final String TAG = "GIP12345PartLeaderMailboxFragment";
	
	private int contentType = 0;

	private static final int HIDEN_CONTENT_ID = R.id.gip_leaderbox_web_fragment;

	private final int[] radioButtonIds = {
			R.id.gip_12345_leaderbox_radioButton_lederBoxList,
			R.id.gip_12345_leaderbox_radioButton_mustKonwMail };

	private ListView mListView;
	private ProgressBar list_pb;
	
	private RadioGroup radioGroup;
	
	private FrameLayout webLayout;
	private FrameLayout notwebLayout;

	private PartLeaderMailWrapper leaderMailWrapper = null;
	private List<PartLeaderMail> leaderMails = null;

	private static final int DATA_LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				list_pb.setVisibility(View.INVISIBLE);
				showList();
				break;
			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		
		radioGroup = (RadioGroup) view.findViewById(R.id.gip_12345_leaderbox_radioGroup);
		
		webLayout = (FrameLayout) view.findViewById(R.id.gip_leaderbox_web_fragment);
		notwebLayout = (FrameLayout) view.findViewById(R.id.gip_12345_leaderbox_fragment);

		mListView = (ListView) view
				.findViewById(R.id.gip_12345_leader_listview);
		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_12345_leader_progress);

		list_pb.setVisibility(View.VISIBLE);
		loadData();
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				PartLeaderMailService mailService = new PartLeaderMailService(
						context);
				try {
					leaderMailWrapper = mailService.getPartLeaderMailWrapper();
					if (null != leaderMailWrapper) {
						leaderMails = leaderMailWrapper.getPartLeaderMails();
						handler.sendEmptyMessage(DATA_LOAD_SUCESS);
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
	private void showList() {
		PortLeaderListAdapter adapter = new PortLeaderListAdapter();
		if (leaderMails == null || leaderMails.size() == 0) {
			Toast.makeText(context, "对不起，暂无信息", Toast.LENGTH_SHORT).show();
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
			radioGroup.setVisibility(View.GONE);
			webLayout.setVisibility(View.VISIBLE);
			notwebLayout.setVisibility(View.GONE);
			PartLeaderMailFragment listFragment = new PartLeaderMailFragment();
			listFragment.setLeaderMail(leaderMails.get(position));
			bindFragment(listFragment);
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_leaderbox_radioButton_lederBoxList:
			contentType = 0;
			webLayout.setVisibility(View.GONE);
			notwebLayout.setVisibility(View.VISIBLE);
			break;

		case R.id.gip_12345_leaderbox_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			webLayout.setVisibility(View.VISIBLE);
			notwebLayout.setVisibility(View.GONE);
			break;

		}

	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_leadermailbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_leaderbox_radioGroup;
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
		initLayout();
		
	}

	/**
	 * 切换界面
	 * 
	 * @param type
	 */
	private void changeContent(int type) {
		GoverInterPeopleWebFragment goverInterPeopleWebFragment = new GoverInterPeopleWebFragment();
		switch (type) {
		case 0:
			init();
			break;
		case 1:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/wap/zmhd/6148278.shtml?backurl=false");
			bindFragment(goverInterPeopleWebFragment);
			break;
		}
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(BaseFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.remove(fragment);
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

}

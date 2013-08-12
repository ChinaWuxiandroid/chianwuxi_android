/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

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
import com.wuxi.app.engine.LeaveMessageService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.LeaveMessageWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 栏目首页 留言提问 碎片布局
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressLint({ "ShowToast", "HandlerLeak" })
public class LiveHomeLeaveMessageFragment extends BaseFragment {

	protected static final String TAG = "LiveHomeLeaveMessageFragment";

	private View view = null;
	private Context context = null;

	private ProgressBar leavePro = null;

	private ListView leaveListView = null;

	private LeaveMessageWrapper messageWrapper = null;

	private List<LeaveMessageWrapper.LeaveMessage> leaveMessages = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取提问留言的起始坐标
	private int startIndex = 0;
	// 获取提问留言的结束坐标
	private int endIndex = 10;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				leavePro.setVisibility(View.GONE);
				showLeaveMessage();
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
		view = inflater.inflate(R.layout.gip_live_home_message_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				LeaveMessageService messageService = new LeaveMessageService(
						context);

				try {
					messageWrapper = messageService.getLeaveMessageWrapper(
							"32480e19-76b8-45d9-b7d1-a6c54933f9f7", startIndex,
							endIndex);
					if (messageWrapper != null) {
						leaveMessages = messageWrapper.getLeaveMessages();
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
	 * 加载布局控件
	 */
	private void initLayout() {
		leaveListView = (ListView) view
				.findViewById(R.id.gip_live_home_message_listview);

		leavePro = (ProgressBar) view
				.findViewById(R.id.gip_live_home_message_progressbar);
		leavePro.setVisibility(View.VISIBLE);

		loadData();
	}

	/**
	 * 显示列表数据
	 */
	private void showLeaveMessage() {
		LeaveMessageListAdapter messageListAdapter = new LeaveMessageListAdapter();

		for (int i = 0; i < leaveMessages.size(); i++) {
			System.out.println(leaveMessages.get(i).getSentUser());
		}

		if (leaveMessages == null || leaveMessages.size() == 0) {
			Toast.makeText(context, "对不起，暂无访谈实录信息", 2000).show();
		} else {
			leaveListView.setAdapter(messageListAdapter);
		}
	}

	/**
	 * 内部类，留言提问列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class LeaveMessageListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return leaveMessages.size();
		}

		@Override
		public Object getItem(int position) {
			return leaveMessages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * 内部类，声明控件变量
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		class ViewHolder {
			public TextView leave_content_text;
			public TextView leave_ask_time_text;
			public TextView leave_answer_content_text;
			public TextView leave_answer_time_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.vedio_leavemessage_list_layout, null);

				holder = new ViewHolder();

				holder.leave_content_text = (TextView) convertView
						.findViewById(R.id.leave_content_text);
				holder.leave_ask_time_text = (TextView) convertView
						.findViewById(R.id.leave_ask_time_text);
				holder.leave_answer_content_text = (TextView) convertView
						.findViewById(R.id.leave_answer_content_text);
				holder.leave_answer_time_text = (TextView) convertView
						.findViewById(R.id.leave_answer_time_text);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.leave_content_text.setText(leaveMessages.get(position)
					.getContent());
			holder.leave_ask_time_text.setText("["
					+ leaveMessages.get(position).getSubmitTime() + "]");
			holder.leave_answer_content_text.setText(leaveMessages
					.get(position).getAnswerContent());
			holder.leave_answer_time_text.setText("["
					+ leaveMessages.get(position).getRecommendTime() + "]");

			return convertView;
		}

	}

}

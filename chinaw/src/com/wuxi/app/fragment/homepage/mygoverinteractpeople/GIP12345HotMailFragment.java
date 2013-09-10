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
import android.widget.ProgressBar;
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
 * 12345来信办理平台 主Fragment --热门信件选登 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345HotMailFragment extends RadioButtonChangeFragment {

	private ListView mListView;
	private ProgressBar list_pb;
	private LetterWrapper letterWrapper;
	private List<LetterWrapper.Letter> letters;
	protected static final String TAG = "GIP12345HotMail";
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	private int startIndex = 0; // 获取话题的起始坐标
	private int endIndex = 100; // 获取话题的结束坐标

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
		};
	};

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_hotmail_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return 0;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return null;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		mListView = (ListView) view
				.findViewById(R.id.gip_12345_hotmail_listView);
		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_12345_hotmail_listView_pb);

		list_pb.setVisibility(View.VISIBLE);
		loadData();
	}

	public void loadData() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				LetterService letterService = new LetterService(context);
				try {
					letterWrapper = letterService.getLetterLitstWrapper(
							Constants.Urls.HOTMAIL_URL, startIndex, endIndex);
					if (null != letterWrapper) {
						letters = letterWrapper.getData();

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

	public void showLettersList() {
		LettersListViewAdapter adapter = new LettersListViewAdapter();
		if (letters == null || letters.size() == 0) {
			Toast.makeText(context, "对不起，暂无热门信件信息", Toast.LENGTH_SHORT).show();
		} else {
			mListView.setAdapter(adapter);
		}
	}

	public class LettersListViewAdapter extends BaseAdapter {

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
			public TextView title_text;
			public TextView depname_text;
			public TextView answerDate_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gip_12345_hotmail_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.gip_12345_hotmail_tilte);
				viewHolder.depname_text = (TextView) convertView
						.findViewById(R.id.gip_12345_hotmail_depname);
				viewHolder.answerDate_text = (TextView) convertView
						.findViewById(R.id.gip_12345_hotmail_answerDate);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.title_text.setText("信件标题："+letters.get(position).getTitle());
			viewHolder.depname_text.setText(letters.get(position).getDepname());
			viewHolder.answerDate_text.setText(letters.get(position)
					.getAnswerdate());

			return convertView;
		}

	}

}

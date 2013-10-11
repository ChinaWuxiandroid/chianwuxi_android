package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment --12345来信办理平台 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIPMine12345Fragment extends RadioButtonChangeFragment {
	
	private static final String TAG = "GIP12345MayorMaiBoxFragment";

	private ListView mListView;
	private LetterWrapper letterWrapper;
	private List<LetterWrapper.Letter> letters;
	
	private static final int DATA__LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	private int startIndex = 0; // 获取话题的起始坐标
	private int endIndex = 5; // 获取话题的结束坐标

	private final int[] radioButtonIds = {
			R.id.gip_mine_12345_radioButton_backmail,
			R.id.gip_mine_12345_radioButton_mybackmail, };

	// 我要写信 按钮
	private ImageButton writeLetterImageBtn = null;

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

		case R.id.gip_mine_12345_radioButton_backmail:
			init();
			break;

		case R.id.gip_mine_12345_radioButton_mybackmail:
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_mine_12345_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_12345_radioGroup;
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
		mListView = (ListView) view
				.findViewById(R.id.goverinterpeople_mine_12345_listview);

		writeLetterImageBtn = (ImageButton) view
				.findViewById(R.id.gip_mine_12345_imageButton_writemail);
		writeLetterImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), MainMineActivity.class);
				intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 5);
				
				intent.putExtra(Constants.CheckPositionKey.LEVEL_TWO__KEY, 1);// 这个意思让你选中左侧第二个菜单也就是12345办理平台
				intent.putExtra(Constants.CheckPositionKey.LEVEL_THREE_KEY, 6);// 这个意思让你选中我要写信
				MainTabActivity.instance.addView(intent);

			}
		});

		loadData();
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 */
	private void loadData() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				LetterService letterService = new LetterService(context);
				try {

					letterWrapper = letterService.getMyLettersList(
							Constants.Urls.MY_LETTER_URL,
							SystemUtil.getAccessToken(context), startIndex,
							endIndex);
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

	/**
	 * @方法： showLettersList
	 * @描述： 显示信件列表
	 */
	private void showLettersList() {
		LettersListViewAdapter adapter = new LettersListViewAdapter();
		if (letters == null || letters.size() == 0) {
			Toast.makeText(context, "对不起，暂无信息", Toast.LENGTH_SHORT).show();
		} else {
			mListView.setAdapter(adapter);
		}
	}

	/**
	 * @类名： LettersListViewAdapter
	 * @描述： 信件列表适配器
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-11 下午4:35:18
	 * @修改时间： 
	 * @修改描述：
	 */
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
			public TextView code_text;
			public TextView answerDate_text;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gip_mine_12345_listview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.gip_mine_12345_textView_title);
				viewHolder.code_text = (TextView) convertView
						.findViewById(R.id.gip_mine_12345_textView_code);
				viewHolder.answerDate_text = (TextView) convertView
						.findViewById(R.id.gip_mine_12345_textView_reply);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			String title_str = (position + 1) + "." + "["
					+ letters.get(position).getType() + "]"
					+ letters.get(position).getTitle();
			viewHolder.title_text.setText(title_str);
			viewHolder.code_text.setText(letters.get(position).getCode());

			// viewHolder.answerDate_text.setText(letters.get(position).getAnswerdate());

			return convertView;
		}

	}
}

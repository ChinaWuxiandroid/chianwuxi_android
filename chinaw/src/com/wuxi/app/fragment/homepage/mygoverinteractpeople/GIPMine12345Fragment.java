package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.GIP12345AllMailContentActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.adapter.MineReplyLetterAdapter;
import com.wuxi.app.engine.LetterService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.LogUtil;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.LetterWrapper;
import com.wuxi.domain.LetterWrapper.Letter;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 我的政民互动 主Fragment --12345来信办理平台 fragment
 * 
 * @author 杨宸 智佳
 * */
public class GIPMine12345Fragment extends RadioButtonChangeFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {

	private static final String TAG = "GIPMine12345Fragment";

	private final int[] radioButtonIds = {
			R.id.gip_mine_12345_radioButton_backmail,
			R.id.gip_mine_12345_radioButton_mybackmail, };

	// 我要写信 按钮
	private ImageButton writeLetterImageBtn = null;

	private ListView mListView;
	private ProgressBar list_pb;

	private LetterWrapper letterWrapper;
	private List<Letter> letters;

	private MineReplyLetterAdapter adapter;

	private static final int DATA_LOAD_SUCESS = 0;
	private static final int DATA_LOAD_ERROR = 1;

	private static final int START = 0; // 获取话题的起始坐标

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private String tip = null;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				showLettersList();
				break;
			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		// 我的回信
		case R.id.gip_mine_12345_radioButton_backmail:
			init();
			break;
		// 我收藏的回信
		case R.id.gip_mine_12345_radioButton_mybackmail:
			Toast.makeText(context, "该功能暂未实现！", Toast.LENGTH_SHORT).show();

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

		writeLetterImageBtn = (ImageButton) view
				.findViewById(R.id.gip_mine_12345_imageButton_writemail);
		// 我要写信，切换页面
		writeLetterImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(),
						MainMineActivity.class);
				intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY, 5);

				intent.putExtra(Constants.CheckPositionKey.LEVEL_TWO__KEY, 1);// 这个意思让你选中左侧第二个菜单也就是12345办理平台
				intent.putExtra(Constants.CheckPositionKey.LEVEL_THREE_KEY, 6);// 这个意思让你选中我要写信
				MainTabActivity.instance.addView(intent);

			}
		});

		initLayout();

		loadFirstData(START, PAGE_NUM);

	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view
				.findViewById(R.id.goverinterpeople_mine_12345_repply_listview);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.goverinterpeople_mine_12345_repply_progressbar);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * @方法： loadData
	 * @描述： 加载数据
	 */
	private void loadData(final int start, final int end) {

		System.out.println("读取数据start===》" + start + "   end===>" + end);

		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				LetterService letterService = new LetterService(context);
				try {
					letterWrapper = letterService.getMyLettersList(
							Constants.Urls.MY_LETTER_URL,
							SystemUtil.getAccessToken(context), start, end);

					System.out
							.println("政民互动页面：" + Constants.Urls.MY_LETTER_URL);

					if (null != letterWrapper && letterWrapper.isData()) {
						handler.sendEmptyMessage(DATA_LOAD_SUCESS);
					} else if (letterWrapper != null
							&& letterWrapper.isData() == false) {
						tip = "没有数据";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					} else {
						tip = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
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
		letters = letterWrapper.getData();

		System.out.println("读取的数据的长度:" + letters.size());

		if (letters == null || letters.size() == 0) {
			Toast.makeText(context, "对不起，暂无您的回信!", Toast.LENGTH_SHORT).show();
		} else {
			if (isFirstLoad) {
				System.out.println("第一次");
				adapter = new MineReplyLetterAdapter(context, letters);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					System.out.println("切换页面");
					adapter.setLetters(letters);
					list_pb.setVisibility(View.GONE);
				} else {
					System.out.println("集合添加数据");
					for (Letter letter : letters) {
						adapter.addItem(letter);
					}
				}
				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}
		if (letterWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			if (adapter != null) {
				mListView.removeFooterView(loadMoreView);
			}

		}
	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (letterWrapper != null && letterWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Letter letter = (Letter) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(getActivity(),
				GIP12345AllMailContentActivity.class);
		intent.putExtra("letter", letter);

		MainTabActivity.instance.addView(intent);
	}

}

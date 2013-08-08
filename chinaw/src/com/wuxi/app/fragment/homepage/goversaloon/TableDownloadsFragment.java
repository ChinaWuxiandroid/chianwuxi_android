package com.wuxi.app.fragment.homepage.goversaloon;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.DeptSpinnerAdapter;
import com.wuxi.app.adapter.GoverTableDownLoadAdapter;
import com.wuxi.app.engine.DeptService;
import com.wuxi.app.engine.GoverSaoonFileService;
import com.wuxi.app.engine.GoverTableDownLoadService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Dept;
import com.wuxi.domain.GoverTableDownLoad;
import com.wuxi.domain.GoverTableDownLoadWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 表格下载Fragment
 * 
 */
public class TableDownloadsFragment extends GoverSaloonContentFragment
		implements OnScrollListener, OnItemSelectedListener,
		OnItemClickListener, android.view.View.OnClickListener {

	protected static final int LOAD_DEPT_SUCCESS = 0;
	protected static final int LOAD_DEPT_FAIL = 1;
	protected static final int GOVERITEM_LOAD_SUCCESS = 2;
	protected static final int GOVERITEM_LOAD_FIAL = 3;
	private static final int FILE_DOWN_SUCCESS = 4;
	private static final int FILE_DOWN_ERROR = 5;
	private static final int PAGE_SIZE = 10;
	private Spinner gover_table_down_deptsp;
	private List<Dept> depts;
	private ProgressBar pb_table_download;
	private ListView gover_tabledowload_lv;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private GoverTableDownLoadWrapper goverTableDownLoadWrapper;
	private GoverTableDownLoadAdapter goverTableDownLoadAdapter;
	private String currentDeptId;
	private boolean isSwitchDept = false;
	private boolean isFisrtLoadItems = true;// 是否是首次加载
	private String currentFileName;
	private EditText et_filename_keywords;
	private Button btn_fileSearch;

	private ProgressDialog pd;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD_DEPT_SUCCESS:
				showDept();
				break;
			case GOVERITEM_LOAD_SUCCESS:
				showTableDownLoadItemList();
				break;
			case FILE_DOWN_SUCCESS:
				Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
				break;
			case FILE_DOWN_ERROR:
				Toast.makeText(context, "下载出错，稍后再试", Toast.LENGTH_SHORT).show();
				break;
			case GOVERITEM_LOAD_FIAL:
			case LOAD_DEPT_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		}
	};

	@Override
	protected void initUI() {

		super.initUI();

		gover_table_down_deptsp = (Spinner) view
				.findViewById(R.id.gover_table_down_deptsp);
		pb_table_download = (ProgressBar) view
				.findViewById(R.id.pb_table_download);
		gover_tabledowload_lv = (ListView) view
				.findViewById(R.id.gover_tabledowload_lv);
		et_filename_keywords = (EditText) view
				.findViewById(R.id.et_filename_keywords);
		btn_fileSearch = (Button) view.findViewById(R.id.btn_fileSearch);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		gover_tabledowload_lv.addFooterView(loadMoreView);
		gover_tabledowload_lv.setOnScrollListener(this);
		gover_tabledowload_lv.setOnItemClickListener(this);
		gover_table_down_deptsp.setOnItemSelectedListener(this);
		btn_fileSearch.setOnClickListener(this);
		loadDept();

		pd = new ProgressDialog(context);

		pd.setMessage("正在下载表格....");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

	}

	private void loadDept() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				DeptService deptService = new DeptService(context);
				try {
					depts = deptService.getDepts();
					if (depts != null) {
						msg.what = LOAD_DEPT_SUCCESS;
						CacheUtil.put(Constants.CacheKey.DEPT_KEY, depts);// 放入缓存
					} else {
						msg.what = LOAD_DEPT_FAIL;
						msg.obj = "没有获取到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_SUCCESS;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示部门信息
	 */
	private void showDept() {

		gover_table_down_deptsp.setAdapter(new DeptSpinnerAdapter(depts,
				context));

		this.currentDeptId = depts.get(0).getId();
		loadItem(currentDeptId, null, 0, PAGE_SIZE);

	}

	/**
	 * 获取办件信息
	 */
	private void loadItem(final String deptId, final String fileName,
			final int start, final int end) {

		if (isFisrtLoadItems || isSwitchDept) {// 首次加载时或切换部门时显示进度条

			pb_table_download.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				GoverTableDownLoadService goverTableDownLoadService = new GoverTableDownLoadService(
						context);
				try {
					goverTableDownLoadWrapper = goverTableDownLoadService
							.getTableDownLoadsPage(deptId, fileName, start, end);
					if (goverTableDownLoadWrapper != null) {
						msg.what = GOVERITEM_LOAD_SUCCESS;
					} else {
						msg.what = GOVERITEM_LOAD_FIAL;
						msg.obj = "加载表格下载列表失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();

					msg.what = GOVERITEM_LOAD_FIAL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示办件事项详细信息
	 */
	protected void showTableDownLoadItemList() {

		if (goverTableDownLoadWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {
			// loadMoreButton.setText(" ");
			gover_tabledowload_lv.removeFooterView(loadMoreView);
		}

		List<GoverTableDownLoad> goDownLoads = goverTableDownLoadWrapper
				.getGoverTableDownLoads();
		if (goDownLoads != null && goDownLoads.size() > 0) {
			if (isFisrtLoadItems) {// 首次加载
				goverTableDownLoadAdapter = new GoverTableDownLoadAdapter(
						goDownLoads, context);
				isFisrtLoadItems = false;
				gover_tabledowload_lv.setAdapter(goverTableDownLoadAdapter);
				pb_table_download.setVisibility(ProgressBar.GONE);

			} else {

				if (isSwitchDept) {// 切换部门
					goverTableDownLoadAdapter
							.setGoverTableDownLoads(goDownLoads);
					pb_table_download.setVisibility(ProgressBar.GONE);
				} else {
					for (GoverTableDownLoad goverTableDownLoad : goDownLoads) {
						goverTableDownLoadAdapter.addItem(goverTableDownLoad);
					}
				}

				goverTableDownLoadAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_tabledowload_lv.setSelection(visibleLastIndex
						- visibleItemCount + 1); // 设置选中项

			}

		}

	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_saloon_tabledownload_layout;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = goverTableDownLoadAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (goverTableDownLoadWrapper != null
					&& goverTableDownLoadWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitchDept = false;
				loadItem(currentDeptId, currentFileName, visibleLastIndex + 1,
						visibleLastIndex + 1 + PAGE_SIZE);

			}

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		Dept dept = (Dept) adapterView.getItemAtPosition(position);
		this.currentDeptId = dept.getId();
		isSwitchDept = true;
		loadItem(currentDeptId, currentFileName, 0, PAGE_SIZE);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	private void showDownloadComfirmDialog(
			final GoverTableDownLoad tableDownLoad) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.logo);
		builder.setTitle("下载提示");
		builder.setMessage("确认要下载该" + tableDownLoad.getFilename()
				+ "文件吗?\n 文件存放地址:" + Constants.APPFiles.DOWNLOAF_FILE_PATH
				+ tableDownLoad.getFilename());
		builder.setCancelable(false);
		File file = new File(Constants.APPFiles.DOWNLOAF_FILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					DownLoadThreadTask dowTask = new DownLoadThreadTask(
							tableDownLoad.getId(),
							Constants.APPFiles.DOWNLOAF_FILE_PATH
									+ tableDownLoad.getFilename());

					new Thread(dowTask).start();
					pd.show();

				} else {
					Toast.makeText(context, "SDK不存在", 1).show();

				}

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();

	}

	private class DownLoadThreadTask implements Runnable {

		private String fileId;
		private String filePath;

		public DownLoadThreadTask(String fileId, String filePath) {

			this.fileId = fileId;
			this.filePath = filePath;
		}

		@Override
		public void run() {

			try {

				GoverSaoonFileService goverSaoonFileService = new GoverSaoonFileService(
						context);
				File file = goverSaoonFileService.dowloadTable(fileId,
						filePath, pd);
				if (file != null) {
					handler.sendEmptyMessage(FILE_DOWN_SUCCESS);
				}
				pd.dismiss();
			} catch (Exception e) {

				e.printStackTrace();

				pd.dismiss();

				handler.sendEmptyMessage(FILE_DOWN_ERROR);
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		GoverTableDownLoad tableDownLoad = (GoverTableDownLoad) adapterView
				.getItemAtPosition(position);
		showDownloadComfirmDialog(tableDownLoad);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_fileSearch:
			if (et_filename_keywords.getText().toString().equals("")) {
				Toast.makeText(context, "请输入关键字", Toast.LENGTH_SHORT).show();
				currentFileName = null;
				return;
			}
			currentFileName = et_filename_keywords.getText().toString();
			loadItem(currentDeptId, currentFileName, 0, PAGE_SIZE);

			break;

		}

	}
}

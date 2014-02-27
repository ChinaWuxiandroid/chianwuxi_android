package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.adapter.PromoMoreVideoAdapter;
import com.wuxi.app.net.HttpUtils;
import com.wuxi.app.net.NetworkUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.PromoMoreVideoInfo;

/**
 * 更多视频预告
 * 
 * @类名： PromoMoreVideoActivity
 * @作者：陈彬
 * @创建时间： 2013 2013-11-27 上午10:57:20
 * @修改时间：
 * @修改描述：
 */
public class PromoMoreVideoActivity extends BaseItemContentActivity {

	private ListView mListView;
	private ProgressDialog mProgressDialog = null;
	private Context context;
	private ArrayList<PromoMoreVideoInfo> arrayList = null;
	private ArrayList<PromoMoreVideoInfo> arrayListAll = new ArrayList<PromoMoreVideoInfo>();
	private PromoMoreVideoAdapter mAdapter = null;
	private int start = 0, end = 10;
	private View loadMoreView;
	private Button loadMoreButton;

	@Override
	protected int getContentLayoutId() {
		return R.layout.promo_more_video_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "节目预告";
	}

	@Override
	protected void findMainContentViews(View view) {
		super.findMainContentViews(view);
		context = PromoMoreVideoActivity.this;
		mListView = (ListView) view
				.findViewById(R.id.promo_more_video_listview);
		new getMorePromoVideo(start, end).execute();
	}

	/**
	 * 异步获取数据
	 * 
	 * @类名： getMorePromoVideo
	 * @作者： 陈彬
	 * @创建时间： 2013 2013-11-27 上午9:46:49
	 * @修改时间：
	 * @修改描述：
	 */
	private class getMorePromoVideo extends AsyncTask<String, Integer, String> {

		private int start;

		private int end;

		public getMorePromoVideo(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected String doInBackground(String... params) {

			String url = Constants.Urls.DOMAIN_URL
					+ "/api/interview/notice.json?start=" + start + "&end="
					+ end;

			NetworkUtil mUtil = NetworkUtil.getInstance();
			String data = null;
			if (mUtil.isConnet(context)) {
				HttpUtils mHttpUtils = HttpUtils.getInstance();
				data = mHttpUtils.executeGetToString(url, 5000);
			} else {
				Toast.makeText(context, "连接网络失败", Toast.LENGTH_SHORT).show();
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (isNULL(result)) {
				arrayList = new PromoMoreVideoInfo(context).resolveData(result);
				if (arrayList == null || arrayList.size() < 1) {
					Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT).show();
					if (mProgressDialog.isShowing()) {
						mProgressDialog.cancel();
					}
				} else {
					mHandler.sendEmptyMessage(100);
				}
			} else {
				Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(context);
			}
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setMessage("正在获取数据...");
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				arrayListAll.addAll(arrayList);
				if (mAdapter == null) {
					mAdapter = new PromoMoreVideoAdapter(context, arrayListAll);
					mListView.setAdapter(mAdapter);
					// 判断是否有下一页
					if (arrayList.get(arrayList.size() - 1).isNext()) {
						mListView.addFooterView(getListViewFoot());
					}
				} else {
					mAdapter.addItem(arrayListAll);
				}
				// 判断是否有下一页
				if (arrayList.get(arrayList.size() - 1).isNext()) {
					loadMoreButton.setText("点击加载更多");
				} else {
					mListView.removeFooterView(loadMoreView);
				}
				if (mProgressDialog.isShowing()) {
					mProgressDialog.cancel();
				}
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 加载更多
	 * 
	 * @方法： getListViewFoot
	 * @return
	 */
	private View getListViewFoot() {
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) View.inflate(context, R.id.loadMoreButton,
				null);
		loadMoreButton.setVisibility(View.GONE);
		loadMoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				start = start + end;
				end += end;
				new getMorePromoVideo(start, end).execute();
			}
		});
		return loadMoreView;
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @方法： isNULL
	 * @param str
	 * @return
	 */
	private boolean isNULL(String str) {
		if (str == null || str == "" || str.equals(null) || str.equals("")
				|| str.length() < 0) {
			return false;
		} else {
			return true;
		}
	}

}

/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
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
import com.wuxi.app.engine.VedioReviewService;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.VedioReviewWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 走进直播间 往期回顾 碎片布局
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressLint("HandlerLeak")
public class VedioReviewFragment extends RadioButtonChangeFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ListView reviewListView = null;

	private ProgressBar reviewProBar = null;

	private VedioReviewWrapper reviewWrapper = null;

	private List<VedioReviewWrapper.VedioReview> reviews = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取提问留言的起始坐标
	private int startIndex = 0;
	// 获取提问留言的结束坐标
	private int endIndex = 10;

	protected static final String TAG = "VedioReviewFragment";

	@Override
	protected int getLayoutId() {
		return R.layout.gip_vedio_review_layout;
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
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				reviewProBar.setVisibility(View.GONE);
				showReviewList();
				break;

			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void init() {
		reviewListView = (ListView) view
				.findViewById(R.id.gip_vedio_review_listview);

		reviewProBar = (ProgressBar) view
				.findViewById(R.id.gip_vedio_review_progressbar);
		reviewProBar.setVisibility(View.VISIBLE);
		
		loadData();
	}
	
	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				VedioReviewService reviewService = new VedioReviewService(
						context);

				try {
					reviewWrapper = reviewService.getVedioReviewWrapper(startIndex,endIndex);
					if (reviewWrapper != null) {
						reviews = reviewWrapper.getReviews();
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
	 * 显示列表数据
	 */
	@SuppressLint("ShowToast")
	private void showReviewList(){
//		BaseSlideFragment baseSlideFragment = (BaseSlideFragment)this.getArguments().get("BaseSlideFragment");
//		
//		System.out.println("+++=="+getArguments().get("BaseSlideFragment"));
		
		ReviewListAdapter reviewListAdapter = new ReviewListAdapter();
		
		for (int i = 0; i < reviews.size(); i++) {
			System.out.println(reviews.get(i).getSubject());
		}
		
		if (reviews == null || reviews.size() == 0) {
			Toast.makeText(context, "对不起，暂无访谈实录信息", 2000).show();
		} else {
			reviewListView.setAdapter(reviewListAdapter);
			reviewListView.setOnItemClickListener(reviewListAdapter);
		}
	}
	
	/**
	 * 内部类，往期回顾列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class ReviewListAdapter extends BaseAdapter implements OnItemClickListener{
		
	
		
		public ReviewListAdapter(){
			
		}
		
		

		@Override
		public int getCount() {
			return reviews.size();
		}

		@Override
		public Object getItem(int position) {
			return reviews.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * 内部类，布局控件声明类
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		class Holder {
			public TextView review_title_text;
			public TextView review_time_text;
			public TextView review_guest_text;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.vedio_review_list_layout, null);

				holder = new Holder();

				holder.review_title_text = (TextView) convertView
						.findViewById(R.id.review_title_text);
				holder.review_time_text = (TextView) convertView
						.findViewById(R.id.review_time_text);
				holder.review_guest_text = (TextView) convertView
						.findViewById(R.id.review_guest_text);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.review_title_text
					.setText(reviews.get(position).getSubject());
			holder.review_time_text
					.setText(reviews.get(position).getWorkDate());
			holder.review_guest_text.setText(reviews.get(position).getGuests());

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
//			baseSlideFragment.slideLinstener.replaceFragment(null, position,
//					Constants.FragmentName.GIP_REVIEW_CONTENT_FRAGMENT, null);
		}

	}
}

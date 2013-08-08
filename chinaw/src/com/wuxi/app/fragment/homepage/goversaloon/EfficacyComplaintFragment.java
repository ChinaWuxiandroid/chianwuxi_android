package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.EfficacyComplaintAdapter;
import com.wuxi.app.engine.EfficaComplainService;
import com.wuxi.app.engine.LetterQueryService;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.ContentType;
import com.wuxi.domain.EfficaComplain;
import com.wuxi.domain.EfficaComplainWrapper;
import com.wuxi.domain.LetterType;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通 效能投诉Fragment
 * 
 */
public class EfficacyComplaintFragment extends GoverSaloonContentFragment
		implements OnScrollListener, OnClickListener {

	private ImageView gover_eff_btn_mail_search;
	private ImageView gover_eff_btn_writemail;
	private ListView gover_eff_lv;

	private ProgressBar gover_eff_pb;
	private static final int PAGE_SIZE = 10;
	protected static final int LOAD_EFF_SUCCESS = 0;
	protected static final int LOAD_EFF_FAIL = 1;
	protected static final int LOAD_CONTENTTYPE_SUCCESS = 2;
	protected static final int LOAD_CONTENTTYPE_ERROR = 3;
	protected static final int LOAD_LETTER_SUCCESS = 4;
	protected static final int LOAD_LETTER_ERROR = 5;
	private boolean isFistLoad = true;
	private EfficaComplainWrapper efficaWrapper;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private EfficacyComplaintAdapter efficacyComplaintAdapter;
	private LinearLayout ll_mail;
	private EditText et_letter_keyword, et_startTime, et_endtime, et_letter_no;
	private Spinner sp_contenttype, sp_lettertype;
	private CheckBox nomal_question;
	private ImageView iv_letter_query;
	private List<ContentType> contentTypes;
	private List<LetterType> letterTypes;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case LOAD_EFF_SUCCESS:
				showEffData();
				break;
			case LOAD_CONTENTTYPE_SUCCESS:
				showContentTypes();
				break;
			case LOAD_LETTER_SUCCESS:
				showLetterTypes();
				break;
			case LOAD_CONTENTTYPE_ERROR:
				
			case LOAD_LETTER_ERROR:
			case LOAD_EFF_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	/**
	 * 
	 * wanglu 泰得利通 显示效能投诉列表数据
	 */
	protected void showEffData() {
		if (efficaWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {

			gover_eff_lv.removeFooterView(loadMoreView);
		}

		List<EfficaComplain> efficaComplains = efficaWrapper
				.getEfficaComplains();
		if (efficaComplains != null && efficaComplains.size() > 0) {
			if (isFistLoad) {
				efficacyComplaintAdapter = new EfficacyComplaintAdapter(
						efficaComplains, context);
				isFistLoad = false;
				gover_eff_lv.setAdapter(efficacyComplaintAdapter);
				gover_eff_pb.setVisibility(ProgressBar.GONE);

			} else {

				for (EfficaComplain eff : efficaComplains) {
					efficacyComplaintAdapter.addItem(eff);
				}

				efficacyComplaintAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_eff_lv.setSelection(visibleLastIndex - visibleItemCount
						+ 1); // 设置选中项

			}

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 加载信件内容类型
	 */
	private void loadLetterTypes() {

		if (this.letterTypes != null) {
			showLetterTypes();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				LetterQueryService letterQueryService = new LetterQueryService(
						context);
				try {
					letterTypes = letterQueryService.getLetterTypes();
					if (letterTypes != null) {
						msg.what = LOAD_LETTER_SUCCESS;
					} else {
						msg.what = LOAD_LETTER_ERROR;
						msg.obj = "没有获取到数据";
					}

					handler.sendMessage(msg);
				} catch (NetException e) {

					e.printStackTrace();
					msg.what = LOAD_LETTER_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {

					e.printStackTrace();
					msg.what = LOAD_LETTER_ERROR;
					msg.obj = Constants.ExceptionMessage.DATA_FORMATE;
					handler.sendMessage(msg);
				}

			}
		}).start();
		
		
	}

	/**
	 * 
	 * wanglu 泰得利通 显示信件内容
	 */
	private void showContentTypes() {
		sp_contenttype.setAdapter(new LetterTypeAdapter(contentTypes));
	}

	private void loadContentTypes() {

		if (this.contentTypes != null) {
			showContentTypes();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				LetterQueryService letterQueryService = new LetterQueryService(
						context);
				try {
					contentTypes = letterQueryService.getContentTypes();
					if (contentTypes != null) {
						msg.what = LOAD_CONTENTTYPE_SUCCESS;
					} else {
						msg.what = LOAD_CONTENTTYPE_ERROR;
						msg.obj = "没有获取到数据";
					}

					handler.sendMessage(msg);
				} catch (NetException e) {

					e.printStackTrace();
					msg.what = LOAD_CONTENTTYPE_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {

					e.printStackTrace();
					msg.what = LOAD_CONTENTTYPE_ERROR;
					msg.obj = Constants.ExceptionMessage.DATA_FORMATE;
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	/**
	 * 
	 * wanglu 泰得利通 显示信件类容
	 */

	private void showLetterTypes() {
		 sp_lettertype.setAdapter(new LetterTypeAdapter(letterTypes));
	}

	public void initUI() {
		super.initUI();
		gover_eff_btn_mail_search = (ImageView) view
				.findViewById(R.id.gover_eff_btn_mail_search);
		gover_eff_btn_writemail = (ImageView) view
				.findViewById(R.id.gover_eff_btn_writemail);
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);

		gover_eff_lv = (ListView) view.findViewById(R.id.gover_eff_lv);
		gover_eff_lv.addFooterView(loadMoreView);
		gover_eff_lv.setOnScrollListener(this);
		gover_eff_pb = (ProgressBar) view.findViewById(R.id.gover_eff_pb);
		ll_mail = (LinearLayout) view.findViewById(R.id.ll_mail);
		gover_eff_btn_mail_search.setOnClickListener(this);

		et_letter_keyword = (EditText) view
				.findViewById(R.id.et_letter_keyword);
		et_startTime = (EditText) view.findViewById(R.id.et_startTime);
		et_endtime = (EditText) view.findViewById(R.id.et_endtime);
		et_letter_no = (EditText) view.findViewById(R.id.et_letter_no);
		sp_contenttype = (Spinner) view.findViewById(R.id.sp_contenttype);
		sp_lettertype = (Spinner) view.findViewById(R.id.sp_lettertype);

		nomal_question = (CheckBox) view.findViewById(R.id.nomal_question);
		iv_letter_query = (ImageView) view.findViewById(R.id.iv_letter_query);

		loadEffData(0, PAGE_SIZE);
		loadContentTypes();
		loadLetterTypes();

	}

	private void loadEffData(final int start, final int end) {
		if (isFistLoad) {
			gover_eff_pb.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				EfficaComplainService efficaComplainService = new EfficaComplainService(
						context);
				try {
					efficaWrapper = efficaComplainService
							.getPageEfficaComplains(start, end);
					if (efficaWrapper != null) {
						msg.what = LOAD_EFF_SUCCESS;

					} else {

						msg.what = LOAD_EFF_FAIL;
						msg.obj = "没有获取到数据!";

					}

					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_EFF_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_EFF_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				} catch (ResultException e) {
					e.printStackTrace();
					msg.what = LOAD_EFF_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = efficacyComplaintAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (efficaWrapper != null && efficaWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				loadEffData(visibleLastIndex + 1, visibleLastIndex + 1
						+ PAGE_SIZE);
			}

		}
	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_efficacycomplaint_layout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gover_eff_btn_mail_search:
			if (ll_mail.getVisibility() == LinearLayout.GONE) {
				ll_mail.setVisibility(LinearLayout.VISIBLE);
			} else {
				ll_mail.setVisibility(LinearLayout.GONE);
			}
			break;

		}

	}

	static class ViewHolder {
		TextView tv_name;
	}

	@SuppressWarnings("rawtypes")
	private final class LetterTypeAdapter extends BaseAdapter {

		
		public List list;

		public LetterTypeAdapter(List list) {
			this.list = list;
		}

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Object getItem(int position) {

			return list.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Object o = list.get(position);
			String name = "";
			if (o instanceof ContentType) {
				name = ((ContentType) o).getTypename();
			} else if (o instanceof LetterType) {
				name = ((LetterType) o).getTypename();
			}

			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.comstuom_spinner_item_layout, null);
				viewHolder = new ViewHolder();
				TextView tv = (TextView) convertView.findViewById(R.id.sp_tv);
				viewHolder.tv_name = tv;

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_name.setText(name);

			return convertView;
		}

	}

}

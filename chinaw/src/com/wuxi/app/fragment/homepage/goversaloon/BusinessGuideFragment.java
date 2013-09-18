package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailCFActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQTActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailQZActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailXKActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonDetailZSActivtiy;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;
import com.wuxi.app.adapter.KindTypeAdapter;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.app.engine.KindTypeService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemWrapper;
import com.wuxi.domain.Kindtype;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 办事指南
 * 
 *         上级分类编号 01：个人身份 02：个人办事 03：企业行业 04：企业办事 05：港澳台侨、外国人 06：主题服务
 */
@SuppressLint("HandlerLeak")
public class BusinessGuideFragment extends GoverSaloonContentFragment implements
		OnCheckedChangeListener, OnScrollListener, OnItemClickListener,
		OnClickListener {

	protected static final int KINDTYPE_LOAD_SUCCESS = 0;
	protected static final int KINDTYPE_LOAD_FAIL = 1;
	protected static final int GOVERITEM_LOAD_SUCCESS = 2;
	protected static final int GOVERITEM_LOAD_FIAL = 3;
	private static final int PAGE_SIZE = 10;
	private GridView gv;
	private String[] types = new String[] { "01", "02", "03", "04", "05", "06" };
	private List<Kindtype> kindtypes;
	private KindTypeAdapter kindTypeAdapter;
	private boolean isFistLoadKindData = true;

	private RadioGroup gover_buness_guide_rg;
	private ProgressBar gover_guide_pb;
	private GoverSaoonItemWrapper goverSaoonItemWrapper;
	private boolean isFirstLoadGoverItem = true;
	private boolean isSwitchDept = false;
	private GoverOnlineApproveAdapter goverOnlineApproveAdapter;
	private ListView gover_guid_lv_content;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private Kindtype currentKindtype;
	private ProgressBar pb_loadmoore;
	private int showIndex = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case KINDTYPE_LOAD_SUCCESS:
				showKindType();
				break;
			case GOVERITEM_LOAD_SUCCESS:
				showItemList();
				break;
			case GOVERITEM_LOAD_FIAL:
			case KINDTYPE_LOAD_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		}
	};

	@Override
	protected void initUI() {

		super.initUI();

		gv = (GridView) view.findViewById(R.id.gover_guide_gv_kindtype);
		gv.setOnItemClickListener(this);
		gover_buness_guide_rg = (RadioGroup) view
				.findViewById(R.id.gover_buness_guide_rg);
		gover_guide_pb = (ProgressBar) view.findViewById(R.id.gover_guide_pb);
		gover_guid_lv_content = (ListView) view
				.findViewById(R.id.gover_guid_lv_content);

		

		gover_guid_lv_content.setOnItemClickListener(this);

		gover_guid_lv_content.addFooterView(getFootView());
		gover_guid_lv_content.setOnScrollListener(this);

		gover_buness_guide_rg.setOnCheckedChangeListener(this);

		showMainType();

	}

	private void showMainType() {
		Bundle bundle = getArguments();
		if (bundle != null
				&& bundle
						.containsKey(Constants.CheckPositionKey.LEVEL_THREE_KEY)) {
			showIndex = bundle
					.getInt(Constants.CheckPositionKey.LEVEL_THREE_KEY);
		}
		int typeIndex = 0;
		switch (showIndex) {
		case 0:
			gover_buness_guide_rg.check(R.id.rb_personal);
			typeIndex = 1;

			break;
		case 1:
			gover_buness_guide_rg.check(R.id.rb_company);
			typeIndex = 3;

			break;
		case 2:
			gover_buness_guide_rg.check(R.id.rb_style_service);
			typeIndex = 5;
			break;
		case 3:
			gover_buness_guide_rg.check(R.id.rb_green);
			break;

		}

		loadKindTypeData(types[typeIndex]);
	}

	private View getFootView() {
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		loadMoreButton.setOnClickListener(this);
		return loadMoreView;
	}

	/**
	 * 获取办件信息
	 */
	private void loadItem(final String type, final String kindType,
			final int start, final int end) {

		if (isFirstLoadGoverItem || isSwitchDept) {// 首次加载时或切换部门时显示进度条

			gover_guide_pb.setVisibility(ProgressBar.VISIBLE);
		} else {
			this.pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				GoverSaoonItemService goverSaoonItemService = new GoverSaoonItemService(
						context);
				try {
					goverSaoonItemWrapper = goverSaoonItemService
							.getGoverSaoonItemsByKindType(type, kindType,
									start, end);
					if (goverSaoonItemWrapper != null) {
						msg.what = GOVERITEM_LOAD_SUCCESS;
					} else {
						msg.what = GOVERITEM_LOAD_FIAL;
						msg.obj = "加载办件信息失败";
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
				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 显示办件事项详细信息
	 */
	protected void showItemList() {

		List<GoverSaoonItem> goverSaoonItems = goverSaoonItemWrapper
				.getGoverSaoonItems();
		if (goverSaoonItems != null && goverSaoonItems.size() > 0) {
			if (isFirstLoadGoverItem) {// 首次加载
				goverOnlineApproveAdapter = new GoverOnlineApproveAdapter(
						goverSaoonItems, context);
				isFirstLoadGoverItem = false;
				gover_guid_lv_content.setAdapter(goverOnlineApproveAdapter);
				gover_guide_pb.setVisibility(ProgressBar.GONE);

			} else {

				if (isSwitchDept) {// 切换部门
					goverOnlineApproveAdapter
							.setGoverSaoonItems(goverSaoonItems);
					gover_guide_pb.setVisibility(ProgressBar.GONE);
				} else {
					for (GoverSaoonItem item : goverSaoonItems) {
						goverOnlineApproveAdapter.addItem(item);
					}
				}

				goverOnlineApproveAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_guid_lv_content.setSelection(visibleLastIndex
						- visibleItemCount + 1); // 设置选中项

			}

		}

		if (goverSaoonItemWrapper.isNext()) {
			if (gover_guid_lv_content.getFooterViewsCount() != 0) {
				loadMoreButton.setText("点击加载更多");
				this.pb_loadmoore.setVisibility(ProgressBar.GONE);
			} else {
				gover_guid_lv_content.addFooterView(getFootView());
			}

		} else {

			gover_guid_lv_content.removeFooterView(loadMoreView);

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示kindType数据
	 */
	private void showKindType() {
		if (isFistLoadKindData) {
			isFistLoadKindData = false;
			kindTypeAdapter = new KindTypeAdapter(kindtypes, context, 0);
			gv.setAdapter(kindTypeAdapter);

			loadItem(kindtypes.get(0).getKindType(), kindtypes.get(0)
					.getSubKindType(), 0, PAGE_SIZE);// 加载办件第一个办件信息
		} else {
			kindTypeAdapter.setKindTypes(kindtypes);
			kindTypeAdapter.setSelectPostion(0);
			kindTypeAdapter.notifyDataSetChanged();
		}

	}

	@SuppressWarnings("unchecked")
	private void loadKindTypeData(final String type) {

		if (CacheUtil.get("type" + type) != null) {
			kindtypes = (List<Kindtype>) CacheUtil.get("type" + type);
			showKindType();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				KindTypeService kindTypeService = new KindTypeService(context);
				try {
					kindtypes = kindTypeService.getKindtypesByType(type);
					if (kindtypes != null) {
						CacheUtil.put("type" + type, kindtypes);
						msg.what = KINDTYPE_LOAD_SUCCESS;
					} else {
						msg.what = KINDTYPE_LOAD_FAIL;
						msg.obj = "获取数据失败";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();

					msg.what = KINDTYPE_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();

					msg.what = KINDTYPE_LOAD_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	@Override
	protected int getLayoutId() {
		return R.layout.gover_saloon_business_guide_layout;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int typeIndex = 0;
		switch (checkedId) {

		case R.id.rb_personal:
			typeIndex = 1;
			break;
		case R.id.rb_company:
			typeIndex = 3;
			break;
		case R.id.rb_style_service:
			typeIndex = 5;
			break;
		case R.id.rb_green:
			break;

		}
		loadKindTypeData(types[typeIndex]);

		for (int index = 0; index < group.getChildCount(); index++) {
			RadioButton rb = (RadioButton) group.getChildAt(index);
			if (rb.isChecked()) {
				rb.setTextColor(Color.RED);
			} else {
				rb.setTextColor(Color.BLACK);
			}
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

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		Object o = parent.getItemAtPosition(position);

		if (o instanceof Kindtype) {
			this.isSwitchDept = true;
			Kindtype kindtype = (Kindtype) o;
			this.currentKindtype = kindtype;
			loadItem(kindtype.getKindType(), kindtype.getSubKindType(), 0,
					PAGE_SIZE);
			kindTypeAdapter.setSelectPostion(position);
			kindTypeAdapter.notifyDataSetChanged();

		} else if (o instanceof GoverSaoonItem) {

			GoverSaoonItem goverSaoonItem = (GoverSaoonItem) parent
					.getItemAtPosition(position);

			Intent intent = null;
			if (goverSaoonItem.getType().equals("XK")) {
				intent = new Intent(getActivity(),
						GoverSaloonDetailXKActivity.class);
			} else if (goverSaoonItem.getType().equals("QT")) {

				intent = new Intent(getActivity(),
						GoverSaloonDetailQTActivity.class);
			} else if (goverSaoonItem.getType().equals("ZS")) {

				intent = new Intent(getActivity(),
						GoverSaloonDetailZSActivtiy.class);
			} else if (goverSaoonItem.getType().equals("QZ")) {

				intent = new Intent(getActivity(),
						GoverSaloonDetailQZActivity.class);
			} else if (goverSaoonItem.getType().equals("CF")) {
				intent = new Intent(getActivity(),
						GoverSaloonDetailCFActivity.class);
			}

			if (intent != null) {
				intent.putExtra("goverSaoonItem", goverSaoonItem);
				MainTabActivity.instance.addView(intent);
			}

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (goverSaoonItemWrapper != null && goverSaoonItemWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitchDept = false;
				loadItem(currentKindtype.getKindType(),
						currentKindtype.getSubKindType(), visibleLastIndex + 1,
						visibleLastIndex + 1 + PAGE_SIZE);

			}

			break;
		}

	}
}

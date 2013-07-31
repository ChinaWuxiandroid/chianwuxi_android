package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;
import com.wuxi.app.adapter.KindTypeAdapter;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.app.engine.KindTypeService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants.FragmentName;
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
		OnCheckedChangeListener, OnScrollListener, OnItemClickListener {

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
		gover_buness_guide_rg.check(R.id.rb_personal);

		gover_guid_lv_content.setOnItemClickListener(this);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		gover_guid_lv_content.addFooterView(loadMoreView);
		gover_guid_lv_content.setOnScrollListener(this);

		gover_buness_guide_rg.setOnCheckedChangeListener(this);
		loadKindTypeData(types[1]);

	}

	/**
	 * 获取办件信息
	 */
	private void loadItem(final String type, final String kindType,
			final int start, final int end) {

		if (isFirstLoadGoverItem || isSwitchDept) {// 首次加载时或切换部门时显示进度条

			gover_guide_pb.setVisibility(ProgressBar.VISIBLE);
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

		if (goverSaoonItemWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {
			loadMoreButton.setText("没有数据了...");
		}

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

	}

	/**
	 * 
	 * wanglu 泰得利通 显示kindType数据
	 */
	private void showKindType() {
		if (isFistLoadKindData) {
			isFistLoadKindData = false;
			kindTypeAdapter = new KindTypeAdapter(kindtypes, context);
			gv.setAdapter(kindTypeAdapter);

			loadItem(kindtypes.get(0).getKindType(), kindtypes.get(0)
					.getSubKindType(), 0, PAGE_SIZE);// 加载办件第一个办件信息
		} else {
			kindTypeAdapter.setKindTypes(kindtypes);
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
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = goverOnlineApproveAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (goverSaoonItemWrapper != null && goverSaoonItemWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitchDept = false;
				loadItem(currentKindtype.getKindType(),
						currentKindtype.getSubKindType(), visibleLastIndex + 1,
						visibleLastIndex + 1 + PAGE_SIZE);

			}

		}

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
		} else if (o instanceof GoverSaoonItem) {
			GoverSaoonItem goverSaoonItem = (GoverSaoonItem) o;
			if (goverSaoonItem.getType().equals("XK")) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("goverSaoonItem", goverSaoonItem);

				baseSlideFragment.slideLinstener.replaceFragment(null, -1,
						FragmentName.GOVERSALOONDETAIL_XK_FRAGMENT, bundle);
			}
		}

	}
}

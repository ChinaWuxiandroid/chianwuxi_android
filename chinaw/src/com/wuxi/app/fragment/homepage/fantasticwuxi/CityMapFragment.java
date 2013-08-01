package com.wuxi.app.fragment.homepage.fantasticwuxi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.wuxi.app.R;
import com.wuxi.app.util.BMapUtil;
import com.wuxi.app.view.MyMapView;
import com.wuxi.app.view.MyPoiOverlay;

/**
 * 
 * @author wanglu 泰得利通 城市地图
 * 
 */
public class CityMapFragment extends Fragment implements
		OnCheckedChangeListener, OnClickListener {
	private static final String KEY = "E1CDDDF289C13BC20A2320E02A058FBD9EBDDC1F";
	private BMapManager mBMapMan = null;
	private MyMapView mMapView = null;
	private static final double LATITUDE = 31.568;// 纬度
	private static final double LONGITUDE = 120.299;// 经度
	private static final String CITY = "无锡";

	private MKSearch mSearch;

	private RadioGroup mRadionGroup;
	private LinearLayout mseach1, msearch2, msearch3;
	private EditText et_keyword, et_start, et_end;
	private TextView tv_showPoint, tv_showBusRoute;
	private RadioButton rb_search, rb_bus, rb_car;
	private Button mBtnPre = null;// 上一个节点
	private Button mBtnNext = null;// 下一个节点
	private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用
	private MKRoute route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用
	private TransitOverlay transit = null;// 保存公交路线图层数据的变量，供浏览节点时使用
	private View viewCache = null;
	private TextView popupText = null;// 泡泡view
	int searchType = -1;// 记录搜索的类型，区分驾车/步行和公交
	private int nodeIndex = -2;// 节点索引,供浏览节点时使用
	private Activity context;
	private View view;
	private LayoutInflater mInflater;
	private static final int BUS = 0;// 公交
	private static final int DRIVER = 1;// 公交

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();

		mInflater = inflater;
		mBMapMan = new BMapManager(context);
		mBMapMan.init(KEY, null);

		view = inflater.inflate(R.layout.city_map_layout, null);

		mMapView = (MyMapView) view.findViewById(R.id.bmapView);
		initUI();

		mMapView.setBuiltInZoomControls(true);
		MapController mapController = mMapView.getController();
		GeoPoint point = new GeoPoint((int) (LATITUDE * 1E6),
				(int) (LONGITUDE * 1E6));
		mapController.setCenter(point);// 设置地图中心点
		mapController.setZoom(12);// 设置地图zoom级别
		mMapView.getController().enableClick(true);
		mSearch = new MKSearch();

		mSearch.init(mBMapMan, new MyMKSearchListener());// 初始化

		return view;

	}

	private void initUI() {

		mRadionGroup = (RadioGroup) view.findViewById(R.id.city_map_rg_tab);
		mRadionGroup.check(R.id.rb_search);

		mRadionGroup.setOnCheckedChangeListener(this);
		mseach1 = (LinearLayout) view.findViewById(R.id.ll_search1);
		msearch2 = (LinearLayout) view.findViewById(R.id.ll_search2);
		msearch3 = (LinearLayout) view.findViewById(R.id.ll_search3);

		et_keyword = (EditText) view.findViewById(R.id.city_map_et_key);
		et_start = (EditText) view.findViewById(R.id.city_map_et_start);
		et_end = (EditText) view.findViewById(R.id.city_map_et_end);

		tv_showPoint = (TextView) view.findViewById(R.id.city_map_tv_showmap);
		tv_showBusRoute = (TextView) view
				.findViewById(R.id.city_map_tv_bus_rote);
		tv_showPoint.setOnClickListener(this);
		tv_showBusRoute.setOnClickListener(this);

		rb_search = (RadioButton) mRadionGroup.findViewById(R.id.rb_search);
		rb_bus = (RadioButton) mRadionGroup.findViewById(R.id.rb_bus);
		rb_car = (RadioButton) mRadionGroup.findViewById(R.id.rb_car);

		mBtnPre = (Button) view.findViewById(R.id.pre);
		mBtnNext = (Button) view.findViewById(R.id.next);
		mBtnPre.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		createPaopao();
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onDestroy() {
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	public void onPause() {
		mMapView.onPause();

		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rb_search:
			mseach1.setVisibility(LinearLayout.VISIBLE);
			msearch2.setVisibility(LinearLayout.INVISIBLE);
			msearch3.setVisibility(LinearLayout.INVISIBLE);

			rb_search.setTextColor(Color.parseColor("#ffffff"));
			rb_bus.setTextColor(Color.parseColor("#6E4325"));
			rb_car.setTextColor(Color.parseColor("#6E4325"));
			mBtnPre.setVisibility(View.INVISIBLE);
			mBtnNext.setVisibility(View.INVISIBLE);
			mMapView.setBuiltInZoomControls(true);
			break;
		case R.id.rb_bus:
			mseach1.setVisibility(LinearLayout.INVISIBLE);
			msearch2.setVisibility(LinearLayout.VISIBLE);
			msearch3.setVisibility(LinearLayout.INVISIBLE);

			rb_search.setTextColor(Color.parseColor("#6E4325"));
			rb_bus.setTextColor(Color.parseColor("#ffffff"));
			rb_car.setTextColor(Color.parseColor("#6E4325"));

			mMapView.setBuiltInZoomControls(false);
			break;
		case R.id.rb_car:
			mseach1.setVisibility(LinearLayout.INVISIBLE);
			msearch2.setVisibility(LinearLayout.VISIBLE);
			msearch3.setVisibility(LinearLayout.INVISIBLE);

			rb_search.setTextColor(Color.parseColor("#6E4325"));
			rb_bus.setTextColor(Color.parseColor("#6E4325"));
			rb_car.setTextColor(Color.parseColor("#ffffff"));
			mMapView.setBuiltInZoomControls(false);
			break;
		case R.id.pre:
			nodeClick(mBtnPre);
			break;
		case R.id.next:
			nodeClick(mBtnNext);
			break;
		}

	}

	/**
	 * 发起路线规划搜索示例
	 * 
	 * @param v
	 */
	private void searchRoute(int type) {
		if (pop != null) {
			pop.hidePop();
		}
		// 重置浏览节点的路线数据
		route = null;
		transit = null;
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		// 处理搜索按钮响应

		String startName = et_start.getText().toString();
		String endName = et_end.getText().toString();
		if ("".equals(startName)) {
			Toast.makeText(context, "请输入起点地址", Toast.LENGTH_SHORT).show();
			return;
		} else if ("".equals(endName)) {
			Toast.makeText(context, "请输入终点地址", Toast.LENGTH_SHORT).show();
			return;
		}

		// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
		MKPlanNode stNode = new MKPlanNode();
		stNode.name = startName;
		MKPlanNode enNode = new MKPlanNode();
		enNode.name = endName;
		if (DRIVER == type) {
			mSearch.drivingSearch(CITY, stNode, CITY, enNode);// 驾车查询
		}

		if (BUS == type) {
			mSearch.transitSearch(CITY, stNode, enNode);// 搜索公交路线
		}
		

	}

	/**
	 * 节点浏览示例
	 * 
	 * @param v
	 */
	public void nodeClick(View v) {
		viewCache = mInflater.inflate(R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		if (searchType == 0 || searchType == 2) {
			// 驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
			if (nodeIndex < -1 || route == null
					|| nodeIndex >= route.getNumSteps())
				return;

			// 上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 0) {
				// 索引减
				nodeIndex--;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
			// 下一个节点
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps() - 1)) {
				// 索引加
				nodeIndex++;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
		}
		if (searchType == 1) {
			// 公交换乘使用的数据结构与其他不同，因此单独处理节点浏览
			if (nodeIndex < -1 || transit == null
					|| nodeIndex >= transit.getAllItem().size())
				return;

			// 上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 1) {
				// 索引减
				nodeIndex--;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						transit.getItem(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transit.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), transit
						.getItem(nodeIndex).getPoint(), 5);
			}
			// 下一个节点
			if (mBtnNext.equals(v)
					&& nodeIndex < (transit.getAllItem().size() - 2)) {
				// 索引加
				nodeIndex++;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						transit.getItem(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transit.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), transit
						.getItem(nodeIndex).getPoint(), 5);
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.city_map_tv_showmap:
			String keyWords = et_keyword.getText().toString();
			if (keyWords.equals("")) {
				Toast.makeText(context, "请输入你要搜索到内容", Toast.LENGTH_LONG).show();
				return;
			}

			mSearch.poiSearchInCity(CITY, keyWords);
			break;
		case R.id.city_map_tv_bus_rote:
			if (rb_bus.isChecked()) {
				searchRoute(BUS);//公交查询
			}

			if (rb_car.isChecked()) {
				searchRoute(DRIVER);//驾车查询
			}

			break;
		case R.id.pre:
			nodeClick(mBtnPre);//上一个节点
			break;
		case R.id.next:
			nodeClick(mBtnNext);//下一个节点
			break;

		}
	}

	/**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao() {

		// 泡泡点击响应回调
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {

			}
		};
		pop = new PopupOverlay(mMapView, popListener);
		MyMapView.pop = pop;
	}

	private class MyMKSearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {

		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {

		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {

			// 起点或终点有歧义，需要选择具体的城市列表或地址列表
			if (error == MKEvent.ERROR_ROUTE_ADDR) {

				return;
			}
			// 错误号可参考MKEvent中的定义
			if (error != 0 || res == null) {
				Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
				return;
			}

			searchType = 0;
			RouteOverlay routeOverlay = new RouteOverlay((Activity) context,
					mMapView);
			// 此处仅展示一个方案作为示例
			routeOverlay.setData(res.getPlan(0).getRoute(0));
			// 清除其他图层
			mMapView.getOverlays().clear();
			// 添加路线图层
			mMapView.getOverlays().add(routeOverlay);
			// 执行刷新使生效
			mMapView.refresh();
			// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// 移动地图到起点
			mMapView.getController().animateTo(res.getStart().pt);
			// 将路线数据保存给全局变量
			route = res.getPlan(0).getRoute(0);
			// 重置路线节点索引，节点浏览时使用
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);

		}

		@Override
		public void onGetPoiDetailSearchResult(int type, int error) {
			if (error != 0) {
				Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "成功，查看详情页面", Toast.LENGTH_SHORT).show();
			}
		}

		/**
		 * 关键词搜索哦
		 */

		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int error) {

			// 错误号可参考MKEvent中的定义
			if (error != 0 || res == null) {
				Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			// 将地图移动到第一个POI中心点
			if (res.getCurrentNumPois() > 0) {
				// 将poi结果显示到地图上
				MyPoiOverlay poiOverlay = new MyPoiOverlay((Activity) context,
						mMapView, mSearch);
				poiOverlay.setData(res.getAllPoi());
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(poiOverlay);
				mMapView.refresh();
				// 当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
				for (MKPoiInfo info : res.getAllPoi()) {
					if (info.pt != null) {
						mMapView.getController().animateTo(info.pt);
						break;
					}
				}
			} else if (res.getCityListNum() > 0) {
				// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
				String strInfo = "在";
				for (int i = 0; i < res.getCityListNum(); i++) {
					strInfo += res.getCityListInfo(i).city;
					strInfo += ",";
				}
				strInfo += "找到结果";
				Toast.makeText(context, strInfo, Toast.LENGTH_LONG).show();
			}

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {

		}

		/**
		 * 公交
		 */

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {

			// 起点或终点有歧义，需要选择具体的城市列表或地址列表
			if (error == MKEvent.ERROR_ROUTE_ADDR) {

				return;
			}
			if (error != 0 || res == null) {
				Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
				return;
			}

			searchType = 1;
			TransitOverlay routeOverlay = new TransitOverlay(context, mMapView);
			// 此处仅展示一个方案作为示例
			routeOverlay.setData(res.getPlan(0));
			// 清除其他图层
			mMapView.getOverlays().clear();
			// 添加路线图层
			mMapView.getOverlays().add(routeOverlay);
			// 执行刷新使生效
			mMapView.refresh();
			// 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// 移动地图到起点
			mMapView.getController().animateTo(res.getStart().pt);
			// 将路线数据保存给全局变量
			transit = routeOverlay;
			// 重置路线节点索引，节点浏览时使用
			nodeIndex = 0;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {

		}

	}

}

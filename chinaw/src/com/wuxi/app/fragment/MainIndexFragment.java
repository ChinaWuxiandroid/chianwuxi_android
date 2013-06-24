package com.wuxi.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.IndexGridAdapter;
import com.wuxi.app.adapter.IndexNewsListAdapter;
import com.wuxi.app.fragment.index.SlideLevelFragment;

/**
 * 
 * @author 主页界面 
 *
 */
public class MainIndexFragment extends BaseFragment {

	private View view;
	private Context context;

	/** —————————ListView——————————— **/
	private ListView listView;
	private IndexNewsListAdapter listAdapter;
	public static final int[] newslist_viewid = { R.id.index_num_text,R.id.index_news_title};
	public static final String[] newslist_dataName = { "num_text", "title_text" };

	/** ————Gridview—————— **/
	private GridView gridView;
	private IndexGridAdapter gridAdapter;
	public static final int[] Grid_viewid = { R.id.index_gridview_image,R.id.index_gridview_texttitle };
	public static final String[] Grid_dataName = { "grid_image", "grid_text" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.main_index_fragment_layout, null);
		context = this.getActivity();
		InitUI();
		return view;
	}

	private void InitUI() {
		gridView = (GridView) view.findViewById(R.id.gridview);
		listView = (ListView) view.findViewById(R.id.index_news_list);
		loadList();
		LoadGrid();
		gridView.setOnItemClickListener(GridviewOnclick);
	}

	private void LoadGrid() {
		List<Map<String, Object>> list = getGridData();
		int size = list.size();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int allWidth = (int) (110 * (size / 2) * density);
		int itemWidth = (int) (100 * density);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
		gridView.setLayoutParams(params);
		gridView.setColumnWidth(itemWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setNumColumns(size / 2);
		gridAdapter = new IndexGridAdapter(LayoutInflater.from(context),R.layout.index_gridview_layout, Grid_viewid, list, Grid_dataName);
		gridView.setAdapter(gridAdapter);
	}

	//gridview测试数据
	private List<Map<String, Object>> getGridData() {

		int[] image = { R.drawable.index_msg_public,
				R.drawable.index_meili_wuxi, R.drawable.index_msg_center,
				R.drawable.index_gover_holl, R.drawable.index_server_public,
				R.drawable.index_hudong };
		String[] name = { "政府信息公开", "魅力锡城", "资讯中心", "政务大厅", "公共服务", "政民互动"};

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Grid_dataName[0], image[i]);
			map.put(Grid_dataName[1], name[i]);
			list.add(map);
		}
		return list;
	}

	private void loadList(){
		List<Map<String, Object>> list = getListData();
		listAdapter = new IndexNewsListAdapter(LayoutInflater.from(context), R.layout.index_newslist_layout, newslist_viewid, list, newslist_dataName);
		listView.setAdapter(listAdapter);
		setViewHeight(listView);
	}
	
	//新闻 公告的测试数据
	private List<Map<String, Object>> getListData() {
		String[] name = { "黄丽鑫：学习吴仁宝同志 学习华西经验 扎实推进基层党组织建设","抓紧舒楠现代化建设示范区 与时俱进开创“两个率先”新局面" };
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(newslist_dataName[0], i+1);
			map.put(newslist_dataName[1], name[i]);
			list.add(map);
		}
		return list;
	}

	
	public void setViewHeight(ListView listView) {
		IndexNewsListAdapter listAdapter = (IndexNewsListAdapter) listView.getAdapter();
        if (listAdapter == null) 
            return;
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

	private OnItemClickListener GridviewOnclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			// TODO Auto-generated method stub
			SlideLevelFragment saveFragment = new SlideLevelFragment();
			saveFragment.setManagers(managers);
			saveFragment.setPosition(position);
			managers.IntentFragment(saveFragment);
		}
	};
}

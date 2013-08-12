package com.wuxi.app.fragment.search;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.SearchFragmentAdapter;
import com.wuxi.app.engine.SearchService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.AdvancedSearchUtil;
import com.wuxi.domain.SearchResultWrapper;
import com.wuxi.domain.SearchResultWrapper.SearchPage;
import com.wuxi.domain.SearchResultWrapper.SearchResult;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 高级搜索的结果列表的fragment
 * @author 杨宸 智佳
 * */

public class AdvancedSearchResultListFragment extends BaseSlideFragment  implements OnClickListener,OnScrollListener, OnItemClickListener{
	protected static final int RESULTS_LOAD_SUCESS = 0;// 搜索结果获取成功
	protected static final int RESULTS_LOAD_FAIL = 1;// 搜索结果获取失败
	protected static final int PAGE_COUNT = 5;// 每次加载的条数

	protected ListView resultListView;						// 搜索结果ListView 
	private ProgressBar normalSearch_pb;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private boolean isSwitch = false;// 切换
	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isLoading = false;
	private ProgressBar pb_loadmoore;
	private SearchFragmentAdapter SearchFragmentAdapter;

	private LinearLayout searchResultSummary_layout;
	private TextView query_txtView;
	private TextView result_textView;

	protected SearchResultWrapper searchResultWrapper;
	protected SearchPage page;
	protected List<SearchResult> results=new ArrayList<SearchResultWrapper.SearchResult>();

	private int pageNum=1;   //加载的页数  
	private String queryContent="";
	
	AdvancedSearchUtil searchUtil;
	
	public void setSearchUtil(AdvancedSearchUtil searchUtil){
		this.searchUtil=searchUtil;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RESULTS_LOAD_SUCESS:
				normalSearch_pb.setVisibility(ProgressBar.GONE);
				showSearchResults();
				break;

			case RESULTS_LOAD_FAIL:
				normalSearch_pb.setVisibility(ProgressBar.GONE);
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	
	
	@Override
	public void initUI() {
		super.initUI();
		searchUtil=(AdvancedSearchUtil) this.getArguments().get("searchUtil");
		findView();
		
		inflateResultListView(searchUtil);
	}
	
	public void findView(){
		resultListView=(ListView)view.findViewById(R.id.search_listView_result);
		normalSearch_pb=(ProgressBar) view.findViewById(R.id.search_normalsearch_pb);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		resultListView.addFooterView(loadMoreView);// 为listView添加底部视图
		resultListView.setOnScrollListener(this);// 增加滑动监听
		resultListView.setOnItemClickListener(this);
		loadMoreButton.setOnClickListener(this);

		searchResultSummary_layout=(LinearLayout)view.findViewById(R.id.search_resultsummary_layout);
		query_txtView=(TextView)view.findViewById(R.id.search_result_title);
		result_textView=(TextView)view.findViewById(R.id.search_result);
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.loadMoreButton:

			if (searchResultWrapper != null && searchResultWrapper.getPage()!=null) {// 还有下一条记录
				SearchPage page=searchResultWrapper.getPage();
				if((Integer.valueOf(page.getPagesize())*Integer.valueOf(page.getCurrentpage()))<Integer.valueOf(page.getHitcount())){
					isSwitch = false;
					loadMoreButton.setText("loading.....");
					loadSearchResults(searchUtil,++pageNum);
				}
			}
			break;
		}
	}
	/*
	 * 显示搜索结果
	 * */
	public void inflateResultListView(AdvancedSearchUtil searchUtil){
		loadSearchResults(searchUtil,pageNum);
	}

	public void loadSearchResults(final AdvancedSearchUtil searchUtil,final int pagenum){
		if (isFirstLoad || isSwitch) {
			normalSearch_pb.setVisibility(ProgressBar.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				SearchService searchService = new SearchService(context);
				try {
					searchResultWrapper=searchService.getAdvancedSearchResult(searchUtil, Constants.Urls.SEARCH_SITENAME, pagenum);
					page=searchResultWrapper.getPage();
					results=searchResultWrapper.getResults();
					if (page != null&&results!=null) {
						msg.what = RESULTS_LOAD_SUCESS;
					} else {
						msg.what = RESULTS_LOAD_FAIL;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = RESULTS_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = RESULTS_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = RESULTS_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	public void showSearchResults(){

		searchResultSummary_layout.setVisibility(View.VISIBLE);
		query_txtView.setText(queryContent);
		result_textView.setText("共 "+page.getHitcount()+"项     以下是第  1-"+
				(Integer.valueOf(page.getPagesize())*Integer.valueOf(page.getCurrentpage()))+
				" 项 （搜索用时 "+page.getSearchtime()+" 秒）");
		
		if (isFirstLoad) {
			SearchFragmentAdapter=new SearchFragmentAdapter(context,results);
			isFirstLoad = false;
			resultListView.setAdapter(SearchFragmentAdapter);
			normalSearch_pb.setVisibility(ProgressBar.GONE);
			isLoading = false;
		} else {
			results=searchResultWrapper.getResults();
			if (isSwitch) {
				SearchFragmentAdapter.setResults(results);
				normalSearch_pb.setVisibility(ProgressBar.GONE);
			} else {
				SearchFragmentAdapter.addResults(results);
				System.out.println("SearchFragmentAdapter.getCount():"+SearchFragmentAdapter.getCount());
			}
			SearchFragmentAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
			resultListView.setSelection(visibleLastIndex
					- visibleItemCount + 1); // 设置选中项
			isLoading = false;
		}

		if (searchResultWrapper != null && searchResultWrapper.getPage()!=null) {// 还有下一条记录
			SearchPage page=searchResultWrapper.getPage();
			if((Integer.valueOf(page.getPagesize())*Integer.valueOf(page.getCurrentpage()))<Integer.valueOf(page.getHitcount())){
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			}
		} else {
			resultListView.removeFooterView(loadMoreView);
		}

	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.search_advanced_searchresult_layout;
	}

	@Override
	protected String getTitleText() {
		return "全站搜索";
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		SearchResult result=(SearchResult) adapterView.getItemAtPosition(position);
		BaseSlideFragment baseSlideFragment=(BaseSlideFragment)this;
		Bundle bundle=new Bundle();
		bundle.putSerializable("result",result);
		baseSlideFragment.slideLinstener.replaceFragment(null, position,
				Constants.FragmentName.SEARCH_DETAIL_FRAGMENT, bundle);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = SearchFragmentAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}
	

}

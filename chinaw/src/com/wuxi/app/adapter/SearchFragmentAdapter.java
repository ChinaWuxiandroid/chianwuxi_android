package com.wuxi.app.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.SearchResultWrapper.SearchResult;

public class SearchFragmentAdapter extends BaseAdapter{
	private Context context;
	private List<SearchResult> resultList;
	private LayoutInflater inflater;

	public void setResults(List<SearchResult> resultList) {
		this.resultList = resultList;
	}
	
	public void addResults(List<SearchResult> results) {
		this.resultList.addAll((results));
	}
	
	public SearchFragmentAdapter(Context context,List<SearchResult> resultList){
		this.context=context;
		this.resultList=resultList;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return resultList.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		TextView title_text;// 标题
		WebView webview;
		TextView link_text;// 时间
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SearchResult result = resultList.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.search_result_list_item, null);
			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.search_result_listitem_titleAndScore);
			viewHolder.webview = (WebView) convertView
					.findViewById(R.id.search_result_listitem_webview);
			viewHolder.link_text = (TextView) convertView
					.findViewById(R.id.search_result_listitem_link);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		convertView.setFocusable(false);
		String title = "",score="",summaryContent="";
		title=result.getTitle().toString();
		summaryContent=result.getSummarycontent().toString();

		score=String.valueOf((int)(Double.valueOf(result.getScore())*100));

		DecimalFormat df = new DecimalFormat("0.00"); 
		int sizeb=Integer.valueOf(result.getSize());
		String sizeKb=df.format(sizeb/1024.0)+"kb";
		//标题 和 相关度
		viewHolder.title_text.setText(title+"  (关联度："+score+"%)");
		//链接和大小
		viewHolder.link_text.setText(result.getLink()+"  - "+sizeKb);

		viewHolder.webview.getSettings().setTextSize(TextSize.SMALLER);
		viewHolder.webview.setClickable(false);
//		viewHolder.webview.getSettings().setUseWideViewPort(true);
//		viewHolder.webview.getSettings().setBuiltInZoomControls(true);
//		viewHolder.webview.getSettings().setLoadWithOverviewMode(true);

		viewHolder.webview.getSettings().setDefaultTextEncodingName("utf-8");
		viewHolder.webview.loadDataWithBaseURL(null,standardHtmlStr(summaryContent), "text/html", "utf-8",null);
		standardHtmlStr(summaryContent);
		return convertView;
	}
	
	public String standardHtmlStr(String htmlStr){
		String standartStr="";
		standartStr=htmlStr.replace("<\\/", "</");
		return standartStr;
	}

	
}

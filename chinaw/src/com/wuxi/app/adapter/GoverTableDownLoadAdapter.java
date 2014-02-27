package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.GoverTableDownLoad;

/**
 * 
 * @author wanglu 泰得利通 政务大厅，表格下载适配器
 * 
 */
public class GoverTableDownLoadAdapter extends BaseAdapter {

	private List<GoverTableDownLoad> goverTableDownLoads;
	

	public void setGoverTableDownLoads(List<GoverTableDownLoad> goverTableDownLoads) {
		this.goverTableDownLoads = goverTableDownLoads;
	}


	private Context context;

	public GoverTableDownLoadAdapter(List<GoverTableDownLoad> goverTableDownLoads, Context context) {
		this.goverTableDownLoads=goverTableDownLoads;
		this.context = context;
	}

	@Override
	public int getCount() {
		return goverTableDownLoads.size();
	}

	@Override
	public Object getItem(int position) {
		return goverTableDownLoads.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView gover_tb_name;
		TextView gover_tb_filename;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		GoverTableDownLoad goverTableDownLoad = this.goverTableDownLoads.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View
					.inflate(context, R.layout.gover_tabledownload_item, null);

			viewHolder = new ViewHolder();

			viewHolder.gover_tb_name = (TextView) convertView
					.findViewById(R.id.gover_tb_name);

			viewHolder.gover_tb_filename = (TextView) convertView
					.findViewById(R.id.gover_tb_filename);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.gover_tb_name.setText(goverTableDownLoad.getName());
		viewHolder.gover_tb_filename.setText(goverTableDownLoad.getFilename());
		
	
		
		return convertView;

	}
	
	
	public void addItem(GoverTableDownLoad goverTableDownLoad){
		this.goverTableDownLoads.add(goverTableDownLoad);
	}

}

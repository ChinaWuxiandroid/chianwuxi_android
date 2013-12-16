/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VideoGuestPresenceAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 嘉宾风采业务类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-26 上午9:39:43
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.util.AsynLoadImageUtil;
import com.wuxi.domain.GuestPresenceWrapper.GuestPresence;

/**
 * @类名： VideoGuestPresenceAdapter
 * @描述： 嘉宾风采业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-26 上午9:39:43
 * @修改时间：
 * @修改描述：
 */
public class VideoGuestPresenceAdapter extends BaseAdapter {

	//图片加载路径
	private static final String URL = "http://www.wuxi.gov.cn";

	private Context context;

	private List<GuestPresence> presences;

	/**
	 * @方法： VideoGuestPresenceAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param presences
	 */
	public VideoGuestPresenceAdapter(Context context,
			List<GuestPresence> presences) {
		this.context = context;
		this.presences = presences;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param presence
	 */
	public void addItem(GuestPresence presence) {
		this.presences.add(presence);
	}

	/**
	 * @param presences
	 *            要设置的 presences
	 */
	public void setPresences(List<GuestPresence> presences) {
		this.presences = presences;
	}

	@Override
	public int getCount() {
		return presences.size();
	}

	@Override
	public Object getItem(int position) {
		return presences.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-26 上午11:46:35
	 * @修改时间： 
	 * @修改描述：
	 */
	class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.video_guest_presence_list_layout, null);

			holder = new ViewHolder();

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.video_guest_presence_list_imageview);
			holder.textView = (TextView) convertView
					.findViewById(R.id.video_guest_presence_list_title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (presences.get(position).getRecommendPictures() != null) {
			
			String url = URL + presences.get(position).getRecommendPictures();
			
			//异步加载图片
			AsynLoadImageUtil util = new AsynLoadImageUtil();
			
			util.showImageAsyn(holder.imageView, url, R.drawable.video_bg);
			
		}else {
			holder.imageView.setImageResource(R.drawable.video_bg);
		}
		
		holder.textView.setText(presences.get(position).getTitle());

		return convertView;
	}

}

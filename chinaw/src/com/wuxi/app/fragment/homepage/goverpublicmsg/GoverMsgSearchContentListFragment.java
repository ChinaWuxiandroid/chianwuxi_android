package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ApplyDeptService;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.ApplyDept;
import com.wuxi.domain.Channel;
import com.wuxi.domain.FifterContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 政府信息公开   里  带有部门/区县   时间  的 内容列表搜索Fragment
 * @author 杨宸  智佳
 * */

public class GoverMsgSearchContentListFragment extends BaseFragment implements OnClickListener{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;
	protected static final int CHANNELLIST_CONTENT_ID=R.id.govermsg_search_content_framelayout;

	private static final int LOAD_DEPT_SUCCESS=1;
	private static final int LOAD_DEPT_FAILED=2;
	private static final int LOAD_YEAR_SUCCESS=3;
	private static final int LOAD_YEAR_FAILED=4;
	private static final int LOAD_CONTENTLIST_SUCCESS=5;
	private static final int LOAD_CONTENTLIST_FAILED=6;

	private MenuItem parentMenuItem;
	private Channel channel;

	List<ApplyDept> depts;

	private Spinner partment_sp;
	private Spinner year_sp;
	private Button search_imbtn;

	private static String DEFAULT_DEPT_FIFTER="按部门筛选";
	private static String DEFAULT_ZONE_FIFTER="按县区筛选";
	private String deptStrFifter=DEFAULT_DEPT_FIFTER;
	private String zoneStrFifter=DEFAULT_ZONE_FIFTER;
	//年份默认今年
	private int DEFAULT_YEAR_FIFTER=2013;
	private int yearFifter=DEFAULT_YEAR_FIFTER;   //2013

	private static final String[] zoneArr={DEFAULT_ZONE_FIFTER,"江阴","宜兴","惠山","滨湖","崇安","南长 ","北塘","无锡新区"};
	
	public static final int DEPT_TYPE=1;
	public static final int ZONE_TYPE=2;
	private int filterType=DEPT_TYPE;  //检索过滤 类型  1-部门时间型(缺省类型)   2-区县时间型  

	public void setFifterType(int type){
		this.filterType=type;
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD_DEPT_SUCCESS:
				showDept();
				break;
			case LOAD_DEPT_FAILED:
				Toast.makeText(context, "部门信息加载失败", Toast.LENGTH_SHORT).show();
				break;
			case	LOAD_YEAR_SUCCESS:
				Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
				break;
			case LOAD_YEAR_FAILED:
				Toast.makeText(context, "下载出错，稍后再试", Toast.LENGTH_SHORT).show();
				break;
			case LOAD_CONTENTLIST_SUCCESS:

				break;
			case LOAD_CONTENTLIST_FAILED:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		}
	};

	public void setParentMenuItem(MenuItem parentMenuItem){
		this.parentMenuItem=parentMenuItem;
	}

	public void setChannel(Channel channel){
		this.channel=channel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.govermsg_search_contentlist_layout, null);
		mInflater = inflater;
		context = getActivity();


		initView();
		return view;
	}

	public void initView(){
		partment_sp=(Spinner)view.findViewById(R.id.govermsg_search_spinner_partment);
		year_sp=(Spinner)view.findViewById(R.id.govermsg_search_spinner_year);
		search_imbtn=(Button)view.findViewById(R.id.govermsg_search_button_search);
		search_imbtn.setOnClickListener(this);
	
		loadContentList();

		initFilter(filterType);
	}

	public void initFilter(int Type){
		Calendar c = Calendar.getInstance();
		DEFAULT_YEAR_FIFTER=c.get(Calendar.YEAR);   //2013
		yearFifter=DEFAULT_YEAR_FIFTER;
		switch(Type){
		case DEPT_TYPE:
			loadDeptData();
			initYearSpinner();
			break;
		case ZONE_TYPE:
			initCountrySpinner();
			initYearSpinner();
			break;
		}
	}
	
	/**
	 * 初始化区县过滤信息 （已屏蔽）
	 * */
	public void initCountrySpinner(){

//		ArrayAdapter<String> country_Spinner_adapter = new ArrayAdapter<String>(context,
//				R.layout.my_simple_spinner_item_layout, zoneArr);
//		country_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_medium_dropdown_item);
//		partment_sp.setAdapter(country_Spinner_adapter);
//		partment_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View view,
//					int arg2, long arg3) {
//				zoneStrFifter=((TextView)view).getText().toString();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
//		partment_sp.setVisibility(View.VISIBLE);
		partment_sp.setVisibility(View.GONE);
	}

	public void loadDeptData(){

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				ApplyDeptService applyDeptService = new ApplyDeptService(context);
				try {
					depts = applyDeptService.getDepts(Constants.Urls.APPLYDEPT_URL);
					if (depts != null) {
						msg.what = LOAD_DEPT_SUCCESS;
						CacheUtil.put(Constants.CacheKey.DEPT_KEY, depts);// 放入缓存
					} else {
						msg.what = LOAD_DEPT_FAILED;
						msg.obj = "没有获取到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_FAILED;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_SUCCESS;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	private void showDept(){
		ApplyDept deptDefault=new ApplyDept();
		deptDefault.setDepId("0");
		deptDefault.setDepName(DEFAULT_DEPT_FIFTER);
		depts.add(0, deptDefault);

		DeptAdapter partment_Spinner_adapter = new DeptAdapter();
		if(partment_sp.getVisibility()==View.VISIBLE){
			partment_sp.setAdapter(partment_Spinner_adapter);
			partment_sp.setOnItemSelectedListener(partment_Spinner_adapter);
			partment_sp.setVisibility(View.VISIBLE);
		}
	
	}

	public void initYearSpinner(){
		List<String> years = TimeFormateUtil
				.getYears(TimeFormateUtil.START_YEAR);

		years.add(0, "按年份");

		ArrayAdapter year_Spinner_adapter = new ArrayAdapter<String>(context,
				R.layout.my_simple_spinner_item_layout, years);
		year_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_medium_dropdown_item);
		year_sp.setAdapter(year_Spinner_adapter);
		year_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				String buffYearStr=((TextView)view).getText().toString();
				if(position!=0){
					yearFifter=Integer.valueOf(((TextView)view).getText().toString());
				}
				else{
					yearFifter=DEFAULT_YEAR_FIFTER;    //表示按年份来
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		year_sp.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.govermsg_search_button_search:
			search();
			break;
		}
	}

	//开始检索
	public void search(){
		GoverMsgFifterContentListFragment goverMsgFifterContentListFragment=new GoverMsgFifterContentListFragment();
		String id=null;
		if (channel != null) {
			id = channel.getChannelId();
		} else if (parentMenuItem != null) {
			id = parentMenuItem.getChannelId();
		}
		FifterContentWrapper fifter=new FifterContentWrapper(id);
		switch(filterType){
		//按部门 时间  检索
		case DEPT_TYPE:
			if(DEFAULT_DEPT_FIFTER.equals(deptStrFifter))
				deptStrFifter=null;
			if(yearFifter==DEFAULT_YEAR_FIFTER)
				yearFifter=-1;
			fifter.setDept(deptStrFifter);
			fifter.setYear(yearFifter);
			break;
			//按区县 时间  检索
		case ZONE_TYPE:
			if(DEFAULT_ZONE_FIFTER.equals(zoneStrFifter))
				zoneStrFifter=null;
			if(yearFifter==DEFAULT_YEAR_FIFTER)
				yearFifter=-1;
			fifter.setZone(zoneStrFifter);
			fifter.setYear(yearFifter);
			break;
		}
		goverMsgFifterContentListFragment.setContentFifter(fifter);
		bindFragment(goverMsgFifterContentListFragment);
	}

	private void loadContentList(){
		GoverMsgContentListFragment goverMsgContentListFragment=new GoverMsgContentListFragment();
		goverMsgContentListFragment.setBaseSlideFragment(this.baseSlideFragment);
		if(parentMenuItem!=null){
			goverMsgContentListFragment.setParentItem(parentMenuItem);
		}
		else if(channel!=null){
			goverMsgContentListFragment.setChannel(channel);
		}

		bindFragment(goverMsgContentListFragment);
	}

	private void bindFragment(BaseFragment fragment) {		
		FragmentManager manager = getActivity().getSupportFragmentManager();
		fragment.setBaseSlideFragment(this.baseSlideFragment);
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(CHANNELLIST_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();	
	}

	public class DeptAdapter extends BaseAdapter implements OnItemSelectedListener{

		@Override
		public int getCount() {
			return depts.size();
		}

		@Override
		public Object getItem(int position) {
			return depts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public class ViewHolder{
			TextView tv_dept;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ApplyDept dept = depts.get(position);
			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.comstuom_spinner_item_layout, null);
				viewHolder = new ViewHolder();
				TextView tv = (TextView) convertView.findViewById(R.id.sp_tv);
				viewHolder.tv_dept = tv;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_dept.setText(dept.getDepName());
			return convertView;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			deptStrFifter= depts.get(position).getDepName();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}

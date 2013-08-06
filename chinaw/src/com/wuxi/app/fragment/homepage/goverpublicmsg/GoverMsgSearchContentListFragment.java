package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

public class GoverMsgSearchContentListFragment extends BaseFragment implements OnClickListener{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;
	protected static final int CHANNELLIST_CONTENT_ID=R.id.govermsg_search_content_framelayout;
	
	private MenuItem parentMenuItem;
	private Channel channel;
	
	private Spinner partment_sp;
	private Spinner year_sp;
	private Button search_imbtn;
	
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
		
		ArrayAdapter partment_Spinner_adapter = ArrayAdapter.createFromResource(
				context, R.array.mailBoxType,R.layout.my_spinner_medium_item);
		partment_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_medium_dropdown_item);
		partment_sp.setAdapter(partment_Spinner_adapter);
		partment_sp.setVisibility(View.VISIBLE);
		
		ArrayAdapter year_Spinner_adapter = ArrayAdapter.createFromResource(
				context, R.array.spinnerYear,R.layout.my_spinner_medium_item);
		year_Spinner_adapter.setDropDownViewResource(R.layout.my_spinner_medium_dropdown_item);
		year_sp.setAdapter(year_Spinner_adapter);
		year_sp.setVisibility(View.VISIBLE);
		
		search_imbtn.setOnClickListener(this);
		
		loadContentList();
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.govermsg_search_button_search:
			search();
			break;
		}
	}
	
	public void search(){
		
	}
	
	private void loadContentList(){
		GoverMsgContentListFragment goverMsgContentListFragment=new GoverMsgContentListFragment();
		if(parentMenuItem!=null){
			goverMsgContentListFragment.setParentItem(parentMenuItem);
		}
		else if(channel!=null){
			goverMsgContentListFragment.setChannel(channel);
		}
		
		bindFragment(goverMsgContentListFragment);
	}
	
	private void bindFragment(Fragment fragment) {		
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(CHANNELLIST_CONTENT_ID, fragment);
		ft.commit();	
	}
}

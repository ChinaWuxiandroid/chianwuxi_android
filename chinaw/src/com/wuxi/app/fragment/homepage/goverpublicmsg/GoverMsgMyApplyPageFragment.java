package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.MyApplyPageService;
import com.wuxi.app.fragment.commonfragment.PagingLoadListFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.CommonDataWrapper;
import com.wuxi.domain.MyApplyPageWrapper;
import com.wuxi.domain.MyApplyPageWrapper.MyApplyPage;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

public class GoverMsgMyApplyPageFragment extends PagingLoadListFragment{
	private MyApplyPageWrapper myApplyPageWrapper;
	private MyApplyPageAdapter adapter;
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		MyApplyPage myPage=(MyApplyPage) adapterView.getItemAtPosition(position);
		
		GoverMyApplyPageContentFragment goverMyApplyPageContentFragment=new GoverMyApplyPageContentFragment();
		goverMyApplyPageContentFragment.setPage(myPage);
		bindFragment(goverMyApplyPageContentFragment);
	}

	@Override
	protected List<Object> getContents() {
		// TODO Auto-generated method stub
		List<Object> objects=new ArrayList<Object>();
		if(myApplyPageWrapper.getData()!=null){
			for(MyApplyPage apply:myApplyPageWrapper.getData()){
				objects.add(apply);
			}
		}
		return objects;
	}

	@Override
	protected BaseAdapter getAdapter() {
		// TODO Auto-generated method stub
		adapter= new MyApplyPageAdapter(myApplyPageWrapper.getData(),context);
		return adapter;
	}

	@Override
	protected void addItem(Object object) {
		// TODO Auto-generated method stub
		adapter.addItem((MyApplyPage) object);
	}

	@Override
	protected void switchContents() {
		// TODO Auto-generated method stub
		adapter.setContents(myApplyPageWrapper.getData());
	}

	@Override
	protected CommonDataWrapper getWarpper(int start, int end) {
		
		myApplyPageWrapper=new MyApplyPageWrapper();
		MyApplyPageService service=new MyApplyPageService(context);
		try {
			myApplyPageWrapper=service.getMyApplyPages(Constants.SharepreferenceKey.TEST_ACCESSTOKEN, start, end);
		} catch (NetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myApplyPageWrapper;
	}

	public class MyApplyPageAdapter extends BaseAdapter{
		
		private List<MyApplyPage> contents;
		private Context context;
		public void setContents(List<MyApplyPage> contents) {
			this.contents = contents;
		}

		public MyApplyPageAdapter(List<MyApplyPage> contents, Context context) {

			this.contents = contents;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contents.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return contents.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class ViewHolder {
			TextView title_text;// 标题
			TextView code_text;
			TextView dept_text;
			TextView time_text;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyApplyPage content = contents.get(position);
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.myapplypage_list_item, null);
				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(R.id.myapplypage_item_title);
				viewHolder.code_text = (TextView) convertView
						.findViewById(R.id.myapplypage_item_code);
				viewHolder.dept_text = (TextView) convertView
						.findViewById(R.id.myapplypage_item_answerDep);
				viewHolder.time_text = (TextView) convertView
						.findViewById(R.id.myapplypage_item_applydate);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.title_text.setText(content.getTitle());
			viewHolder.code_text.setText(String.valueOf(content.getCode()));
			viewHolder.dept_text.setText(content.getAnswerDep());
			viewHolder.time_text.setText(content.getApplyDate());
			return convertView;
		}
		
		public void addItem(MyApplyPage content) {
			this.contents.add(content);
		}
	}
	
	public void bindFragment(BaseFragment fragment){
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(FRAMELAYOUT_ID, fragment);

		ft.commit();
	}
}

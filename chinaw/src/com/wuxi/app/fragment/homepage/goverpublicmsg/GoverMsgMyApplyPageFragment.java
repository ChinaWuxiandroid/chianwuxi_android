package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	protected List<Object> getContents() {
		// TODO Auto-generated method stub
		List<Object> objects=new ArrayList<Object>();
		for(MyApplyPage apply:myApplyPageWrapper.getData()){
			objects.add(apply);
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
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public void addItem(MyApplyPage content) {
			this.contents.add(content);
		}
	}
}

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
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.CommonDataWrapper;
import com.wuxi.domain.MyApplyPageWrapper;
import com.wuxi.domain.MyApplyPageWrapper.MyApplyPage;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * @类名： GoverMsgMyApplyPageFragment
 * @描述： 政府信息公开 依申请公开 我的依申请公开办件答复 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-22 上午11:17:49
 * @修改时间：
 * @修改描述：
 */
public class GoverMsgMyApplyPageFragment extends PagingLoadListFragment {

	private MyApplyPageWrapper myApplyPageWrapper;
	private MyApplyPageAdapter adapter;

	@Override
	protected void initUI() {
		super.initUI();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		MyApplyPage myPage = (MyApplyPage) adapterView
				.getItemAtPosition(position);

		GoverMyApplyPageContentFragment goverMyApplyPageContentFragment = new GoverMyApplyPageContentFragment();
		goverMyApplyPageContentFragment.setPage(myPage);
		bindFragment(goverMyApplyPageContentFragment);
	}

	@Override
	protected List<Object> getContents() {
		List<Object> objects = new ArrayList<Object>();
		if (myApplyPageWrapper.getData() != null) {
			for (MyApplyPage apply : myApplyPageWrapper.getData()) {
				objects.add(apply);
			}
		}
		return objects;
	}

	@Override
	protected CommonDataWrapper getWarpper(int start, int end) {

		myApplyPageWrapper = new MyApplyPageWrapper();
		MyApplyPageService service = new MyApplyPageService(context);
		try {
			myApplyPageWrapper = service.getMyApplyPages(
					SystemUtil.getAccessToken(context), start, end);

		} catch (NetException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ResultException e) {
			e.printStackTrace();
		}
		return myApplyPageWrapper;
	}

	@Override
	protected BaseAdapter getAdapter() {

		for (int i = 0; i < myApplyPageWrapper.getData().size(); i++) {
			System.out.println(myApplyPageWrapper.getData().get(i).getTitle());
		}

		adapter = new MyApplyPageAdapter(myApplyPageWrapper.getData(), context);
		return adapter;
	}

	@Override
	protected void addItem(Object object) {
		adapter.addItem((MyApplyPage) object);
	}

	@Override
	protected void switchContents() {
		adapter.setContents(myApplyPageWrapper.getData());
	}

	/**
	 * @类名： MyApplyPageAdapter
	 * @描述： 适配器
	 * @作者： 罗森
	 * @创建时间： 2013 2013-10-11 下午5:44:02
	 * @修改时间：
	 * @修改描述：
	 */
	public class MyApplyPageAdapter extends BaseAdapter {

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
			if (contents.size() > 0) {
				return contents.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return contents.get(position);
		}

		@Override
		public long getItemId(int position) {
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

	/**
	 * @方法： bindFragment
	 * @描述： 绑定视图
	 * @param fragment
	 */
	private void bindFragment(BaseFragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(FRAMELAYOUT_ID, fragment);

		ft.commitAllowingStateLoss();
	}

}

package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.DeptSpinnerAdapter;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;
import com.wuxi.app.engine.DeptService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.Dept;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 网上审批
 * 
 */
public class OnlineApprovalFragment extends GoverSaloonContentFragment {

	protected static final int LOAD_DEPT_SUCCESS = 0;
	protected static final int LOAD_DEPT_FAIL = 1;
	private Spinner gover_oline_filter;
	private ListView gover_online_approval_lv;

	private List<Dept> depts;// 部门
	private String[] str = { "事项：伪造，编造，买卖计划生育证明", "事项：伪造，编造，买卖计划生育证明",
			"事项：伪造，编造，买卖计划生育证明" };

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case LOAD_DEPT_SUCCESS:
				showDept();
				break;
			case LOAD_DEPT_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;

			}

		}
	};

	public void initUI() {
		super.initUI();
		gover_oline_filter = (Spinner) view
				.findViewById(R.id.gover_oline_filter);
		gover_online_approval_lv = (ListView) view
				.findViewById(R.id.gover_online_approval_lv);
		gover_online_approval_lv.setAdapter(new GoverOnlineApproveAdapter(str,
				context));

		loadDept();
	}

	static class ViewHolder {
		TextView tv_dept;
	}

	
	/**
	 * 
	 * wanglu 泰得利通 获取部门信息
	 */
	@SuppressWarnings("unchecked")
	private void loadDept() {

		if (CacheUtil.get(Constants.CacheKey.DEPT_KEY) != null) {
			depts = (List<Dept>) CacheUtil.get(Constants.CacheKey.DEPT_KEY);
			showDept();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				DeptService deptService = new DeptService(context);
				try {
					depts = deptService.getDepts();
					if (depts != null) {
						msg.what = LOAD_DEPT_SUCCESS;
						CacheUtil.put(Constants.CacheKey.DEPT_KEY, depts);// 放入缓存
					} else {
						msg.what = LOAD_DEPT_FAIL;
						msg.obj = "没有获取到数据";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_DEPT_FAIL;
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

	/**
	 * 
	 * wanglu 泰得利通 显示部门信息
	 */
	private void showDept() {
		depts.add(new Dept("按审批部门赛选(默认为可全部审批事项)"));
		gover_oline_filter.setAdapter(new DeptSpinnerAdapter(depts, context));
		gover_oline_filter.setSelection(depts.size()-1);

	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_onlineapproval;
	}

}

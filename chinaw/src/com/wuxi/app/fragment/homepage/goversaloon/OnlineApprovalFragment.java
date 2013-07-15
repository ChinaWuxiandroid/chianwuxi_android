package com.wuxi.app.fragment.homepage.goversaloon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;

/**
 * 
 * @author wanglu 泰得利通 网上审批
 * 
 */
public class OnlineApprovalFragment extends BaseFragment {

	private View view;
	private Spinner gover_oline_filter;
	private ListView gover_online_approval_lv;
	private Context context;
	private String[] str = { "事项：伪造，编造，买卖计划生育证明", "事项：伪造，编造，买卖计划生育证明",
			"事项：伪造，编造，买卖计划生育证明" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gover_onlineapproval, null);
		context = getActivity();
		initUI();
		return view;
	}

	private void initUI() {
		gover_oline_filter = (Spinner) view
				.findViewById(R.id.gover_oline_filter);
		gover_online_approval_lv = (ListView) view
				.findViewById(R.id.gover_online_approval_lv);
		gover_online_approval_lv.setAdapter(new GoverOnlineApproveAdapter(str,
				context));
	}

}

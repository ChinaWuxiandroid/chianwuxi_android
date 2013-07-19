package com.wuxi.app.fragment.homepage.goversaloon;

import android.widget.ListView;
import android.widget.Spinner;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverOnlineApproveAdapter;

/**
 * 
 * @author wanglu 泰得利通 网上审批
 * 
 */
public class OnlineApprovalFragment extends GoverSaloonContentFragment {

	
	private Spinner gover_oline_filter;
	private ListView gover_online_approval_lv;
	
	private String[] str = { "事项：伪造，编造，买卖计划生育证明", "事项：伪造，编造，买卖计划生育证明",
			"事项：伪造，编造，买卖计划生育证明" };

	

	public void initUI() {
		super.initUI();
		gover_oline_filter = (Spinner) view
				.findViewById(R.id.gover_oline_filter);
		gover_online_approval_lv = (ListView) view
				.findViewById(R.id.gover_online_approval_lv);
		gover_online_approval_lv.setAdapter(new GoverOnlineApproveAdapter(str,
				context));
	}

	@Override
	protected int getLayoutId() {
		
		return R.layout.gover_onlineapproval;
	}

}

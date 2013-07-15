package com.wuxi.app.fragment.homepage.goversaloon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.EfficacyComplaintAdapter;

/**
 * 
 * @author wanglu 泰得利通 效能投诉Fragment
 * 
 */
public class EfficacyComplaintFragment extends BaseFragment {

	private View view;
	private ImageView gover_eff_btn_mail_search;
	private ImageView gover_eff_btn_writemail;
	private ListView gover_eff_lv;
	private Context context;
	private String[] itemStr = new String[] { "清西路的高烟囱何时拆掉？", "清西路的高烟囱何时拆掉？",
			"清西路的高烟囱何时拆掉？" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gover_efficacycomplaint_layout, null);
		context=getActivity();
		initUI();
		return view;
	}

	private void initUI() {
		gover_eff_btn_mail_search = (ImageView) view
				.findViewById(R.id.gover_eff_btn_mail_search);
		gover_eff_btn_writemail = (ImageView) view
				.findViewById(R.id.gover_eff_btn_writemail);
		gover_eff_lv = (ListView) view.findViewById(R.id.gover_eff_lv);

		EfficacyComplaintAdapter efficacyComplaintAdapter = new EfficacyComplaintAdapter(
				itemStr, context);
		gover_eff_lv.setAdapter(efficacyComplaintAdapter);

	}

}

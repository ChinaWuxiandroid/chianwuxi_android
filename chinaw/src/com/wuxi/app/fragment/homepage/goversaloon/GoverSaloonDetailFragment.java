package com.wuxi.app.fragment.homepage.goversaloon;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;

/**
 * '
 * 
 * @author wanglu 泰得利通 办件详情 fragment
 * 
 */
public class GoverSaloonDetailFragment extends BaseItemContentFragment
		implements OnClickListener {

	private TextView tv_ssmc_name;
	private TableLayout tl_tb_detail;

	@Override
	public void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		tv_ssmc_name = (TextView) view.findViewById(R.id.tv_ssmc_name);
		tl_tb_detail = (TableLayout) view.findViewById(R.id.tl_tb_detail);
		tv_ssmc_name.setOnClickListener(this);
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.goversaloon_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "行政许可";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ssmc_name:
			if(tl_tb_detail.getVisibility()==TableLayout.VISIBLE){
				tl_tb_detail.setVisibility(TableLayout.GONE);
				//tv_ssmc_name.setCompoundDrawables(left, top, right, bottom)
				//drawableRight
			}else if(tl_tb_detail.getVisibility()==TableLayout.GONE){
				tl_tb_detail.setVisibility(TableLayout.VISIBLE);
			}
			
			
			
			
			break;

		}

	}
}

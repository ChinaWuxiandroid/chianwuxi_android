package com.wuxi.app.fragment.homepage.goversaloon;

import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemQTDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 其他办件详情 fragment
 * 
 */
public class GoverSaloonDetailQTFragment extends BaseItemContentFragment
		implements OnClickListener {

	protected static final int LOAD_ITEM_DETIAL_SUCCESS = 0;
	protected static final int LOAD_ITEM_DETIAL_FAIL = 1;
	private TextView tv_ssmc_name;
	private TableLayout tl_tb_detail;
	private ProgressBar pb_detail;
	private TextView tv_ssbm, tv_clss, tv_sljg, tv_jdjg, tv_lldh, tv_sfjbj,
			tv_sffz, tv_xzfwzxbl, tv_sfsf, tv_qtbldd;
	private GoverSaoonItem goverSaoonItem;
	private GoverSaoonItemQTDetail goverSaoonItemDetail;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_ITEM_DETIAL_SUCCESS:
				showItemDetail();
				break;
			case LOAD_ITEM_DETIAL_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		}
	};

	@Override
	public void initUI() {

		super.initUI();
		tv_ssmc_name = (TextView) view.findViewById(R.id.tv_ssmc_name);
		tl_tb_detail = (TableLayout) view.findViewById(R.id.tl_tb_detail);
		pb_detail = (ProgressBar) view.findViewById(R.id.pb_detail);

		tv_ssbm = (TextView) view.findViewById(R.id.tv_ssbm);
		tv_clss=(TextView) view.findViewById(R.id.tv_clss);
		tv_sljg=(TextView) view.findViewById(R.id.tv_sljg);
		tv_jdjg=(TextView) view.findViewById(R.id.tv_jdjg);
		tv_lldh=(TextView) view.findViewById(R.id.tv_lldh);
		tv_sfjbj=(TextView) view.findViewById(R.id.tv_sfjbj);
		tv_sffz=(TextView) view.findViewById(R.id.tv_sffz);
		tv_xzfwzxbl=(TextView) view.findViewById(R.id.tv_xzfwzxbl);
		tv_sfsf=(TextView) view.findViewById(R.id.tv_sfsf);
		tv_qtbldd=(TextView) view.findViewById(R.id.tv_qtbldd);

		goverSaoonItem = (GoverSaoonItem) getArguments().get("goverSaoonItem");

		loadItemDetail();

		tv_ssmc_name.setOnClickListener(this);
	}

	protected void showItemDetail() {
		pb_detail.setVisibility(ProgressBar.INVISIBLE);
		tv_ssmc_name.setText(goverSaoonItemDetail.getName());// 事项名称
		tv_ssbm.setText(goverSaoonItemDetail.getBm());// 事项编码
		tv_clss.setText(goverSaoonItemDetail.getCnsj()+"工作日");// 承诺时间
		tv_sljg.setText(goverSaoonItemDetail.getSqsljg());// 受理机关
		tv_jdjg.setText(goverSaoonItemDetail.getJdjg());// 决定机关
		tv_lldh.setText(goverSaoonItemDetail.getLxdh());// 联系电话
		tv_sfjbj.setText(goverSaoonItemDetail.getSfjbj());// 是否即办理
		tv_sffz.setText(goverSaoonItemDetail.getIsfz());// 是否发证
		tv_xzfwzxbl.setText(goverSaoonItemDetail.getXzfwzxbl());//是否行政服务中心办理
		tv_sfsf.setText(goverSaoonItemDetail.getIssf());// 是否收费
		tv_qtbldd.setText(goverSaoonItemDetail.getQtbldd());// 其它办理地点
		
		

	}

	/**
	 * 
	 * wanglu 泰得利通 加载相信信息
	 */
	private void loadItemDetail() {

		new Thread(

		new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				GoverSaoonItemService goverSaoonItemService = new GoverSaoonItemService(
						context);
				try {
					goverSaoonItemDetail = goverSaoonItemService
							.getGoverItemQTDetailById(goverSaoonItem.getId());

					if (goverSaoonItemDetail != null) {
						msg.what = LOAD_ITEM_DETIAL_SUCCESS;

					} else {
						msg.what = LOAD_ITEM_DETIAL_FAIL;
						msg.obj = "加载失败";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_ITEM_DETIAL_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = LOAD_ITEM_DETIAL_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = LOAD_ITEM_DETIAL_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}
			}
		}

		).start();

	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.goversaloon_qt_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "其它";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ssmc_name:
			if (tl_tb_detail.getVisibility() == TableLayout.VISIBLE) {
				tl_tb_detail.setVisibility(TableLayout.GONE);
				tv_ssmc_name.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getResources().getDrawable(
								R.drawable.gover_item_detail_expa_down), null);
				// drawableRight
			} else if (tl_tb_detail.getVisibility() == TableLayout.GONE) {
				tl_tb_detail.setVisibility(TableLayout.VISIBLE);

				tv_ssmc_name.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getResources().getDrawable(
								R.drawable.gover_item_detail_expa_up), null);
			}

			break;

		}

	}
}

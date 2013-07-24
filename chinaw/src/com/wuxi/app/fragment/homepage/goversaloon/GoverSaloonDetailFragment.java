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
import com.wuxi.domain.GoverSaoonItemDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 办件详情 fragment
 * 
 */
public class GoverSaloonDetailFragment extends BaseItemContentFragment
		implements OnClickListener {

	protected static final int LOAD_ITEM_DETIAL_SUCCESS = 0;
	protected static final int LOAD_ITEM_DETIAL_FAIL = 1;
	private TextView tv_ssmc_name;
	private TableLayout tl_tb_detail;
	private ProgressBar pb_detail;
	private TextView tv_ssbm, tv_clss, tv_sljg, tv_jdjg, tv_jddh, tv_cjwt,
			tv_lldh, tv_sfzabl, tv_sffz, tv_sfsf;
	private GoverSaoonItem goverSaoonItem;
	private GoverSaoonItemDetail goverSaoonItemDetail;
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
		tv_clss = (TextView) view.findViewById(R.id.tv_clss);
		tv_sljg = (TextView) view.findViewById(R.id.tv_sljg);
		tv_jdjg = (TextView) view.findViewById(R.id.tv_jdjg);
		tv_jddh = (TextView) view.findViewById(R.id.tv_jddh);
		tv_cjwt = (TextView) view.findViewById(R.id.tv_cjwt);
		tv_lldh = (TextView) view.findViewById(R.id.tv_lldh);
		tv_sfzabl = (TextView) view.findViewById(R.id.tv_sfzabl);
		tv_sffz = (TextView) view.findViewById(R.id.tv_sffz);
		tv_sfsf = (TextView) view.findViewById(R.id.tv_sfsf);

		goverSaoonItem = (GoverSaoonItem) getArguments().get("goverSaoonItem");

		loadItemDetail();

		tv_ssmc_name.setOnClickListener(this);
	}

	protected void showItemDetail() {
		pb_detail.setVisibility(ProgressBar.INVISIBLE);
		tv_ssmc_name.setText(goverSaoonItemDetail.getName());//事项名称
		tv_ssbm.setText(goverSaoonItemDetail.getItemcode());//事项编码
		tv_clss.setText(goverSaoonItemDetail.getTimelimit()+"个工作日 ");//工作日 
		tv_sljg.setText(goverSaoonItemDetail.getSlbm());//受理机关
		tv_jdjg.setText(goverSaoonItemDetail.getJdbm());//决定机关
		tv_jddh.setText(goverSaoonItemDetail.getSupertel());//监督电话
		tv_cjwt.setText("查看");
		tv_lldh.setText(goverSaoonItemDetail.getLinktel());//联系电话
		tv_sfzabl.setText(goverSaoonItemDetail.getBjtype());//是否再即办理
		tv_sffz.setText(goverSaoonItemDetail.getCert());//是否发证
		tv_sfsf.setText(goverSaoonItemDetail.getCharge());;//收费收费
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
							.getGoverItemDetailById(goverSaoonItem.getType(),
									goverSaoonItem.getId());

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

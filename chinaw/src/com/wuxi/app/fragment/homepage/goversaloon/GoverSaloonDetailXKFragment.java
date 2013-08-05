package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.app.engine.GoverSaoonWorkFlowImageService;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.domain.GoverMaterials;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemXKDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 办件详情 fragment
 * 
 */
public class GoverSaloonDetailXKFragment extends BaseItemContentFragment
		implements OnClickListener, OnCheckedChangeListener {

	protected static final int LOAD_ITEM_DETIAL_SUCCESS = 0;
	protected static final int LOAD_ITEM_DETIAL_FAIL = 1;
	protected static final int LC_LOADERROR = 3;
	protected static final int LC_LOADSCUCESS = 2;

	private TextView tv_ssmc_name;
	private TableLayout tl_tb_detail;
	private ProgressBar pb_detail;
	private TextView tv_ssbm, tv_clss, tv_sljg, tv_jdjg, tv_jddh, tv_cjwt,
			tv_lldh, tv_sfzabl, tv_sffz, tv_sfsf;
	private GoverSaoonItem goverSaoonItem;
	private GoverSaoonItemXKDetail goverSaoonItemDetail;
	
	private RadioGroup rg_detial;
	private TextView tv_content;
	private ImageView iv_lc;
	private Bitmap bitmap;
	private Button btn_bl,btn_zxzx;
	private LinearLayout ll_zxbl,ll_zxnr;//在线办理
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_ITEM_DETIAL_SUCCESS:
				showItemDetail();
				break;
			case LC_LOADSCUCESS:
				showLcImage();
				break;
			case LC_LOADERROR:
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
		ll_zxbl=(LinearLayout) view.findViewById(R.id.ll_zxbl);
		btn_bl=(Button) view.findViewById(R.id.btn_bl);
		btn_bl.setOnClickListener(this);
		btn_zxzx=(Button) view.findViewById(R.id.btn_zxzx);
		btn_zxzx.setOnClickListener(this);
		ll_zxnr=(LinearLayout) view.findViewById(R.id.ll_zxnr);
		rg_detial = (RadioGroup) view.findViewById(R.id.rg_detial);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		iv_lc = (ImageView) view.findViewById(R.id.iv_lc);
		
		rg_detial.setOnCheckedChangeListener(this);
		goverSaoonItem = (GoverSaoonItem) getArguments().get("goverSaoonItem");

		loadItemDetail();

		tv_ssmc_name.setOnClickListener(this);
	}

	protected void showItemDetail() {
		pb_detail.setVisibility(ProgressBar.INVISIBLE);
		tv_ssmc_name.setText(goverSaoonItemDetail.getName());// 事项名称
		tv_ssbm.setText(goverSaoonItemDetail.getItemcode());// 事项编码
		tv_clss.setText(goverSaoonItemDetail.getTimelimit() + "个工作日 ");// 工作日
		tv_sljg.setText(goverSaoonItemDetail.getSlbm());// 受理机关
		tv_jdjg.setText(goverSaoonItemDetail.getJdbm());// 决定机关
		tv_jddh.setText(goverSaoonItemDetail.getSupertel());// 监督电话
		tv_cjwt.setText("查看");
		tv_lldh.setText(goverSaoonItemDetail.getLinktel());// 联系电话
		tv_sfzabl.setText(goverSaoonItemDetail.getBjtype());// 是否再即办理
		tv_sffz.setText(goverSaoonItemDetail.getCert());// 是否发证
		tv_sfsf.setText(goverSaoonItemDetail.getCharge());

		if(goverSaoonItemDetail.isIswssb()){//可以在线办理
			
			btn_bl.setVisibility(Button.VISIBLE);
		}
		
		rg_detial.check(R.id.rb_sszt);

	}

	private void showLcImage() {

		iv_lc.setImageBitmap(bitmap);
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
							.getGoverItemXKDetailById(goverSaoonItem.getId());

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
		return R.layout.goversaloon_xk_detial_layout;
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
				
			} else if (tl_tb_detail.getVisibility() == TableLayout.GONE) {
				tl_tb_detail.setVisibility(TableLayout.VISIBLE);

				tv_ssmc_name.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getResources().getDrawable(
								R.drawable.gover_item_detail_expa_up), null);
			}

			break;
		case R.id.btn_bl://在线办理
			if(ll_zxbl.getVisibility()==LinearLayout.GONE){
				ll_zxbl.setVisibility(LinearLayout.VISIBLE);
				ll_zxnr.setVisibility(LinearLayout.GONE);
			}else if(ll_zxbl.getVisibility()==LinearLayout.VISIBLE){
				ll_zxbl.setVisibility(LinearLayout.GONE);
			}
			break;
		case R.id.btn_zxzx://在线咨询
			if(ll_zxnr.getVisibility()==LinearLayout.GONE){
				ll_zxnr.setVisibility(LinearLayout.VISIBLE);
				ll_zxbl.setVisibility(LinearLayout.GONE);
			}else if(ll_zxnr.getVisibility()==LinearLayout.VISIBLE){
				ll_zxnr.setVisibility(LinearLayout.GONE);
			}
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (goverSaoonItemDetail == null) {
			return;
		}
		switch (checkedId) {
		case R.id.rb_sszt:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText("实施主体名称:" + goverSaoonItemDetail.getSszt()
					+ "\r\n实施主体编码" + goverSaoonItemDetail.getSsztbm() + "\r\n"
					+ "实施主体性质:" + goverSaoonItemDetail.getSsztxz()
					+ "\r\n委托机关:" + goverSaoonItemDetail.getWtjg());

			break;
		case R.id.rb_sqcl:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			if (goverSaoonItemDetail.getGoverMaterials() != null
					&& goverSaoonItemDetail.getGoverMaterials().size() > 0) {

				List<GoverMaterials> materials = goverSaoonItemDetail
						.getGoverMaterials();
				StringBuffer sb = new StringBuffer();
				for (int index = 0; index < materials.size(); index++) {

					sb.append(materials.get(index).getName()).append("\r\n");

				}

				tv_content.setText(sb.toString());

			}

			break;
		case R.id.rb_sfbz:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getSfbz());
			break;
		case R.id.rb_flfg:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getFlfg());
			break;
		case R.id.rb_sltj:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getSltj());
			break;
		case R.id.rb_fwzn:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getFwzn());
			break;
		case R.id.rb_gslc:
			iv_lc.setVisibility(ImageView.VISIBLE);
			tv_content.setVisibility(TextView.GONE);
			if(this.bitmap!=null){
				showLcImage();
			}else{
				if(goverSaoonItemDetail.getBslc()!=null){
					loadLcImag();
				}
			}
			
			
			break;

		}

		for (int i = 0; i < group.getChildCount(); i++) {

			RadioButton rb = (RadioButton) group.getChildAt(i);
			if (rb.isChecked()) {
				rb.setTextColor(Color.WHITE);
			} else {
				rb.setTextColor(Color.BLACK);
			}
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 加载流程图片
	 */
	private void loadLcImag() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				GoverSaoonWorkFlowImageService goverSaoonWorkFlowImageService = new GoverSaoonWorkFlowImageService(
						context);
				try {
					bitmap = goverSaoonWorkFlowImageService
							.getBitMap(goverSaoonItemDetail.getBslc());
					if (bitmap != null) {
						msg.what = LC_LOADSCUCESS;

					} else {
						msg.what = LC_LOADERROR;
						msg.obj = "获取流程图片失败";
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {

					e.printStackTrace();
					msg.what = LC_LOADERROR;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}
}

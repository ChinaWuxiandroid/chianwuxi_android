package com.wuxi.app.fragment.homepage.goversaloon;

import org.json.JSONException;

import android.annotation.SuppressLint;
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
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemZSDetail;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 行政征收办件详情 fragment
 * 
 */
public class GoverSaloonDetailZSFragment extends BaseItemContentFragment
		implements OnClickListener, OnCheckedChangeListener {

	protected static final int LOAD_ITEM_DETIAL_SUCCESS = 0;
	protected static final int LOAD_ITEM_DETIAL_FAIL = 1;
	protected static final int LC_LOADSCUCESS = 2;
	protected static final int LC_LOADERROR = 3;
	private TextView tv_ssmc_name;
	private TableLayout tl_tb_detail;
	private ProgressBar pb_detail;
	private TextView tv_ssbm, tv_bgdz, tv_jddh;
	private GoverSaoonItem goverSaoonItem;
	private GoverSaoonItemZSDetail goverSaoonItemDetail;
	private RadioGroup rg_deatil;
	private TextView tv_content;
	private ImageView iv_zslc;
	private Bitmap bitmap;
	private Button btn_zxzx;
	private LinearLayout ll_zxnr;// 在线办理
	@SuppressLint("HandlerLeak")
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
		tv_bgdz = (TextView) view.findViewById(R.id.tv_bgdz);
		tv_jddh = (TextView) view.findViewById(R.id.tv_jddh);
		rg_deatil = (RadioGroup) view.findViewById(R.id.rg_deatil);
		rg_deatil.setOnCheckedChangeListener(this);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		iv_zslc = (ImageView) view.findViewById(R.id.iv_zslc);

		goverSaoonItem = (GoverSaoonItem) getArguments().get("goverSaoonItem");

		loadItemDetail();

		tv_ssmc_name.setOnClickListener(this);
		
		
		btn_zxzx = (Button) view.findViewById(R.id.btn_zxzx);
		btn_zxzx.setOnClickListener(this);
		ll_zxnr = (LinearLayout) view.findViewById(R.id.ll_zxnr);
	}

	protected void showItemDetail() {
		pb_detail.setVisibility(ProgressBar.INVISIBLE);
		tv_ssmc_name.setText(goverSaoonItemDetail.getName());// 事项名称
		tv_ssbm.setText(goverSaoonItemDetail.getBm());// 事项编码
		tv_bgdz.setText(goverSaoonItemDetail.getBgdd());
		tv_jddh.setText(goverSaoonItemDetail.getJddh());// 监督电话
		rg_deatil.check(R.id.rb_sszt);
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
							.getGoverSaoonItemZSDetailByid(goverSaoonItem
									.getId());

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
		return R.layout.goversaloon_zs_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "行政征收";
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
		case R.id.btn_zxzx:// 在线咨询
			if (ll_zxnr.getVisibility() == LinearLayout.GONE) {
				ll_zxnr.setVisibility(LinearLayout.VISIBLE);

			} else if (ll_zxnr.getVisibility() == LinearLayout.VISIBLE) {
				ll_zxnr.setVisibility(LinearLayout.GONE);
			}

		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (goverSaoonItemDetail == null) {
			return;
		}
		switch (checkedId) {
		case R.id.rb_sszt:
			iv_zslc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText("实施主体名称:" + goverSaoonItemDetail.getSszt()
					+ "\r\n实施主体编码" + goverSaoonItemDetail.getSsztbm() + "\r\n"
					+ "实施主体性质:" + goverSaoonItemDetail.getSsztxz()
					+ "\r\n委托机关:" + goverSaoonItemDetail.getWtjg());

			break;

		case R.id.rb_flfg:
			iv_zslc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getFlfg());
			break;

		case R.id.rb_zslc:
			iv_zslc.setVisibility(ImageView.VISIBLE);
			tv_content.setVisibility(TextView.GONE);
			if (this.bitmap != null) {
				showLcImage();
			} else {
				if (goverSaoonItemDetail.getZscx() != null) {
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

	private void loadLcImag() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				GoverSaoonWorkFlowImageService goverSaoonWorkFlowImageService = new GoverSaoonWorkFlowImageService(
						context);
				try {
					bitmap = goverSaoonWorkFlowImageService
							.getBitMap(goverSaoonItemDetail.getZscx());
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

	private void showLcImage() {
		iv_zslc.setImageBitmap(bitmap);
	}
}

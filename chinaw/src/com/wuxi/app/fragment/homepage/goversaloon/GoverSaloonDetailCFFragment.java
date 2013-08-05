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
import com.wuxi.domain.GoverSaoonCFCL;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemCFDetail;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 行政处罚办件详情 fragment
 * 
 */
@SuppressLint("HandlerLeak")
public class GoverSaloonDetailCFFragment extends BaseItemContentFragment
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
	private GoverSaoonItemCFDetail goverSaoonItemDetail;
	private Bitmap bitmap;
	private RadioGroup rb_detail;
	private TextView tv_content;
	private ImageView iv_lc;
	private Button btn_zxzx;
	private LinearLayout ll_zxnr;// 在线办理
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
		rb_detail = (RadioGroup) view.findViewById(R.id.rb_detail);
		iv_lc = (ImageView) view.findViewById(R.id.iv_lc);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		rb_detail.setOnCheckedChangeListener(this);
		goverSaoonItem = (GoverSaoonItem) getArguments().get("goverSaoonItem");
		btn_zxzx = (Button) view.findViewById(R.id.btn_zxzx);
		btn_zxzx.setOnClickListener(this);
		ll_zxnr = (LinearLayout) view.findViewById(R.id.ll_zxnr);
		loadItemDetail();

		tv_ssmc_name.setOnClickListener(this);
	}

	protected void showItemDetail() {
		pb_detail.setVisibility(ProgressBar.INVISIBLE);
		tv_ssmc_name.setText(goverSaoonItemDetail.getName());// 事项名称
		tv_ssbm.setText(goverSaoonItemDetail.getItemcode());// 事项编码
		tv_bgdz.setText(goverSaoonItemDetail.getAddress());
		tv_jddh.setText(goverSaoonItemDetail.getSupertel());// 监督电话

		rb_detail.check(R.id.rb_sszt);
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
							.getGoverSaoonItemCFDetailByid(goverSaoonItem
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
		return R.layout.goversaloon_cf_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "行政处罚";
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
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText("实施主体名称:" + goverSaoonItemDetail.getSszt()
					+ "\r\n实施主体编码" + goverSaoonItemDetail.getSsztbm() + "\r\n"
					+ "实施主体性质:" + goverSaoonItemDetail.getSsztxz()
					+ "\r\n委托机关:" + goverSaoonItemDetail.getWtjg());

			break;
		case R.id.rb_cfyj:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getCfyj());
			break;
		case R.id.rb_cfbz:
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);
			tv_content.setText(goverSaoonItemDetail.getCfbz());
			break;

		case R.id.rb_zslc:
			iv_lc.setVisibility(ImageView.VISIBLE);
			tv_content.setVisibility(TextView.GONE);
			if (this.bitmap != null) {
				showLcImage();
			} else {
				if (goverSaoonItemDetail.getLcfileid() != null) {
					loadLcImag();
				}
			}

			break;
		case R.id.rb_fwcl:// 处罚裁量
			iv_lc.setVisibility(ImageView.GONE);
			tv_content.setVisibility(TextView.VISIBLE);

			if (goverSaoonItemDetail.getGoverSaoonCFCLs() != null
					&& goverSaoonItemDetail.getGoverSaoonCFCLs().size() > 0) {

				StringBuffer sb = new StringBuffer();

				for (GoverSaoonCFCL goverSaoonCFCL : goverSaoonItemDetail
						.getGoverSaoonCFCLs()) {
					sb.append(
							goverSaoonCFCL.getStatue() + " :"
									+ goverSaoonCFCL.getResult())
							.append("\r\n");
				}

				tv_content.setText(sb.toString());
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

	private void showLcImage() {
		iv_lc.setImageBitmap(bitmap);
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
							.getBitMap(goverSaoonItemDetail.getLcfileid());
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

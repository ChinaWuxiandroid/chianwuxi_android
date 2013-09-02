package com.wuxi.app.activity.homepage.goversaloon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.app.engine.GoverApplyOnlieService;
import com.wuxi.app.engine.GoverSaoonItemService;
import com.wuxi.app.engine.GoverSaoonWorkFlowImageService;
import com.wuxi.app.engine.GoversaoonOnlineASKDetailService;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.domain.GoverApplyOnlie;
import com.wuxi.domain.GoverMaterials;
import com.wuxi.domain.GoverSaoonItem;
import com.wuxi.domain.GoverSaoonItemQTDetail;
import com.wuxi.domain.GoversaoonOnlineASKDetail;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * '
 * 
 * @author wanglu 泰得利通 其他办件详情 fragment
 * 
 */
public class GoverSaloonDetailQTActivity extends BaseItemContentActivity
		implements OnClickListener, OnCheckedChangeListener {

	protected static final int LOAD_ITEM_DETIAL_SUCCESS = 0;

	protected static final int LOAD_ITEM_DETIAL_FAIL = 1;

	protected static final int LC_LOADSCUCESS = 2;

	protected static final int LC_LOADERROR = 3;

	protected static final int COMMIT_SUCCESS = 4;

	protected static final int COMMIT_ERROR = 5;

	protected static final int COMMIT_APPLY_SUCCESS = 6;

	protected static final int COMMIT_APPLY_ERROR = 7;

	private TextView tv_ssmc_name;

	private TableLayout tl_tb_detail;

	private ProgressBar pb_detail;

	private TextView tv_ssbm, tv_clss, tv_sljg, tv_jdjg, tv_lldh, tv_sfjbj,
			tv_sffz, tv_xzfwzxbl, tv_sfsf, tv_qtbldd;

	private GoverSaoonItem goverSaoonItem;

	private GoverSaoonItemQTDetail goverSaoonItemDetail;

	private RadioGroup rg_detail;

	private ImageView iv_lc;

	private TextView tv_content;

	private Bitmap bitmap;

	private Button btn_bl, btn_zxzx;

	private LinearLayout ll_zxbl, ll_zxnr;// 在线办理

	private TextView tv_item_name;

	private EditText et_content;

	private Button btn_ask_submit, btn_ask_reset;

	private Spinner sp_sqtype;

	private EditText et_dwdm, et_dz, et_lxr, et_dh, et_sj, et_zjmc, et_zjhm,
			et_jssy;

	private Button apply_btn_submit, apply_btn_reset;

	private String[] applyType = new String[] { "个人", "企业法人" };

	private LoginDialog loginDialog;

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
			case COMMIT_APPLY_SUCCESS:
			case COMMIT_SUCCESS:
				pb_detail.setVisibility(ProgressBar.GONE);
				Toast.makeText(
					GoverSaloonDetailQTActivity.this, "提交成功",
					Toast.LENGTH_SHORT).show();
				break;

			case COMMIT_APPLY_ERROR:
			case COMMIT_ERROR:

			case LC_LOADERROR:
			case LOAD_ITEM_DETIAL_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(
					GoverSaloonDetailQTActivity.this, tip, Toast.LENGTH_SHORT)
						.show();
				break;
			}

		}

	};

	@Override
	protected void findMainContentViews(View view) {

		super.findMainContentViews(view);

		loginDialog = new LoginDialog(this);
		tv_ssmc_name = (TextView) view.findViewById(R.id.tv_ssmc_name);
		tl_tb_detail = (TableLayout) view.findViewById(R.id.tl_tb_detail);
		pb_detail = (ProgressBar) view.findViewById(R.id.pb_detail);

		tv_ssbm = (TextView) view.findViewById(R.id.tv_ssbm);
		tv_clss = (TextView) view.findViewById(R.id.tv_clss);
		tv_sljg = (TextView) view.findViewById(R.id.tv_sljg);
		tv_jdjg = (TextView) view.findViewById(R.id.tv_jdjg);
		tv_lldh = (TextView) view.findViewById(R.id.tv_lldh);
		tv_sfjbj = (TextView) view.findViewById(R.id.tv_sfjbj);
		tv_sffz = (TextView) view.findViewById(R.id.tv_sffz);
		tv_xzfwzxbl = (TextView) view.findViewById(R.id.tv_xzfwzxbl);
		tv_sfsf = (TextView) view.findViewById(R.id.tv_sfsf);
		tv_qtbldd = (TextView) view.findViewById(R.id.tv_qtbldd);
		rg_detail = (RadioGroup) view.findViewById(R.id.rg_detail);
		rg_detail.setOnCheckedChangeListener(this);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		iv_lc = (ImageView) view.findViewById(R.id.iv_lc);

		goverSaoonItem = (GoverSaoonItem) getIntent().getExtras().get(
			"goverSaoonItem");

		loadItemDetail();

		tv_ssmc_name.setOnClickListener(this);

		ll_zxbl = (LinearLayout) view.findViewById(R.id.ll_zxbl);
		btn_bl = (Button) view.findViewById(R.id.btn_bl);
		btn_bl.setOnClickListener(this);
		btn_zxzx = (Button) view.findViewById(R.id.btn_zxzx);
		btn_zxzx.setOnClickListener(this);
		ll_zxnr = (LinearLayout) view.findViewById(R.id.ll_zxnr);

		tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
		et_content = (EditText) view.findViewById(R.id.et_content);
		btn_ask_submit = (Button) view.findViewById(R.id.btn_ask_submit);
		btn_ask_reset = (Button) view.findViewById(R.id.btn_ask_reset);
		btn_ask_submit.setOnClickListener(this);
		btn_ask_reset.setOnClickListener(this);

		sp_sqtype = (Spinner) view.findViewById(R.id.sp_sqtype);
		et_dwdm = (EditText) view.findViewById(R.id.et_dwdm);
		et_dz = (EditText) view.findViewById(R.id.et_dz);
		et_lxr = (EditText) view.findViewById(R.id.et_lxr);
		et_dh = (EditText) view.findViewById(R.id.et_dh);
		et_sj = (EditText) view.findViewById(R.id.et_sj);
		et_zjmc = (EditText) view.findViewById(R.id.et_zjmc);
		et_zjhm = (EditText) view.findViewById(R.id.et_zjhm);
		et_jssy = (EditText) view.findViewById(R.id.et_jssy);

		apply_btn_submit = (Button) view.findViewById(R.id.apply_btn_submit);

		apply_btn_reset = (Button) view.findViewById(R.id.apply_btn_reset);

		apply_btn_submit.setOnClickListener(this);
		apply_btn_reset.setOnClickListener(this);

		sp_sqtype.setAdapter(new ArrayAdapter<String>(this,
			R.layout.my_simple_spinner_item_layout, applyType));
	}

	private void showLcImage() {
		iv_lc.setImageBitmap(bitmap);

	}

	protected void showItemDetail() {
		pb_detail.setVisibility(ProgressBar.INVISIBLE);
		tv_ssmc_name.setText(goverSaoonItemDetail.getName());// 事项名称
		tv_ssbm.setText(goverSaoonItemDetail.getBm());// 事项编码
		tv_clss.setText(goverSaoonItemDetail.getCnsj() + "工作日");// 承诺时间
		tv_sljg.setText(goverSaoonItemDetail.getSqsljg());// 受理机关
		tv_jdjg.setText(goverSaoonItemDetail.getJdjg());// 决定机关
		tv_lldh.setText(goverSaoonItemDetail.getLxdh());// 联系电话
		tv_sfjbj.setText(goverSaoonItemDetail.getSfjbj());// 是否即办理
		tv_sffz.setText(goverSaoonItemDetail.getIsfz());// 是否发证
		tv_xzfwzxbl.setText(goverSaoonItemDetail.getXzfwzxbl());// 是否行政服务中心办理
		tv_sfsf.setText(goverSaoonItemDetail.getIssf());// 是否收费
		tv_qtbldd.setText(goverSaoonItemDetail.getQtbldd());// 其它办理地点
		rg_detail.check(R.id.rb_sszt);

		if (goverSaoonItemDetail.getIswssb().equals("1")) {// 在线办理
			btn_bl.setVisibility(Button.VISIBLE);
		}
		tv_item_name.setText(goverSaoonItemDetail.getName());

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
					GoverSaloonDetailQTActivity.this);
				try {
					goverSaoonItemDetail = goverSaoonItemService.getGoverItemQTDetailById(goverSaoonItem.getId());

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

		case R.id.btn_bl:// 在线办理
			if (ll_zxbl.getVisibility() == LinearLayout.GONE) {
				if (loginDialog.checkLogin()) {
					ll_zxbl.setVisibility(LinearLayout.VISIBLE);
					ll_zxnr.setVisibility(LinearLayout.GONE);
				} else {
					loginDialog.showDialog();// 显示
				}

			} else if (ll_zxbl.getVisibility() == LinearLayout.VISIBLE) {
				ll_zxbl.setVisibility(LinearLayout.GONE);
			}
			break;
		case R.id.btn_zxzx:// 在线咨询
			if (ll_zxnr.getVisibility() == LinearLayout.GONE) {
				if (loginDialog.checkLogin()) {
					ll_zxnr.setVisibility(LinearLayout.VISIBLE);
					ll_zxbl.setVisibility(LinearLayout.GONE);
				} else {
					loginDialog.showDialog();
				}

			} else if (ll_zxnr.getVisibility() == LinearLayout.VISIBLE) {
				ll_zxnr.setVisibility(LinearLayout.GONE);
			}
			break;
		case R.id.btn_ask_submit:// 在线咨询提交
			if (et_content.getText().toString().equals("")) {
				Toast.makeText(
					GoverSaloonDetailQTActivity.this, "请输入您要提交的内容",
					Toast.LENGTH_SHORT).show();
				return;
			}
			commitAsk();
			break;
		case R.id.btn_ask_reset:// 在线提交重置
			et_content.setText("");
			break;
		case R.id.apply_btn_submit:
			if (validateApplyOnlieForm()) {
				commitApply();// 提交在线办理信息
			}
			break;
		case R.id.apply_btn_reset:
			applyonleReSet();
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

				List<GoverMaterials> materials = goverSaoonItemDetail.getGoverMaterials();
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
			if (this.bitmap != null) {
				showLcImage();
			} else {
				if (goverSaoonItemDetail.getBslc() != null) {
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
					GoverSaloonDetailQTActivity.this);
				try {
					bitmap = goverSaoonWorkFlowImageService.getBitMap(goverSaoonItemDetail.getBslc());
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

	private void commitAsk() {
		if (goverSaoonItemDetail == null) {
			Toast.makeText(
				GoverSaloonDetailQTActivity.this, "没有咨询信息，提交失败",
				Toast.LENGTH_SHORT).show();
			return;
		}

		pb_detail.setVisibility(ProgressBar.VISIBLE);

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				GoversaoonOnlineASKDetailService goversaoonOnlineASKDetailService = new GoversaoonOnlineASKDetailService(
					GoverSaloonDetailQTActivity.this);
				try {
					GoversaoonOnlineASKDetail goversaoonOnlineDetail = goversaoonOnlineASKDetailService.commitGoversaoonOnlineASKDetail(
						goverSaoonItemDetail.getId(),
						"XK",
						et_content.getText().toString(),
						SystemUtil.getAccessToken(GoverSaloonDetailQTActivity.this));
					if (goversaoonOnlineDetail != null) {
						msg.what = COMMIT_SUCCESS;
					} else {
						msg.what = COMMIT_ERROR;
						msg.obj = "提交失败，稍后再试";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = COMMIT_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);

				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = COMMIT_ERROR;
					msg.obj = Constants.ExceptionMessage.DATA_FORMATE;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	public void applyonleReSet() {
		et_dwdm.setText("");
		et_dz.setText("");
		et_lxr.setText("");
		et_dh.setText("");
		et_sj.setText("");
		et_zjmc.setText("");
		et_zjhm.setText("");
		et_jssy.setText("");
	}

	/**
	 * 
	 * wanglu 泰得利通 表单验证
	 * 
	 * @return
	 */
	public boolean validateApplyOnlieForm() {
		if (et_dwdm.getText().toString().equals("")) {
			Toast.makeText(this, "请输入单位代码", Toast.LENGTH_SHORT).show();
			et_dwdm.requestFocus();
			return false;
		} else if (et_dz.getText().toString().equals("")) {
			Toast.makeText(this, "请输地址", Toast.LENGTH_SHORT).show();
			et_dz.requestFocus();
			return false;
		} else if (et_lxr.getText().toString().equals("")) {
			Toast.makeText(this, "请输入联系人", Toast.LENGTH_SHORT).show();
			et_lxr.requestFocus();
			return false;
		} else if (et_dh.getText().toString().equals("")) {
			Toast.makeText(this, "请输电话", Toast.LENGTH_SHORT).show();
			et_dh.requestFocus();
			return false;
		} else if (et_sj.getText().toString().equals("")) {
			Toast.makeText(this, "请输手机", Toast.LENGTH_SHORT).show();
			et_sj.requestFocus();
			return false;
		} else if (et_zjmc.getText().toString().equals("")) {
			Toast.makeText(this, "请输入证件名称", Toast.LENGTH_SHORT).show();
			et_zjmc.requestFocus();
			return false;
		} else if (et_zjhm.getText().toString().equals("")) {
			Toast.makeText(this, "请输入证件号码", Toast.LENGTH_SHORT).show();
			et_zjhm.requestFocus();
			return false;
		} else if (et_jssy.getText().toString().equals("")) {
			Toast.makeText(this, "请输入申请事由", Toast.LENGTH_SHORT).show();
			et_jssy.requestFocus();
			return false;
		}

		return true;

	}

	/**
	 * 
	 * wanglu 泰得利通 在线办理提交参数
	 * 
	 * @return
	 */
	private Map<String, String> getParams() {
		Map<String, String> params = new HashMap<String, String>();

		params.put("id", UUID.randomUUID().toString());
		params.put("sqzlx", sp_sqtype.getSelectedItemPosition() + "");// 申请者类型
		params.put("sqzmc", "test");// 名称
		params.put("dwdm", et_dwdm.getText().toString());// 单位代码
		params.put("dz", et_dz.getText().toString());
		params.put("lxr", et_lxr.getText().toString());
		params.put("dh", et_dh.getText().toString());
		params.put("sj", et_sj.getText().toString());
		params.put("zjmc", et_zjmc.getText().toString());
		params.put("zjhm", et_zjhm.getText().toString());
		params.put("sqsy", et_jssy.getText().toString());
		params.put("itemid", goverSaoonItemDetail.getId());
		params.put("itemtype", "XK");
		params.put("access_token", SystemUtil.getAccessToken(this));

		return params;
	}

	private void commitApply() {
		if (goverSaoonItemDetail == null) {
			Toast.makeText(this, "没有咨询信息，提交失败", Toast.LENGTH_SHORT).show();
			return;
		}

		pb_detail.setVisibility(ProgressBar.VISIBLE);

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				GoverApplyOnlieService goApplyOnlieService = new GoverApplyOnlieService(
					GoverSaloonDetailQTActivity.this);
				try {

					GoverApplyOnlie apply = goApplyOnlieService.commitOnlieForm(getParams());
					if (apply != null) {
						msg.what = COMMIT_APPLY_SUCCESS;
					} else {
						msg.what = COMMIT_APPLY_ERROR;
						msg.obj = "提交失败，稍后再试";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = COMMIT_ERROR;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);

				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = COMMIT_ERROR;
					msg.obj = Constants.ExceptionMessage.DATA_FORMATE;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

}

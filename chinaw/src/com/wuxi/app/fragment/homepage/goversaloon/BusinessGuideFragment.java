package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.KindTypeAdapter;
import com.wuxi.app.engine.KindTypeService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Kindtype;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 办事指南
 * 
 *         上级分类编号 01：个人身份 02：个人办事 03：企业行业 04：企业办事 05：港澳台侨、外国人 06：主题服务
 */
public class BusinessGuideFragment extends GoverSaloonContentFragment implements
		OnCheckedChangeListener {

	protected static final int KINDTYPE_LOAD_SUCCESS = 0;
	protected static final int KINDTYPE_LOAD_FAIL = 1;
	private GridView gv;
	private String[] types = new String[] { "01", "02", "03", "04", "05", "06" };
	private List<Kindtype> kindtypes;
	private KindTypeAdapter kindTypeAdapter;
	private boolean isFistLoadKindData = true;
	private RadioGroup gover_buness_guide_rg;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case KINDTYPE_LOAD_SUCCESS:
				showKindType();
				break;
			case KINDTYPE_LOAD_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		}
	};

	@Override
	protected void initUI() {

		super.initUI();

		gv = (GridView) view.findViewById(R.id.gover_guide_gv_kindtype);
		gover_buness_guide_rg = (RadioGroup) view
				.findViewById(R.id.gover_buness_guide_rg);
		gover_buness_guide_rg.setOnCheckedChangeListener(this);
		loadKindTypeData(types[1]);

	}

	/**
	 * 
	 * wanglu 泰得利通 显示kindType数据
	 */
	private void showKindType() {
		if (isFistLoadKindData) {
			isFistLoadKindData = false;
			kindTypeAdapter = new KindTypeAdapter(kindtypes, context);
			gv.setAdapter(kindTypeAdapter);
		} else {
			kindTypeAdapter.setKindTypes(kindtypes);
			kindTypeAdapter.notifyDataSetChanged();
		}

	}

	private void loadKindTypeData(final String type) {

		if (CacheUtil.get("type" + type) != null) {
			kindtypes = (List<Kindtype>) CacheUtil.get("type" + type);
			showKindType();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				KindTypeService kindTypeService = new KindTypeService(context);
				try {
					kindtypes = kindTypeService.getKindtypesByType(type);
					if (kindtypes != null) {
						CacheUtil.put("type" + type, kindtypes);
						msg.what = KINDTYPE_LOAD_SUCCESS;
					} else {
						msg.what = KINDTYPE_LOAD_FAIL;
						msg.obj = "获取数据失败";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();

					msg.what = KINDTYPE_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();

					msg.what = KINDTYPE_LOAD_FAIL;
					msg.obj = "数据格式错误";
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	@Override
	protected int getLayoutId() {
		return R.layout.gover_saloon_business_guide_layout;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int typeIndex = 0;
		switch (checkedId) {

		case R.id.rb_personal:
			typeIndex = 1;
			break;
		case R.id.rb_company:
			typeIndex = 3;
			break;
		case R.id.rb_style_service:
			typeIndex = 5;
			break;
		case R.id.rb_green:
			break;

		}
		loadKindTypeData(types[typeIndex]);
	}
}

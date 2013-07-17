package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Myconsult;
import com.wuxi.domain.MyconsultWrapper;
import com.wuxi.exception.NetException;
import com.wuxi.exception.ResultException;

/**
 * 
 * @author wanglu 泰得利通
 * 
 */
public class TestMyConsultWapperService extends AndroidTestCase {

	private static final String TAG = "TestMyConsultWapperService";

	public void testGetWapper() throws NetException, JSONException,
			ResultException {
		MyconsultService myconsultService = new MyconsultService(getContext());

		MyconsultWrapper wa = myconsultService.getPageMyconsults(
				"bd58fcdfe5b54f4c95ed5f2e3a945f7c", 0, 10);

		List<Myconsult> myconsults = wa.getMyconsults();

		for (Myconsult my : myconsults) {

			LogUtil.i(TAG, my.getId());
		}

	}

}

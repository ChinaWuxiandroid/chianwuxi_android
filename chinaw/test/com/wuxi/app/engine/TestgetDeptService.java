package com.wuxi.app.engine;

import java.util.List;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Dept;
import com.wuxi.exception.NetException;

public class TestgetDeptService extends AndroidTestCase {

	private static final String TAG = "TestgetDeptService";

	public void testGetDetp() throws NetException, JSONException {
		DeptService deptService = new DeptService(getContext());

		List<Dept> depts = deptService.getDepts();

		if (depts != null) {
			for (Dept dept : depts) {
				LogUtil.i(TAG, dept.getName());
			}
		}

	}
}

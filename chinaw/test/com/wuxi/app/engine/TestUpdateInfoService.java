package com.wuxi.app.engine;

import android.test.AndroidTestCase;

import com.wuxi.app.R;
import com.wuxi.domain.UpdateInfo;

public class TestUpdateInfoService extends AndroidTestCase {

	
	public void testUpdate() throws Exception{
		UpdateInfoService updateInfoService=new UpdateInfoService(getContext());
	UpdateInfo updateInfo=	updateInfoService.getUpdateInfo(R.string.updateurl);
	
	updateInfo.getDescription();
		
	}

}

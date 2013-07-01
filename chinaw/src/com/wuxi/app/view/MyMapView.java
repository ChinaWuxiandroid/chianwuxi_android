package com.wuxi.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupOverlay;

/**
 * 
 * @author wanglu 泰得利通
 * 地图
 *
 */
public class MyMapView extends MapView {

public static PopupOverlay   pop  = null;//弹出泡泡图层，浏览节点时使用
	
	public MyMapView(Context context) {
		super(context);
	}
	public MyMapView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MyMapView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event){
		if (!super.onTouchEvent(event)){
			//消隐泡泡
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}

}

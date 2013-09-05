package com.wuxi.app.popwin;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.wuxi.app.R;

/**
 * 
 * @author wanglu 泰得利通 分享弹出漂浮窗popWindow
 * 
 */

public class SharePopWindow implements OnClickListener {

	private Context context;
	private View view;
	private PopupWindow popWindow;
	private LinearLayout ll_sina, ll_tencentweibo, ll_weixin, ll_qqzone,
			ll_renren;

	public SharePopWindow(Context context) {

		this.context = context;

	}

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param parent
	 * @param showX
	 * @param showY
	 *            显示popWindow
	 */
	public void showPopWindow(View parent, int showX, int showY) {

		view = View.inflate(context, R.layout.share_pop_layout, null);
		findViews();

		LinearLayout ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
		popWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		fixPopupWindow(popWindow);
		popWindow.showAsDropDown(parent, showX, showY);

		ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
		sa.setDuration(200);
		ll_share.startAnimation(sa);

	}

	private void findViews() {
		ll_sina = (LinearLayout) view.findViewById(R.id.ll_sina);
		ll_tencentweibo = (LinearLayout) view
				.findViewById(R.id.ll_tencentweibo);
		ll_weixin = (LinearLayout) view.findViewById(R.id.ll_weixin);
		ll_qqzone = (LinearLayout) view.findViewById(R.id.ll_qqzone);
		ll_renren = (LinearLayout) view.findViewById(R.id.ll_renren);

		ll_sina.setOnClickListener(this);
		ll_tencentweibo.setOnClickListener(this);
		ll_weixin.setOnClickListener(this);
		ll_qqzone.setOnClickListener(this);
		ll_renren.setOnClickListener(this);

	}

	public void dissmissPopWindow() {
		if (popWindow != null) {
			
			this.popWindow.dismiss();
			this.popWindow = null;
		}

	}

	@Override
	public void onClick(View v) {
		Toast.makeText(context, "施工中", Toast.LENGTH_SHORT).show();
		switch (v.getId()) {

		case R.id.ll_sina:

			break;
		case R.id.ll_tencentweibo:
			break;
		case R.id.ll_weixin:
			break;
		case R.id.ll_qqzone:
			break;
		case R.id.ll_renren:
			break;

		}

	}
	
	/**
	 * 
	 *wanglu 泰得利通 
	 *解决3.0一下系统的popWindow bug
	 * @param window
	 */
	private void fixPopupWindow(final PopupWindow window) {
	    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	        try {
	            final Field fAnchor = PopupWindow.class
	                    .getDeclaredField("mAnchor");
	            fAnchor.setAccessible(true);
	            Field listener = PopupWindow.class
	                    .getDeclaredField("mOnScrollChangedListener");
	            listener.setAccessible(true);
	            final ViewTreeObserver.OnScrollChangedListener originalListener = (ViewTreeObserver.OnScrollChangedListener) listener
	                    .get(window);
	            ViewTreeObserver.OnScrollChangedListener newListener = new ViewTreeObserver.OnScrollChangedListener() {
	                @Override
	                public void onScrollChanged() {
	                    try {
	                        @SuppressWarnings("unchecked")
							WeakReference<View> mAnchor = (WeakReference<View>) fAnchor.get(window);
	                        if (mAnchor == null || mAnchor.get() == null) {
	                            return;
	                        } else {
	                            originalListener.onScrollChanged();
	                        }
	                    } catch (IllegalAccessException e) {
	                        e.printStackTrace();
	                    }
	                }
	            };
	            listener.set(window, newListener);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

}

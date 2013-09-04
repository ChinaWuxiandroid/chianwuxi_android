package com.wuxi.app.activity.commactivity;

import com.polites.android.GestureImageView;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
 * 
 * @author wanglu 泰得利通 图片预览Activity
 * 
 */
public class ImageViewScaleActivity extends Activity {

	private GestureImageView gestureImageView;
	public static final String BITMAP_KEY = "bitMap";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MainTabActivity.instance.hideOrShowTab();
		setContentView(R.layout.image_scal_layout);
		gestureImageView = (GestureImageView) findViewById(R.id.gg_image);

		Bitmap bitmap = (Bitmap) getIntent().getExtras().get(BITMAP_KEY);

		if (bitmap != null) {
			gestureImageView.setImageBitmap(bitmap);
		}

	}
	
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				MainTabActivity.instance.hideOrShowTab();
				MainTabActivity.instance.pop();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	

}

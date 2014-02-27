package com.wuxi.app;

import java.util.ArrayList;
import java.util.List;

import android.widget.PopupWindow;

/**
 * 
 * @author wanglu 泰得利通 PopWindow 管理
 */
public class PopWindowManager {

	private static PopWindowManager instance;
	private List<PopupWindow> popWindows = new ArrayList<PopupWindow>();

	private PopWindowManager() {

	}

	public synchronized static PopWindowManager getInstance() {

		if (instance == null) {
			instance = new PopWindowManager();
		}

		return instance;
	}

	public PopupWindow getPopupWindow(PopupWindow popupWindow) {

		if (popWindows.indexOf(popupWindow) == -1) {
			return null;
		}

		return popWindows.get(popWindows.indexOf(popupWindow));
	}

	public void addPopWindow(PopupWindow popupWindow) {
		popWindows.add(popupWindow);
	}

	public void dissMissPopWindow(PopupWindow popupWindow) {
		if (popupWindow == null) {
			return;
		}
		popupWindow.dismiss();
		if (popWindows.indexOf(popupWindow) != -1) {
			popWindows.remove(popWindows.indexOf(popupWindow));
		}

	}

	public void removePowWIndows() {

		for (PopupWindow popupWindow : popWindows) {
			if (popupWindow != null) {
				dissMissPopWindow(popupWindow);
			}
		}
	}
}

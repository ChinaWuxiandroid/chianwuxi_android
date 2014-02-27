package com.wuxi.app.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;

import com.wuxi.app.R;


/**
 * 我的政民互动  中RadioButton  的图片风格切换帮助类
 * @author 杨宸 智佳
 * */

public class GIPRadioButtonStyleChange {

	public GIPRadioButtonStyleChange(){}
	
	public GIPRadioButtonStyleChange(int selectedDrawable,
			int noSelectedDrawable, int selectedTextColor,
			int noSelectedTextColor) {
		this.selectedDrawable = selectedDrawable;
		this.noSelectedDrawable = noSelectedDrawable;
		this.selectedTextColor = selectedTextColor;
		this.noSelectedTextColor = noSelectedTextColor;
	}

	private int selectedDrawable=R.drawable.title_item_select_bg;  //默认背景图
	private int noSelectedDrawable=R.drawable.title_item_bg;  //默认背景图
	private int selectedTextColor=Color.WHITE;  //默认背景图
	private int noSelectedTextColor=R.color.gip_button_blue;  //默认背景图

	public void setSelectedDrawable(int selectedDrawable) {
		this.selectedDrawable = selectedDrawable;
	}

	public void setNoSelectedDrawable(int noSelectedDrawable) {
		this.noSelectedDrawable = noSelectedDrawable;
	}

	public void setSelectedTextColor(int selectedTextColor) {
		this.selectedTextColor = selectedTextColor;
	}

	public void setNoSelectedTextColor(int noSelectedTextColor) {
		this.noSelectedTextColor = noSelectedTextColor;
	}

	@SuppressLint("ResourceAsColor")
	public void refreshRadioButtonStyle(View view,int[] radioButtonIds,int checkedId){
		RadioButton rBtn;
		for(int i=0;i<radioButtonIds.length;i++){
			if(checkedId==radioButtonIds[i]){
				rBtn=(RadioButton)view.findViewById
						(radioButtonIds[i]);
				rBtn.setBackgroundResource(selectedDrawable);
				rBtn.setTextColor(selectedTextColor);
			}
			else{
				rBtn=(RadioButton)view.findViewById
						(radioButtonIds[i]);
				if(noSelectedDrawable==0)
					rBtn.setBackgroundResource(0);
				else
					rBtn.setBackgroundResource(noSelectedDrawable);
				rBtn.setTextColor(noSelectedTextColor);
			}
		}
	}




}

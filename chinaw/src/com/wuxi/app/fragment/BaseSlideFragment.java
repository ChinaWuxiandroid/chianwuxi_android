package com.wuxi.app.fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;


public class BaseSlideFragment extends BaseFragment {

	public View view = null;
	public SlideLevelFragment fragment;
	public ImageView OPearn_btn, Member_btnm,back_btn;
	public TextView Title_text;

	public void InitBtn() {  
		OPearn_btn = (ImageView) view.findViewById(R.id.open_close_left_btn);
		Member_btnm = (ImageView) view.findViewById(R.id.member_btn);
		Title_text = (TextView) view.findViewById(R.id.Title_Text);
		back_btn = (ImageView)view.findViewById(R.id.back_btn);
		OPearn_btn.setOnClickListener(LeftClick);
		Member_btnm.setOnClickListener(MemberClick);
		back_btn.setOnClickListener(BackClick);
	}

	private OnClickListener LeftClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			OpenOrCloseMenu(v);
		}
	};
	private OnClickListener MemberClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MemberMenu(v);
		}
	};
	
	private OnClickListener BackClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			
		}
	};
	
	
	

	public void OpenOrCloseMenu(View souce) {
		 fragment.OpearnLeft();
	}

	public void MemberMenu(View souce) {
		 fragment.OpearnRight();
	}
	
	public void setFragmentTitle(String Title){
		Title_text.setText(Title);
	}

	public void setFragment(SlideLevelFragment fragment) {
		this.fragment = fragment;
	}
}

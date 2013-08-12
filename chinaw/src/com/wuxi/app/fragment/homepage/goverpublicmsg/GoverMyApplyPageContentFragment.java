package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MyApplyPageWrapper.MyApplyPage;

/**
 * 政府信息公开   我的依申请公开办件回复  内容页
 * @author 杨宸
 * */

public class GoverMyApplyPageContentFragment extends BaseFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;

	private TextView code_txtView,title_txtView,answerDate_txtView,receiveDate_txtView,ansewerDept_txtView;
	private EditText mailContent_editText,applyResult_editText;
	private Button back_btn;
	
	private MyApplyPage myPage;

	public void setPage(MyApplyPage page){
		this.myPage=page;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.myapplypage_content_layout, null);
		mInflater = inflater;
		context = getActivity();

		initView();
		return view;
	}

	public void initView(){
		code_txtView=(TextView)view.findViewById(R.id.myapplycontent_txtview_code);
		title_txtView=(TextView)view.findViewById(R.id.myapplycontent_txtview_title);
		answerDate_txtView=(TextView)view.findViewById(R.id.myapplycontent_txtview_answerdate);
		receiveDate_txtView=(TextView)view.findViewById(R.id.myapplycontent_txtview_receivedate);
		ansewerDept_txtView=(TextView)view.findViewById(R.id.myapplycontent_txtview_answerdept);

		mailContent_editText=(EditText)view.findViewById(R.id.myapplycontent_edittext_mailcontent);
		applyResult_editText=(EditText)view.findViewById(R.id.myapplycontent_edittext_applyresult);

		back_btn=(Button)view.findViewById(R.id.myapplycontent_btn_back);
		if(myPage!=null){
			code_txtView.setText(String.valueOf(myPage.getCode()));
			title_txtView.setText(String.valueOf(myPage.getTitle()));

			mailContent_editText.setText(String.valueOf(myPage.getContent()));

			receiveDate_txtView.setText(String.valueOf(myPage.getApplyDate()));
			answerDate_txtView.setText(String.valueOf(myPage.getAnswerDate()));
			ansewerDept_txtView.setText(String.valueOf(myPage.getAnswerDep()));

			applyResult_editText.setText(String.valueOf(myPage.getAnswerContent()));
		}
		
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onRemove();
			}
		});
		
	}
	
	public void onRemove(){
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.remove(this);
		ft.commit();
	}
}

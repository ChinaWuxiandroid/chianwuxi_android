/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;

/**
 * 公众论坛发表帖子碎片布局
 * 
 * @author 智佳 罗森
 *
 */
public class ForumPostFragment extends BaseItemContentFragment {

	/* (non-Javadoc)
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentLayoutId()
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.forum_post_layout;
	}

	/* (non-Javadoc)
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentTitleText()
	 */
	@Override
	protected String getContentTitleText() {
		return "公众论坛";
	}

}

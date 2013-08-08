/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;

/**
 * 公众论坛帖子详细内容碎片
 * 
 * @author 智佳 罗森
 *
 */
public class ForumContentFragment extends BaseItemContentFragment {

	/* (non-Javadoc)
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentLayoutId()
	 */
	@Override
	protected int getContentLayoutId() {
		return R.layout.forum_content_layout;
	}

	/* (non-Javadoc)
	 * @see com.wuxi.app.fragment.BaseItemContentFragment#getContentTitleText()
	 */
	@Override
	protected String getContentTitleText() {
		return "公众论坛";
	}

}

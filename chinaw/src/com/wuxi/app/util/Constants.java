package com.wuxi.app.util;

import android.os.Environment;

/**
 * 常量类
 * 
 * @author wanglu
 * 
 */
public class Constants {

	/**
	 * API地址
	 * 
	 * @author wanglu
	 * 
	 */
	public static final class Urls {
		/**
		 * 菜单获取URL
		 * 
		 * recursions false Int 返回层次,不输入则为2,为0则返回所有
		 */
		public static final String MENU_URL = "http://3g.wuxi.gov.cn/api/menu/tree.json";

		/**
		 * 获取子菜单接口 {id}要获取子菜单的Id
		 */
		public static final String SUB_MENU_URL = "http://3g.wuxi.gov.cn/api/menu/{id}/menus.json";

		/**
		 * 获取子频道URL {channelId}频道ID占位符
		 */
		public static final String CHANNEL_URL = "http://3g.wuxi.gov.cn/api/channel/{channelId}/channels.json";

		/**
		 * 获取频道内容信息 {id}频道Id占位符
		 */

		public static final String CHANNEL_CONTENT_URL = "http://3g.wuxi.gov.cn/api/channel/{id}/contents.json";

		/**
		 * 获取频道内容分页接口 {id}频道占位符 {start}开始位置占位符 {end}结束位置占位符
		 */
		public static final String CHANNEL_CONTENT_P_URL = "http://3g.wuxi.gov.cn/api/channel/{id}/contents_p.json?start={start}&end={end}";

		/**
		 * 推荐公告URL
		 */

		public static final String ANNOUNCENTS_URL = "http://3g.wuxi.gov.cn/api/main/announcements.json";

		/**
		 * 无锡推荐信息URL
		 */
		public static final String IMPORT_NEWS_URL = "http://3g.wuxi.gov.cn/api/main/wuiximportantnews.json";

		/**
		 * 用户登录URL {username}用户名占位符 {pwd}密码占位符
		 */

		public static final String LOGIN_URL = "http://3g.wuxi.gov.cn/api/login.json?username={username}&password={pwd}";

		public static final String REGIST_URL = "http://3g.wuxi.gov.cn/api/register.json";

		/**
		 * 公开电话 URL
		 * */
		public static final String OPENTEL_URL = "http://3g.wuxi.gov.cn/api/opentel/list.json";

		/**
		 * 热点话题列表 URL
		 * */
		public static final String HOTREVIEW_LIST_URL = "http://3g.wuxi.gov.cn/api/hotreview/list.json";

		/**
		 * 分页获取立法征求意见和民意征集 URL
		 * */
		public static final String POLITICS_LIST_URL = "http://3g.wuxi.gov.cn/api//politics/list.json";

		/**
		 * 获取当前用户 立法征求意见和民意征集 URL
		 * */
		public static final String MY_POLITICS_LIST_URL = "http://3g.wuxi.gov.cn/politics/mylist.json";
		/**
		 * 获取市长信箱列表 URL
		 * */
		public static final String MAYOR_MAILBOX_URL = "http://3g.wuxi.gov.cn/api/letter/mayorletter.json";

		/**
		 * 获取建议咨询信件列表 URL
		 * */
		public static final String SUGGESTLETTER_URL = "http://3g.wuxi.gov.cn/api/letter/suggestletter.json";

		/**
		 * 获取热门信件列表 URL
		 * */
		public static final String HOTMAIL_URL = "http://3g.wuxi.gov.cn/api/letter/hotletter.json";

		/**
		 * 获取我的信件列表 URL
		 * */
		public static final String MY_LETTER_URL = "http://3g.wuxi.gov.cn/api/letter/myletters.json";

		/**
		 * 获取答复率总数统计 URL
		 * */
		public static final String LETTERS_ALLCOUNT_URL = "http://3g.wuxi.gov.cn/api/letter/allcount.json";

		/**
		 * 获取信箱各部门答复率总数统计 URL
		 * */
		public static final String LETTERS_STATISTICS_URL = "http://3g.wuxi.gov.cn/api/letter/statistics.json";

		/**
		 * 获取我的申报列表 URL {access_token} access_token 占位符 {start} 开始位置暂占位符
		 * {end}结束位置占位符
		 */
		public static final String MY_APPLY_URL = "http://3g.wuxi.gov.cn/api/zhengwu/myapply.json?access_token={access_token}&start={start}&end={end}";

		/**
		 * 获取我的在线咨询列表
		 * {access_token} access_token 占位符 {start} 开始位置暂占位符
		 * {end}结束位置占位符
		 */
		public static final String MYCONSULT_URL = "http://3g.wuxi.gov.cn/api/zhengwu/myconsult.json?access_token={access_token}&start={start}&end={end}";
		
		/**
		 * 效能投诉列表
		 * {start}开始占位符
		 * {end}结束占位符
		 */
		public static final String TOUSU_URL="http://3g.wuxi.gov.cn/api/zhengwu/tousu.json?start={start}&end={end}";
		
		
		/**
		 * 政务大厅 部门获取url
		 */
		public static final String DEPT_URL="http://3g.wuxi.gov.cn/api/zhengwu/depts.json";

	}

	/**
	 * 
	 * @author wanglu 泰得利通 存储地址
	 * 
	 */
	public static final class APPFiles {

		/**
		 * 项目外部文件存储路径
		 */
		public static final String APP_PATH = Environment
				.getExternalStorageDirectory().getPath() + "/chinawi/";

		/**
		 * 项目菜单图标存储路径
		 */
		public static final String MENU_ICON_PATH = APP_PATH + "menu/icon/";

	}

	/**
	 * 
	 * @author wanglu 泰得利通 异常信息常量
	 * 
	 */
	public static final class ExceptionMessage {

		/**
		 * 没有获取到数据
		 */
		public static final String NODATA_MEG = "没有获取到数据";

		/**
		 * 没有网络连接
		 */
		public static final String NO_NET = "没有网络连接";

		/**
		 * 数据格式不正确异常
		 */
		public static final String DATA_FORMATE = "数据格式错误";

	}

	/**
	 * 
	 * @author wanglu 泰得利通 缓存键
	 * 
	 */
	public static final class CacheKey {

		/**
		 * 首页导航菜单缓存key名称
		 */
		public static final String HOME_MENUITEM_KEY = "man_menu_item";

		/**
		 * 登录的用户key
		 */
		public static final String LOGIN_USER = "login_user";

	}

	/**
	 * 
	 * @author wanglu 泰得利通 Sharepreference 保存常量
	 */
	public static final class SharepreferenceKey {
		/**
		 * shareprefer名称
		 */
		public static final String SHARE_CONFIG = "chinawuxi_sp_config";

		/**
		 * 令牌
		 */
		public static final String ACCESSTOKEN = "accessToken";

		public static final String REFRESHTOKEN = "refreshToken";

		public static final String USERNAME = "userName";

		/**
		 * 测试用户 用户名：youngii 密码：123123 的ACCESSTOKEN
		 * */
		public static final String TEST_ACCESSTOKEN = "873778da0396423a8831af27956e097d";

	}

	/**
	 * 
	 * @author wanglu 泰得利通 fragment名字
	 * 
	 */
	public enum FragmentName {
		/**
		 * 登录fragment
		 */
		LOGIN_FRAGMENT,

		/**
		 * 注册 fragments
		 */
		REGIST_FRAGMENT;
	}

}

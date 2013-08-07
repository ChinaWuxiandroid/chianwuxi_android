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
		 * 跟地址
		 */
		public static final String ROOT_URL="http://3g.wuxi.gov.cn/api";
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
		 * 公共论坛列表URL
		 */
		public static final String FORUM_LIST_URL = "http://3g.wuxi.gov.cn/api/publicbbs/list.json";

		/**
		 * 公开电话 URL
		 * */
		public static final String OPENTEL_URL = "http://3g.wuxi.gov.cn/api/opentel/list.json";

		/**
		 * 热点话题列表 URL
		 * */
		public static final String HOTREVIEW_LIST_URL = "http://3g.wuxi.gov.cn/api/hotreview/list.json";

		/**
		 * 热点话题内容 URL
		 * */
		public static final String HOTREVIEWCONTENT_LIST_URL = "http://3g.wuxi.gov.cn/api/hotreview/{id}.json";

		/**
		 * 分页获取立法征求意见和民意征集 URL
		 * */
		public static final String POLITICS_LIST_URL = "http://3g.wuxi.gov.cn/api/politics/list.json";

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
		 * 获取我的在线咨询列表 {access_token} access_token 占位符 {start} 开始位置暂占位符
		 * {end}结束位置占位符
		 */
		public static final String MYCONSULT_URL = "http://3g.wuxi.gov.cn/api/zhengwu/myconsult.json?access_token={access_token}&start={start}&end={end}";

		/**
		 * 效能投诉列表 {start}开始占位符 {end}结束占位符
		 */
		public static final String TOUSU_URL = "http://3g.wuxi.gov.cn/api/zhengwu/tousu.json?start={start}&end={end}";

		/**
		 * 政务大厅 部门获取url
		 */
		public static final String DEPT_URL = "http://3g.wuxi.gov.cn/api/zhengwu/depts.json";

		/**
		 * 我要写信 {access_token} access_token {doprojectid} 提交信箱的项目编号占位符 {typeid}
		 * 咨询类型 占位符 {title}标题 {content}内容占位符 {openstate}是否公开
		 * {sentmailback}是否邮件回复 {msgstatus}是否短信回复
		 * */
		public static final String IWANTMAIL_URL = "http://3g.wuxi.gov.cn/api/letter/submit.json?"
				+ "access_token={access_token}&doprojectid={doprojectid}&typeid={typeid}&title={title}"
				+ "&content={content}&openstate={openstate}&sentmailback={sentmailback}&msgstatus={msgstatus}";

		/**
		 * 政务大厅，办件分类获取 type 01：个人身份 02：个人办事 03：企业行业 04：企业办事 05：港澳台侨、外国人 06：主题服务
		 */
		public static final String KIND_TYPE_URL = "http://3g.wuxi.gov.cn/api/zhengwu/kindtype.json?type={type}";

		/**
		 * 政务大厅，根据部们获取部门ID获取办件信息
		 */
		public static final String GETITEM_BYDEPT_URL = "http://3g.wuxi.gov.cn/api/zhengwu/itemlistdept.json?deptid={deptid}&start={start}&end={end}";

		/**
		 * 
		 * 政务大厅根据，kindType类型获办件列表
		 */
		public static final String GETITEM_BYKINDTYPE_URL = "http://3g.wuxi.gov.cn/api/zhengwu/itemlist.json?type={type}&kindtype={kindtype}&start={start}&end={end}";

		/**
		 * 政务大厅多条件查询接口
		 * 
		 * 参数:itemname false String 办件名称，模糊查询 qltype false String 办件类型 deptid false
		 * String 部门id year true Int 年份 start true Int 分页开始 end true Int 分页结束
		 */
		public static final String GETITEM_QUERY_URL = "http://3g.wuxi.gov.cn/api/zhengwu/itemlist/query.json";


		/**
		 * 政务大厅获取表格下载列表
		 * {deptid} 部门ID
		 * {start}开始记录
		 * {end} 结束记录
		 */
		public static final String GETTABLE_DOWNLOADS_URL="http://3g.wuxi.gov.cn/api/zhengwu/downloadfiles.json?deptid={deptid}&start={start}&end={end}";


		/**
		 * 政务大厅获取好办件 行政许可详情信息  
		 * 
		 * {id} id 
		 */
		public static final String GETGOVER_ITEMDETIAL_XK_URL="http://3g.wuxi.gov.cn/api/zhengwu/item/XK.json?id={id}";


		/**
		 * 政务大厅获取好办件 其他详情信息  
		 */
		public static final String GETGOVER_ITEMDETIAL_QT_URL="http://3g.wuxi.gov.cn/api/zhengwu/item/QT.json?id={id}";

		/**
		 * 政务大厅获取好办件 处罚详情信息  
		 */
		public static final String GETGOVER_ITEMDETIAL_CF_URL="http://3g.wuxi.gov.cn/api/zhengwu/item/CF.json?id={id}";

		/**
		 *  政务大厅获取好办件 征收详情信息  
		 */
		public static final String GETGOVER_ITEMDETIAL_ZS_URL="http://3g.wuxi.gov.cn/api/zhengwu/item/ZS.json?id={id}";
		/**
		 * 政务大厅获取好办件 强制详情信息  
		 */
		public static final String GETGOVER_ITEMDETIAL_QZ_URL="http://3g.wuxi.gov.cn/api/zhengwu/item/QZ.json?id={id}";

		/**
		 * 政务大厅办件统计
		 */
		public static final String GETGOVEDR_ITEM_COUNT_URL="http://3g.wuxi.gov.cn/api/zhengwu/allcount.json";

		/**
		 * 政务大厅，办件在线咨询详情
		 */
		public static final String GETGOVER_ONLINEASK_DETAIL_URL="http://3g.wuxi.gov.cn/api/zhengwu/consult/{id}.json?access_token={access_token}";
		/**
		 * 政务大厅流程图片获取
		 */
		public static final String GETLIUC_IMG_URL="http://3g.wuxi.gov.cn/api/zhengwu/lcimg/{id}.json";

		/**
		 * 政务大厅 在线咨询提交URL
		 */
		public static final String GOVER_ONLEINASK_COMMIT_URL="http://3g.wuxi.gov.cn/api/zhengwu/consult.json?id={id}&type={type}&content={content}&access_token={access_token}";


		/**
		 * 政务大厅表   在线办理表单提交
		 */

		public static final String GOVER_APPLY_ONLINE_URL="http://3g.wuxi.gov.cn/api/zhengwu/applyonline.json";


		/*
		 * 政务大厅下 附近现在地址
		 */
		public static final String GOVER_FILE_DOWN_URL="http://3g.wuxi.gov.cn/api/zhengwu/file/{id}.json";



		/**
		 * 政府信息公开  获取公开意见箱的APPUI布局数据
		 * */
		public static final String GOVERMSG_WORKSUGGESTIONBOX_LAYOUT_URL="http://3g.wuxi.gov.cn/api/selfforms/workopinion.json";

		/**
		 * 政府信息公开  提交自定义表单信息的URL
		 * */
		public static final String SUBMIT_SELFFORM_URL="http://3g.wuxi.gov.cn/api/selfforms/submit.json";

		/**
		 * 政府信息公开  各部门依申请公开
		 * */
		public static final String APPLYDEPT_URL="http://3g.wuxi.gov.cn/api/applyopen/deps.json";


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


		/**
		 * 下载文件存放地址
		 */
		public static final String DOWNLOAF_FILE_PATH=APP_PATH+"file/";

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

		/**
		 * 政务大厅中部门信息
		 */
		public static final String DEPT_KEY = "gover_dept";

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
		REGIST_FRAGMENT,

		/**
		 * 全站搜索
		 */
		MAINSEARCH_FRAGMENT,
		/**
		 * 政务大厅
		 */
		MAINMINEFRAGMENT,
		/**
		 * 系统设置
		 */
		SYSTEMSETF_RAGMENT,
		/**
		 * 首页常用栏设置
		 */
		MENUITEMSET_FRAGMENT,

		/**
		 * 网站地图
		 */

		SITEMAP_FRAGMENT,
		/**
		 * 在线咨询
		 */

		MYONLINEASKFRAGMENT,
		/**
		 * 公共论坛帖子详细界面
		 */
		GIP_FOROUM_FRAGMENT,
		/**
		 * 公众论坛发表帖子碎片
		 */
		GIP_FORUM_POST_FRAGMENT,

		/**
		 * 热点话题内容
		 * */
		HOTREVIEW_CONTENT_FRAGMENT,

		/**
		 * 政务大厅行政许可办件详情
		 */
		GOVERSALOONDETAIL_XK_FRAGMENT,
		/**
		 * 政务大厅行其它可办件详情
		 */
		GOVERSALOONDETAIL_QT_FRAGMENT,
		/**
		 * 政务大厅行处罚可办件详情
		 */
		GOVERSALOONDETAIL_CF_FRAGMENT,
		/**
		 * 政务大厅行强制可办件详情
		 */
		GOVERSALOONDETAIL_QZ_FRAGMENT,
		/**
		 * 政务大厅行征收可办件详情
		 */
		GOVERSALOONDETAIL_ZS_FRAGMENT,


		/**
		 * 魅力锡城内容页
		 */
		WUXICHANNELCONTENTDETAILFRAGMENT,
		/**
		 * 资讯中心->热点专题内容页
		 */
		HOTTOPICCONTENTFRAGMENT
		;
	}

}

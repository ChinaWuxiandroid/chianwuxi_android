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
		 * 域名地址
		 */
		public static final String DOMAIN_URL = "http://m.wuxi.gov.cn";

		/**
		 * 根地址
		 */
		public static final String BASIC_URL = DOMAIN_URL + "/api";

		/**
		 * 菜单获取URL
		 * 
		 * recursions false Int 返回层次,不输入则为2,为0则返回所有
		 */
		public static final String MENU_URL = BASIC_URL + "/menu/tree.json";

		/**
		 * 获取子菜单接口 {id}要获取子菜单的Id
		 */
		public static final String SUB_MENU_URL = BASIC_URL
				+ "/menu/{id}/menus.json";

		/**
		 * 获取子频道URL {channelId}频道ID占位符
		 */
		public static final String CHANNEL_URL = BASIC_URL
				+ "/channel/{channelId}/channels.json";

		/**
		 * 获取频道内容信息 {id}频道Id占位符
		 */

		public static final String CHANNEL_CONTENT_URL = BASIC_URL
				+ "/channel/{id}/contents.json";

		/**
		 * 类容页阅读次数URL {id}类容Id
		 */

		public static final String CONTENT_BROWSECOUNT_URL = BASIC_URL
				+ "/content/{id}/flush.json";

		/**
		 * 获取频道内容分页接口 {id}频道占位符 {start}开始位置占位符 {end}结束位置占位符
		 */
		public static final String CHANNEL_CONTENT_P_URL = BASIC_URL
				+ "/channel/{id}/contents_p.json?start={start}&end={end}";

		/**
		 * 推荐公告URL
		 */

		public static final String ANNOUNCENTS_URL = BASIC_URL
				+ "/main/announcements.json";

		/**
		 * 无锡推荐信息URL
		 */
		public static final String IMPORT_NEWS_URL = BASIC_URL
				+ "/main/wuiximportantnews.json";

		/**
		 * 用户登录URL {username}用户名占位符 {pwd}密码占位符
		 */

		public static final String LOGIN_URL = BASIC_URL
				+ "/login.json?username={username}&password={pwd}";

		public static final String REGIST_URL = BASIC_URL + "/register.json";

		/**
		 * 政民互动 12345来信办理平台 部门领导信箱 各部门领导信箱列表
		 */
		public static final String PART_LEADER_MAIL_URL = BASIC_URL
				+ "/letter/deps.json";

		/**
		 * 政民互动 12345来信办理平台 信件查询 部门下拉列表 URL
		 */
		public static final String QUERY_MAIL_DEP_URL = BASIC_URL
				+ "/letter/depids.json";

		/**
		 * 政民互动 12345来信办理平台 信件查询 内容分类 URL
		 */
		public static final String QUERY_MAIL_CONTENT_TYPE_URL = BASIC_URL
				+ "/letter/contenttype.json";

		/**
		 * 政民互动 12345来信办理平台 信件查询 信件分类 URL
		 */
		public static final String QUERY_MAIL_TYPE_URL = BASIC_URL
				+ "/letter/lettertype.json";

		/**
		 * 政民互动 12345来信办理平台 信件评价URL
		 */
		public static final String MAIL_COMMENT_SUBMIT_URL = BASIC_URL
				+ "/letter/appraise.json";

		/**
		 * 公共论坛列表URL
		 */
		public static final String FORUM_LIST_URL = BASIC_URL
				+ "/publicbbs/list.json";

		/**
		 * 政民互动 公众论坛 获取帖子详情
		 */
		public static final String FORUM_CONTENT_URL = BASIC_URL
				+ "/publicbbs/{id}/details.json";

		/**
		 * 公开电话 URL
		 * 
		 */
		public static final String OPENTEL_URL = BASIC_URL
				+ "/opentel/list.json";

		/**
		 * 热点话题列表 URL
		 * 
		 */
		public static final String HOTREVIEW_LIST_URL = BASIC_URL
				+ "/hotreview/list.json";

		/**
		 * 热点话题内容 URL
		 * 
		 */
		public static final String HOTREVIEWCONTENT_LIST_URL = BASIC_URL
				+ "/hotreview/{id}.json";

		/**
		 * 热点话题 话题评论 URL
		 */
		public static final String HOT_REVIEW_COMMENT_URL = BASIC_URL
				+ "/hotreview/{id}/reply.json";

		/**
		 * 热点话题 回复 列表 URL
		 */
		public static final String HOTREVIEW_REPLY_LIST_URL = BASIC_URL
				+ "/hotreview/{id}/replylist.json";

		/**
		 * 分页获取立法征求意见和民意征集 URL
		 * */
		public static final String POLITICS_LIST_URL = BASIC_URL
				+ "/politics/list.json";

		/**
		 * 获取 我的征求意见平台信息 URL
		 */
		public static final String POLITICS_MY_LIST_URL = BASIC_URL
				+ "/politics/mylist.json";

		/**
		 * 获取当前用户 立法征求意见和民意征集 URL
		 * */
		public static final String MY_POLITICS_LIST_URL = BASIC_URL
				+ "/politics/mylist.json";

		/**
		 * 获取市长信箱列表 URL
		 * */
		public static final String MAYOR_MAILBOX_URL = BASIC_URL
				+ "/letter/mayorletter.json";

		/**
		 * 获取部门领导信件列表URL
		 */
		public static final String PART_LEADER_MAIL_LIST_URL = BASIC_URL
				+ "/letter/depletter.json";

		/**
		 * 获取建议咨询信件列表 URL
		 * */
		public static final String SUGGESTLETTER_URL = BASIC_URL
				+ "/letter/suggestletter.json";

		/**
		 * 获取热门信件列表 URL
		 * */
		public static final String HOTMAIL_URL = BASIC_URL
				+ "/letter/hotletter.json";

		/**
		 * 获取我的信件列表 URL
		 * */
		public static final String MY_LETTER_URL = BASIC_URL
				+ "/letter/myletters.json";

		/**
		 * 获取答复率总数统计 URL
		 * */
		public static final String LETTERS_ALLCOUNT_URL = BASIC_URL
				+ "/letter/allcount.json";

		/**
		 * 获取信箱各部门答复率总数统计 URL
		 * */
		public static final String LETTERS_STATISTICS_URL = BASIC_URL
				+ "/letter/statistics.json";

		/**
		 * 获取我的申报列表 URL {access_token} access_token 占位符 {start} 开始位置暂占位符
		 * {end}结束位置占位符
		 */
		public static final String MY_APPLY_URL = BASIC_URL
				+ "/zhengwu/myapply.json?access_token={access_token}&start={start}&end={end}";

		/**
		 * 获取我的在线咨询列表 {access_token} access_token 占位符 {start} 开始位置暂占位符
		 * {end}结束位置占位符
		 */
		public static final String MYCONSULT_URL = BASIC_URL
				+ "/zhengwu/myconsult.json?access_token={access_token}&start={start}&end={end}";

		/**
		 * 政民互动 获取信件详细信息 URL
		 */
		public static final String MAIL_INFO_URL = BASIC_URL
				+ "/letter/{id}.json";

		/**
		 * 政民互动 公众论坛 发帖
		 */
		public static final String FOORUM_POST_URL = BASIC_URL
				+ "/publicbbs/newpost.json";

		/**
		 * 政民互动 公众论坛 评论
		 */
		public static final String FORUM_COMMENT_URL = BASIC_URL
				+ "/publicbbs/{id}/result_submit.json";

		/**
		 * 政民互动 视频直播平台 访谈实录
		 */
		public static final String MEMOIR_CONTENT_URL = BASIC_URL
				+ "/interview/{interViewId}/textrecords.json";

		/**
		 * 政民互动 视频直播平台 留言提问
		 */
		public static final String LEAVE_MESSAGE_CONTENT_URL = BASIC_URL
				+ "/interview/{interViewId}/questions.json";

		/**
		 * 政民互动 视频直播平台 提交留言提问
		 */
		public static final String VIDEO_SUBMIT_IDEA_URL = BASIC_URL
				+ "/interview/{interViewId}/submit.json";

		/**
		 * 政民互动 视频直播平台 走进直播间 往期回顾
		 */
		public static final String VEDIO_REVIEW_CONTENT_URL = BASIC_URL
				+ "/interview/history.json";

		/**
		 * 政民互动 征求意见平台 立法征求意见详情 和 民意征集
		 */
		public static final String LEGISLATION_CONTENT_URL = BASIC_URL
				+ "/politics/{id}.json";

		/**
		 * 政民互动 征求意见平台 民意征集 回复列表 和 立法征求意见
		 */
		public static final String LEGISLATION_REPLY_URL = BASIC_URL
				+ "/politics/replylist.json";

		/**
		 * 政民互动 征求意见平台 立法征求意见 民意征集 提交评论 URL
		 */
		public static final String LEGISLATION_SUBMIT_DATA_URL = BASIC_URL
				+ "/politics/reply.json";

		/**
		 * 政民互动 网上调查 列表
		 */
		public static final String INTERNET_SURVEY_URL = BASIC_URL
				+ "/survery/list.json";

		/**
		 * 效能投诉列表 {start}开始占位符 {end}结束占位符
		 */
		public static final String TOUSU_URL = BASIC_URL
				+ "/zhengwu/tousu.json?start={start}&end={end}";

		/**
		 * 政务大厅 部门获取url
		 */
		public static final String DEPT_URL = BASIC_URL + "/zhengwu/depts.json";

		/**
		 * 我要写信URL
		 * */
		public static final String IWANTMAIL_URL = BASIC_URL
				+ "/letter/submit.json";

		/**
		 * 政民互动视频播放
		 */
		public static final String PLAY_VIDEO_URL = BASIC_URL
				+ "/interview/{id}/video.json";

		/**
		 * 政务大厅，办件分类获取 type 01：个人身份 02：个人办事 03：企业行业 04：企业办事 05：港澳台侨、外国人 06：主题服务
		 */
		public static final String KIND_TYPE_URL = BASIC_URL
				+ "/zhengwu/kindtype.json?type={type}";

		/**
		 * 政务大厅，根据部们获取部门ID获取办件信息
		 */
		public static final String GETITEM_BYDEPT_URL = BASIC_URL
				+ "/zhengwu/itemlistdept.json?deptid={deptid}&start={start}&end={end}";

		/**
		 * 
		 * 政务大厅根据，kindType类型获办件列表
		 */
		public static final String GETITEM_BYKINDTYPE_URL = BASIC_URL
				+ "/zhengwu/itemlist.json?type={type}&kindtype={kindtype}&start={start}&end={end}";

		/**
		 * 政务大厅多条件查询接口
		 * 
		 * 参数:itemname false String 办件名称，模糊查询 qltype false String 办件类型 deptid
		 * false String 部门id year true Int 年份 start true Int 分页开始 end true Int
		 * 分页结束
		 */
		public static final String GETITEM_QUERY_URL = BASIC_URL
				+ "/zhengwu/itemlist/query.json";

		/**
		 * 政务大厅获取表格下载列表 {deptid} 部门ID {start}开始记录 {end} 结束记录
		 */
		public static final String GETTABLE_DOWNLOADS_URL = BASIC_URL
				+ "/zhengwu/downloadfiles.json?deptid={deptid}&start={start}&end={end}";

		/**
		 * 政务大厅获取好办件 行政许可详情信息
		 * 
		 * {id} id
		 */
		public static final String GETGOVER_ITEMDETIAL_XK_URL = BASIC_URL
				+ "/zhengwu/item/XK.json?id={id}";

		/**
		 * 政务大厅获取好办件 其他详情信息
		 */
		public static final String GETGOVER_ITEMDETIAL_QT_URL = BASIC_URL
				+ "/zhengwu/item/QT.json?id={id}";

		/**
		 * 政务大厅获取好办件 处罚详情信息
		 */
		public static final String GETGOVER_ITEMDETIAL_CF_URL = BASIC_URL
				+ "/zhengwu/item/CF.json?id={id}";

		/**
		 * 政务大厅获取好办件 征收详情信息
		 */
		public static final String GETGOVER_ITEMDETIAL_ZS_URL = BASIC_URL
				+ "/zhengwu/item/ZS.json?id={id}";

		/**
		 * 政务大厅获取好办件 强制详情信息
		 */
		public static final String GETGOVER_ITEMDETIAL_QZ_URL = BASIC_URL
				+ "/zhengwu/item/QZ.json?id={id}";

		/**
		 * 政务大厅办件统计
		 */
		public static final String GETGOVEDR_ITEM_COUNT_URL = BASIC_URL
				+ "/zhengwu/allcount.json";

		/**
		 * 政务大厅，办件在线咨询详情
		 */
		public static final String GETGOVER_ONLINEASK_DETAIL_URL = BASIC_URL
				+ "/zhengwu/consult/{id}.json?access_token={access_token}";

		/**
		 * 政务大厅流程图片获取
		 */
		public static final String GETLIUC_IMG_URL = BASIC_URL
				+ "/zhengwu/lcimg/{id}.json";

		/**
		 * 政务大厅 在线咨询提交URL
		 */
		public static final String GOVER_ONLEINASK_COMMIT_URL = BASIC_URL
				+ "/zhengwu/consult.json?id={id}&type={type}&content={content}&access_token={access_token}";

		/**
		 * 政务大厅表 在线办理表单提交
		 */

		public static final String GOVER_APPLY_ONLINE_URL = BASIC_URL
				+ "/zhengwu/applyonline.json";

		/**
		 * 政务大厅下 附近现在地址
		 */
		public static final String GOVER_FILE_DOWN_URL = BASIC_URL
				+ "/zhengwu/file/{id}.json";

		/**
		 * 信件查询 内容分类下来
		 */

		public static final String CONTENT_TYPE_URL = BASIC_URL
				+ "/zhengwu/types.json?type=0";

		/**
		 * 信件查询 信件分类下拉
		 */
		public static final String LETTER_TYPE_URL = BASIC_URL
				+ "/zhengwu/types.json?type=1";

		/**
		 * 获取收藏列表
		 */
		public static final String GET_FAVORITES_URL = BASIC_URL
				+ "/menu/favorites.json";

		/**
		 * 政府信息公开 获取公开意见箱的APPUI布局数据
		 * */
		public static final String GOVERMSG_WORKSUGGESTIONBOX_LAYOUT_URL = BASIC_URL
				+ "/selfforms/workopinion.json";

		/**
		 * 政府信息公开 提交自定义表单信息的URL
		 * */
		public static final String SUBMIT_SELFFORM_URL = BASIC_URL
				+ "/selfforms/submit.json";

		/**
		 * 政府信息公开 市区县依申请公开
		 * */
		public static final String APPLYGOVER_URL = BASIC_URL
				+ "/applyopen/quxians.json";

		/**
		 * 政府信息公开 各部门依申请公开
		 * */
		public static final String APPLYDEPT_URL = BASIC_URL
				+ "/applyopen/deps.json";

		/**
		 * 政府信息公开 依申请公开网上办件统计
		 * */
		public static final String INTERNET_LETTERS_STATISTICS_URL = BASIC_URL
				+ "/applyopen/applycount.json";

		/**
		 * 政府信息公开 获取我的依申请公开列表 URL {access_token} access_token 占位符 {start}
		 * 开始位置暂占位符 {end}结束位置占位符
		 */
		public static final String MY_APPLYPAGE_URL = BASIC_URL
				+ "/applyopen/myapplypage.json?access_token={access_token}&start={start}&end={end}";

		/**
		 * 政府信息公开 获取 依申请公开的表格 下载地址 的URl
		 * */
		public static final String GOVERMSG_TABLE_DOWNLOAD_URL = BASIC_URL
				+ "/applyopen/doc.json";

		/**
		 * 政府信息公开 个人依申请公开提交
		 * */
		public static final String CITIZEN_APPLY_SUBMIT_URL = BASIC_URL
				+ "/applyopen/personalapply.json";

		/**
		 * 政府信息公开 法人/组织依申请公开提交
		 * */
		public static final String LEGALPERSONAPPLY_SUBMIT_URL = BASIC_URL
				+ "/applyopen/orgapply.json";

		/**
		 * 全文检索 URL
		 * */
		public static final String SEARCH_URL = "http://3g.wuxi.gov.cn/contentsearch/getJson?jsonpCallback=?&query={query}&sitename={sitename}&countperpage={countperpage}&pagenum={pagenum}";

		/**
		 * 全文检索 sitename
		 * */
		public static final String SEARCH_SITENAME = "site_1374209144805";
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
		public static final String DOWNLOAF_FILE_PATH = APP_PATH + "file/";

		public static final String CACHE_FILE_PATH = DOWNLOAF_FILE_PATH
				+ "cache/";
		/**
		 * 内容列表缓存文件夹
		 */
		public static final String CAHCE_FILE_CONTENT_PATH = CACHE_FILE_PATH
				+ "content/";

		/**
		 * 政府信息公开中 依申请公开 唯一表格 文件名（服务器端未提供字段）
		 */
		public static final String GOVERMSG_APPLYOPEN_TABLENAME = "无锡市人民政府办公室信息公开申请表.doc";

	}

	/**
	 * 
	 * @author wanglu 泰得利通
	 * @version $1.0, 2013-9-2 2013-9-2 GMT+08:00 菜单选中的key值
	 */
	public static final class CheckPositionKey {

		/**
		 * 一级菜单只首页的6个大大模块的菜单 一级子菜单选中的key值 如：魅力锡城，头部一排菜单，政务大厅，左侧菜单都属于一级子菜单
		 */
		public static final String LEVEL_TWO__KEY = "LEVEL_TWO_KEY";

		/**
		 * 二级子菜单选中的key值 如：魅力锡城左侧的竖排菜单为二级子菜单
		 */
		public static final String LEVEL_THREE_KEY = "LEVEL_THREE_KEY";

		/**
		 * 政民互动我要写信功能
		 */
		public static final String WIRTE_MAIL_KEY = "WIRTE_MAIL_KEY";

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
		 * 首页六大主模块导航菜单缓存key名称
		 */
		public static final String MAIN_MENUITEM_KEY = "man_menu_item";

		/**
		 * 收藏菜单+首页六大模块 缓存key
		 */
		public static final String HOME_MENUITEM_KEY = "home_menu_item";

		/**
		 * 收藏列表菜单
		 */
		public static final String FAVAITEMS_KEY = "favaitems";

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

		public static final String LAST_LOGIN_TIME = "last_login_time";// 上一次登录时间

		public static final String USEAPP_COUNT = "use_app_count";// 使用app次数

		/**
		 * shareprefer名称
		 */
		public static final String SHARE_CONFIG = "chinawuxi_sp_config";

		/**
		 * 令牌
		 */
		public static final String ACCESSTOKEN = "accessToken";

		public static final String REFRESHTOKEN = "refreshToken";

		/**
		 * 登录用户名
		 */
		public static final String USERNAME = "userName";

		/**
		 * 测试用户 用户名：youngii 密码：123123 的ACCESSTOKEN
		 * */
		// public static final String TEST_ACCESSTOKEN =
		// "bd58fcdfe5b54f4c95ed5f2e3a945f7c";

	}

}

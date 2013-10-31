package com.wuxi.app.listeners;

import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 初始化 政府信息公开 里 市政府信息公开目录 里的展开列表后的布局Fragment
 * 
 * @author 杨宸 智佳
 * */
public class GoverMsgInitInfoOpenListener {
	/**
	 * 0 -没有过滤搜索条目类型 1-部门时间型 2-区县时间型
	 * */
	public static int getChannelFragmentType(Channel channel, int defaultType) {

		int fifterType = defaultType; // 默认没有搜索条目类型
		String channleName = channel.getChannelName();
		// 规划计划
		if (channleName.equals("中长期规划") || channleName.equals("年度规划计划")
				|| channleName.equals("专项规划计划")) {
			fifterType = 1;
		}
		// 工作报告
//		else if (channleName.equals("县区政府工作报告")) {
//			fifterType = 2;
//		} 
		else if (channleName.equals("部门工作总结")) {
			fifterType = 1;
		}
		// 业务工作
		// 财政信息
		else if (channleName.equals("部门预决算报告") ) {
			fifterType = 1;
		}
//		|| channleName.equals("专项资金")
		// 政府采购
		else if (channleName.equals("采购公示公告") || channleName.equals("中标公告")) {
			fifterType = 1;
		}
		// 价格收费
//		channleName.equals("违法违规行为查处")
		else if (channleName.equals("部门行政事业性收费")) {
			fifterType = 1;
		}
		// 统计信息
		else if (channleName.equals("部门统计数据") || channleName.equals("数据解读")) {
			fifterType = 1;
		} 
//		else if (channleName.equals("县区统计数据")) {
//			fifterType = 2;
//		}
		// 人事信息
		else if (channleName.equals("公务员招考") || channleName.equals("事业单位招聘")
				|| channleName.equals("干部任前公示") || channleName.equals("干部任免公告")
				|| channleName.equals("干部选拔的条件和程序")) {
			fifterType = 1;
		}
		// 应急管理
		else if (channleName.equals("应急知识")) {
			fifterType = 1;
		}
		// 重要会议
		else if (channleName.equals("其他重要会议")) {
			fifterType = 1;
		}else {
			fifterType = 0;
		}
//		// 实事项目
//
//		else if (channleName.equals("项目介绍") || channleName.equals("进展情况")) {
//			fifterType = 1;
//		}
//		// 重点工作
//		else if (channleName.equals("目标任务") || channleName.equals("进展情况")) {
//			fifterType = 1;
//		}
//		// 重大项目
//		else if (channleName.equals("项目介绍") || channleName.equals("进展情况")) {
//			fifterType = 1;
//		}
//		// 社会管理
//		// 社会管理--房屋拆遷
//		else if (channleName.equals("拆迁公告")
//				|| channleName.equals("补偿补助费用发放使用情况")
//				|| channleName.equals("社会公益") || channleName.equals("抢险救灾")
//				|| channleName.equals("优抚救济") || channleName.equals("社会捐助")) {
//			fifterType = 1;
//		}
//		// 社会管理--食品安全
//		else if (channleName.equals("食品安全标准") || channleName.equals("日常监管信息")
//				|| channleName.equals("社会公益") || channleName.equals("抢险救灾")
//				|| channleName.equals("优抚救济") || channleName.equals("社会捐助")) {
//			fifterType = 1;
//		}
		return fifterType;
	}

	public static int getMenuItemFragmentType(MenuItem menuItem, int defaultType) {

		int fifterType = defaultType; // 默认没有搜索条目类型
		String menuName = menuItem.getName();

		// 政府概括
		if (menuName.equals("市政府领导分工") || menuName.equals("市政府工作规则")
				|| menuName.equals("市政府组成部门") || menuName.equals("市政府直属特设机构")
				|| menuName.equals("市政府直属部门") || menuName.equals("双重领导单位")
				|| menuName.equals("市政府派出机构") || menuName.equals("县区政府概况")) {

			fifterType = 3;

		}

		// 政策法规
		else if (menuName.equals("国家法律") || menuName.equals("国家行政法规规章")
				|| menuName.equals("省级法规规章") || menuName.equals("市级法规规章")
				|| menuName.equals("市政府文件") || menuName.equals("市政府办公室文件")
				|| menuName.equals("县区文件") || menuName.equals("部门文件")
				|| menuName.equals("其它文件") || menuName.equals("政策解读")) {
			fifterType = 4;
		}
		// 行政事项
		else if (menuName.equals("行政许可") || menuName.equals("行政处罚")
				|| menuName.equals("行政征收") || menuName.equals("行政强制")
				|| menuName.equals("其它")) {
			fifterType = 5;
		}
		return fifterType;
	}
}

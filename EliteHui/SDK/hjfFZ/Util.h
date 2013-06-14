//
//  Util.h
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

/*===============================*/
//工具类
//封装了常用的方法,公共方法应该放到该类中
/*===============================*/

#import <Foundation/Foundation.h>
#import "MBProgressHUD.h"

@interface Util : NSObject

/**
 *	代码创建父视图的UIView
 *	@returns view
 */
+ (UIView *)UIViewControllerAndView;

/**
 *	创建导航栏
 *	@param view 加载导航栏的父视图
 */
+ (void)navbarWithView:(UIView *)view;

/**
 *	创建导航栏返回按钮
 *	@param view 加载导航栏返回按钮的父视图
 *	@param targrt 调用self
 *	@param action 按钮响应事件
 */
+ (void)navbarWithBackBtn:(UIView *)view
                andTarget:(id)targrt
             andandAction:(SEL)action;

/**
 *	创建导航栏返回按钮
 *	@param view 加载导航栏返回按钮的父视图
 *	@param targrt 调用self
 *	@param action 按钮响应事件
 */
+ (void)navbarWithBtn:(UIView *)view
             andtitlt:(NSString *)title
                andTarget:(id)targrt
         andandAction:(SEL)action;

/**
 * 从资源文件中获取UIImage (PNG 格式)
 * @param imageName 图片名称(不包含后缀名)
 * @return UIImage
 */
+ (UIImage*)retrunImageByName:(NSString*)imageName;

/**
 * 加载HUD效果
 * view 添加HUD效果的父视图
 * msg  提示的文字
 */
+ (void)showHUD:(UIView *)view andString:(NSString *)msg;

/**
 * 移除HUD效果
 */
+ (void)removeHUD;

/**
 * 消除UITableView多余的分割线效果
 */
+ (void)setExtraCellLineHidden: (UITableView *)tableView;


@end

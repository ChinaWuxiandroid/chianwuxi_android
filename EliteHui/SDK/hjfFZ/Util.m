//
//  Util.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "Util.h"

static MBProgressHUD *HUD;

@implementation Util

/**
 *	代码创建父视图的UIView
 *	@returns view
 */
+ (UIView *)UIViewControllerAndView
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, 480)];
    [view setAutoresizingMask:UIViewAutoresizingFlexibleHeight];
    return view;
}

/**
 *	创建导航栏
 *	@param view 加载导航栏的父视图
 */
+ (void)navbarWithView:(UIView *)view
{
    UIImageView *navbarImgView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
    //navbarImgView.image = [Util retrunImageByName:NavBar_BackGroung_Img];
    [navbarImgView setBackgroundColor:[UIColor blackColor]];
    [view addSubview:navbarImgView];
}

/**
 *	创建导航栏返回按钮
 *	@param view 加载导航栏返回按钮的父视图
 *	@param targrt 调用self
 *	@param action 按钮响应事件
 */
+ (void)navbarWithBackBtn:(UIView *)view
                andTarget:(id)targrt
             andandAction:(SEL)action
{
    UIButton *navbarBackBtn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [navbarBackBtn setFrame:CGRectMake(10, 5, 60, 35)];
    //[navbarBackBtn setBackgroundImage:[Util retrunImageByName:NavBar_BackBtn_Img] forState:UIControlStateNormal];
    [navbarBackBtn setTitle:@"返回" forState:UIControlStateNormal];
    //[navbarBackBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [navbarBackBtn addTarget:targrt action:action forControlEvents:UIControlEventTouchUpInside];
    [view addSubview:navbarBackBtn];
}

/**
 *	创建导航栏返回按钮
 *	@param view 加载导航栏返回按钮的父视图
 *	@param targrt 调用self
 *	@param action 按钮响应事件
 */
+ (void)navbarWithBtn:(UIView *)view
             andtitlt:(NSString *)title
            andTarget:(id)targrt
         andandAction:(SEL)action
{
    UIButton *navbarBackBtn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [navbarBackBtn setFrame:CGRectMake(250, 5, 60, 35)];
    //[navbarBackBtn setBackgroundImage:[Util retrunImageByName:NavBar_Btn_Img] forState:UIControlStateNormal];
    [navbarBackBtn setTitle:title forState:UIControlStateNormal];
    //[navbarBackBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [navbarBackBtn addTarget:targrt action:action forControlEvents:UIControlEventTouchUpInside];
    [view addSubview:navbarBackBtn];
}

/**
 * 从资源文件中获取UIImage (PNG 格式)
 * @param imageName 图片名称(不包含后缀名)
 * @return UIImage
 */
+ (UIImage*)retrunImageByName:(NSString*)imageName
{
    NSString *imagePath = [[NSBundle mainBundle] pathForResource:imageName ofType:@"png"];
    UIImage *image = [UIImage imageWithContentsOfFile:imagePath];
    return image;
}

/**
 * 加载HUD效果
 * view 添加HUD效果的父视图
 * msg  提示的文字
 */
+ (void)showHUD:(UIView *)view andString:(NSString *)msg
{
    HUD = [[MBProgressHUD alloc] initWithView:view];
	[view addSubview:HUD];
    //HUD.dimBackground = YES;
	HUD.labelText = msg;
	[HUD show:YES];
}

/**
 * 移除HUD效果
 */
+ (void)removeHUD
{
    [HUD hide:YES];
    [HUD removeFromSuperViewOnHide];
    HUD = nil;
}

/**
 * 消除UITableView多余的分割线效果
 */
+ (void)setExtraCellLineHidden: (UITableView *)tableView
{
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [tableView setTableFooterView:view];
}

@end

//
//  ColumnBar.h
//  EliteHui
//
//  Created by hjf on 13-6-13.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

/*===============================*/
//网易滑动选择效果
/*===============================*/

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

//每个按钮之间的间距
#define kSpace 20

@protocol ColumnBarDataSource;
@protocol ColumnBarDelegate;

@interface ColumnBar : UIImageView <UIScrollViewDelegate>

//滑动视图
@property (strong, nonatomic) UIScrollView         *myScrollView;
//当左边视图为显示完时的图片
@property (strong, nonatomic) UIImageView          *leftCapImageView;
//当右边视图为显示完时的图片
@property (strong, nonatomic) UIImageView          *rightCapImageView;
//移动图片
@property (strong, nonatomic) UIImageView          *moveImageView;
//选择的按钮
@property (assign, nonatomic) int                  selectedIndex;
//委托
@property(nonatomic, assign) id<ColumnBarDataSource>    dataSource;
@property(nonatomic, assign) id<ColumnBarDelegate>      delegate;
//初始化过后加载
- (void)reloadData;
//初始化加载默认选择哪一个
- (void)selectTabAtIndex:(int)index;

@end

@protocol ColumnBarDataSource <NSObject>

- (int)numberOfTabsInColumnBar:(ColumnBar *)columnBar;
- (NSString *)columnBar:(ColumnBar *)columnBar titleForTabAtIndex:(int)index;

@end

@protocol ColumnBarDelegate <NSObject>

- (void)columnBar:(ColumnBar *)columnBar didSelectedTabAtIndex:(int)index;

@end

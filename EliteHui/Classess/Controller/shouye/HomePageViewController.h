//
//  HomePageViewController.h
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

/*===============================*/
//视图控制器 首页页面
/*===============================*/

#import <UIKit/UIKit.h>
#import "PullingRefreshTableView.h"
#import "ColumnBar.h"

@interface HomePageViewController : UIViewController <PullingRefreshTableViewDelegate,UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate,ColumnBarDelegate,ColumnBarDataSource>

//创建滑动选择视图
@property (strong, nonatomic) ColumnBar                        *columnBar;
//滑动选择视图标题数组
@property (strong, nonatomic) NSArray                          *columnBarArray;
//新闻搜索
@property (strong, nonatomic) UISearchBar                      *newsSearch;
//首页表示图
@property (strong, nonatomic) PullingRefreshTableView          *homepageTableView;
//表示图数据源数组
@property (strong, nonatomic) NSMutableArray                   *dataArray;
//判断是否上拉刷新
@property (nonatomic)         BOOL                             refreshing;
//判断标签 hot 热点关注 elite 精英动态 activity活动发布 school校友风采
@property (strong, nonatomic) NSString                         *changeString;

@end

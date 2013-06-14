//
//  HomePageViewController.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "HomePageViewController.h"
#import "HomePageCell.h"
#import "UIImageView+WebCache.h"

@interface HomePageViewController ()

@end

@implementation HomePageViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _dataArray = [[NSMutableArray alloc] init];
        _columnBarArray = [[NSArray alloc] initWithObjects:@"热点关注",@"精英动态",@"活动发布",@"校友风采",nil];
    }
    return self;
}

#pragma mark UIViewController调用方法
- (void)loadView
{
    self.view = [Util UIViewControllerAndView];
    
    [Util navbarWithView:self.view];
    
    [self loadColumnBar];
    [self loadSearchBar];
    [self loadTableView];
}

#pragma 创建主页界面
//创建滑动选择视图
- (void)loadColumnBar
{
    _columnBar = [[ColumnBar alloc] initWithFrame:CGRectMake(0, 44, 320, 37)];
    _columnBar.dataSource = self;
    _columnBar.delegate = self;
    _columnBar.image = [UIImage imageNamed:@"navbar_background"];
    _columnBar.leftCapImageView.image = [UIImage imageNamed:@"navbar_left_more"];
    _columnBar.rightCapImageView.image = [UIImage imageNamed:@"navbar_right_more"];
    _columnBar.moveImageView.image = [UIImage imageNamed:@"navbar_selected_background"];
    [self.view addSubview:_columnBar];
    [_columnBar reloadData];
    [_columnBar selectTabAtIndex:0];
}
//创建搜索视图
- (void)loadSearchBar
{
    _newsSearch = [[UISearchBar alloc] initWithFrame:CGRectMake(0, 44+37, 320, 44)];
    [_newsSearch setBarStyle:UIBarStyleBlack];
    [_newsSearch setDelegate:self];
    [self.view addSubview:_newsSearch];
}
//创建表示图
- (void)loadTableView
{
    _homepageTableView  = [[PullingRefreshTableView alloc] initWithFrame:CGRectMake(0, 88+37, 320, 480-49-44-37) pullingDelegate:self];
    _homepageTableView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
    _homepageTableView.delegate=self;
    _homepageTableView.dataSource=self;
    _homepageTableView.separatorColor = [UIColor grayColor];
    _homepageTableView.backgroundColor = [UIColor clearColor];
    _homepageTableView.headerOnly=NO;
    [self.view addSubview:_homepageTableView];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark ColumnBarDataSource
- (int)numberOfTabsInColumnBar:(ColumnBar *)columnBar
{
    return [_columnBarArray count];
}

- (NSString *)columnBar:(ColumnBar *)columnBar titleForTabAtIndex:(int)index
{
    return [_columnBarArray objectAtIndex:index];
}

#pragma mark ColumnBarDelegate
- (void)columnBar:(ColumnBar *)columnBar didSelectedTabAtIndex:(int)index
{
    if (index==1)
    {
        _changeString = @"hot";
        [_dataArray removeAllObjects];
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"ios" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
            }
        }];
    }
    
    if (index==2)
    {
        _changeString = @"elite";
        [_dataArray removeAllObjects];
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"c" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
            }
        }];
    }
    
    if (index==3)
    {
        _changeString = @"activity";
        [_dataArray removeAllObjects];
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"jave" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
            }
        }];
    }
    
    if (index==4)
    {
        _changeString = @"school";
        [_dataArray removeAllObjects];
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"c++" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
            }
        }];
    }
}

#pragma mark UISearchBarDelegate
- (void)searchBarTextDidBeginEditing:(UISearchBar *)searchBar
{
    [_newsSearch setShowsCancelButton:YES];
}

- (void)searchBarCancelButtonClicked:(UISearchBar *)searchBar
{
    [_newsSearch resignFirstResponder];
    [_newsSearch setShowsCancelButton:NO];
}

- (void)searchBarSearchButtonClicked:(UISearchBar *)searchBar
{
    [_newsSearch resignFirstResponder];
    [SVProgressHUD showSuccessWithStatus:@"搜索成功"];
}

#pragma mark - 表示图
#pragma mark - UITableViewGelegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_dataArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //NSString *GroupedTableIdentifier = [NSString stringWithFormat:@"cell%d%d",indexPath.section,indexPath.row];
    static NSString *GroupedTableIdentifier = @"GroupedTableIdentifier";
    HomePageCell *cell = [tableView dequeueReusableCellWithIdentifier:GroupedTableIdentifier];
    if (cell == nil)
    {
        cell = [[HomePageCell alloc] initWithStyle:UITableViewCellStyleDefault
                                          reuseIdentifier:GroupedTableIdentifier];
        cell.selectionStyle=UITableViewCellSelectionStyleNone;
        cell.accessoryType=UITableViewCellAccessoryNone;
    }
    
    NSMutableDictionary *dic = [_dataArray objectAtIndex:indexPath.row];
    cell.unitLabel.text = [NSString stringWithFormat:@"%@",[dic valueForKey:@"publisher"]];
    cell.dataLabel.text = [NSString stringWithFormat:@"%@",[dic valueForKey:@"title"]];
    [cell.imgView setImageWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"%@",[dic valueForKey:@"image"]]] placeholderImage:nil];
    
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 70;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
}

#pragma mark PullingRefreshTableViewDelegate
- (void)pullingTableViewDidStartRefreshing:(PullingRefreshTableView *)tableView{
    self.refreshing = YES;
    [self performSelector:@selector(loadData) withObject:nil afterDelay:1.f];
}

- (NSDate *)pullingTableViewRefreshingFinishedDate{
    NSDate *date = [NSDate date];
    return date;
}

- (void)pullingTableViewDidStartLoading:(PullingRefreshTableView *)tableView{
    [self performSelector:@selector(loadData) withObject:nil afterDelay:1.f];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    [_homepageTableView tableViewDidScroll:scrollView];
}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
    [_homepageTableView tableViewDidEndDragging:scrollView];
}

- (void)loadData
{
    if (_refreshing)
    {
        _refreshing = NO;
        [_dataArray removeAllObjects];
    }
    
    if ([_changeString isEqualToString:@"hot"])
    {
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"c" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView tableViewDidFinishedLoading];
                _homepageTableView.reachedTheEnd  = NO;
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
                [_homepageTableView tableViewDidFinishedLoading];
            }
        }];
    }
    
    if ([_changeString isEqualToString:@"elite"])
    {
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"ios" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView tableViewDidFinishedLoading];
                _homepageTableView.reachedTheEnd  = NO;
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
                [_homepageTableView tableViewDidFinishedLoading];
            }
        }];
    }
    
    if ([_changeString isEqualToString:@"activity"])
    {
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"c++" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView tableViewDidFinishedLoading];
                _homepageTableView.reachedTheEnd  = NO;
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
                [_homepageTableView tableViewDidFinishedLoading];
            }
        }];
    }
    
    if ([_changeString isEqualToString:@"school"])
    {
        [EliteHuiHttp EliteHuiHttpWithURL:self.view andHUDString:@"正在加载" andHostString:@"api.douban.com/v2/book/search" andHeaderDic:nil andPathString:nil andparams:[NSDictionary dictionaryWithObject:@"jave" forKey:@"q"] andHttpMethod:@"GET" httpsYESorNO:NO withBlock:^(NSDictionary *dataDic, NSError *error) {
            if (dataDic)
            {
                NSArray *books = [dataDic valueForKey:@"books"];
                [_dataArray addObjectsFromArray:books];
                [_homepageTableView tableViewDidFinishedLoading];
                _homepageTableView.reachedTheEnd  = NO;
                [_homepageTableView reloadData];
            }
            if (error)
            {
                [SVProgressHUD showErrorWithStatus:@"网络连接出错"];
                [_homepageTableView tableViewDidFinishedLoading];
            }
        }];
    }
    
}


@end

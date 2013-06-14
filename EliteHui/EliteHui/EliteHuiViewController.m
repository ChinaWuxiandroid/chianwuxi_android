//
//  EliteHuiViewController.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "EliteHuiViewController.h"
#import "HomePageViewController.h"
#import "ContactsViewController.h"
#import "UnitViewController.h"
#import "MeViewController.h"
#import "GalleryViewController.h"

@interface EliteHuiViewController ()

@end

@implementation EliteHuiViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        //[self setNoHighlightTabBar];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self loadEliteHuiTabBar];;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark 创建TabBar
- (void)loadEliteHuiTabBar
{
    //[self.tabBar setBackgroundImage:[Util retrunImageByName:TabBar_Back_Img]];
    
    HomePageViewController *homepageViewController = [[HomePageViewController alloc] init];
    ContactsViewController *contactsViewController = [[ContactsViewController alloc] init];
    UnitViewController *unitViewController = [[UnitViewController alloc] init];
    MeViewController *meViewController = [[MeViewController alloc] init];
    GalleryViewController *galleryViewController = [[GalleryViewController alloc] init];
    
    UINavigationController *homepageNavController = [self viewControllerWithTitle:@"首页" titleAndFont:10 selectedImg:[Util retrunImageByName:HomePage_TabBarItem_selectedImg] unselectedImg:[Util retrunImageByName:HomePage_TabBarItem_unselectedImg] viewController:homepageViewController tag:1];
    [homepageNavController setNavigationBarHidden:YES];
    
    UINavigationController *contactsNavController = [self viewControllerWithTitle:@"通讯录" titleAndFont:10 selectedImg:[Util retrunImageByName:Contacts_TabBarItem_selectedImg] unselectedImg:[Util retrunImageByName:Contacts_TabBarItem_unselectedImg] viewController:contactsViewController tag:2];
    [contactsNavController setNavigationBarHidden:YES];
    
    UINavigationController *unitNavController = [self viewControllerWithTitle:@"单位" titleAndFont:10 selectedImg:[Util retrunImageByName:Unit_TabBarItem_selectedImg] unselectedImg:[Util retrunImageByName:Unit_TabBarItem_unselectedImg] viewController:unitViewController tag:3];
    [unitNavController setNavigationBarHidden:YES];
    
    
    UINavigationController *meNavController = [self viewControllerWithTitle:@"我" titleAndFont:10 selectedImg:[Util retrunImageByName:Me_TabBarItem_selectedImg] unselectedImg:[Util retrunImageByName:Me_TabBarItem_unselectedImg] viewController:meViewController tag:4];
    [meNavController setNavigationBarHidden:YES];
    
    UINavigationController *galleryNavController = [self viewControllerWithTitle:@"百宝箱" titleAndFont:10 selectedImg:[Util retrunImageByName:Gallery_TabBarItem_selectedImg] unselectedImg:[Util retrunImageByName:Gallery_TabBarItem_unselectedImg] viewController:galleryViewController tag:5];
    [galleryNavController setNavigationBarHidden:YES];
    
    self.viewControllers = [NSArray arrayWithObjects:homepageNavController,contactsNavController,unitNavController,meNavController,galleryNavController,nil];
}

/**
 *	设置视图控制器标签项标题和图像
 *	@param title TabBaritem标题
 *	@param font TabBaritem标题大小
 *	@param selectedImage 选择图片
 *	@param unselectedImage 非选择图片
 *	@param viewController 父视图
 *	@param tag 判断标签
 *	@returns controller
 */
- (UINavigationController *)viewControllerWithTitle:(NSString *)title
                                       titleAndFont:(CGFloat )font
                                        selectedImg:(UIImage *)selectedImage
                                      unselectedImg:(UIImage *)unselectedImage
                                     viewController:(UIViewController *)viewController
                                                tag:(NSInteger )tag
{
    UITabBarItem *barItem = [[UITabBarItem alloc] initWithTitle:title image:nil tag:tag];
    [barItem setFinishedSelectedImage:selectedImage
          withFinishedUnselectedImage:unselectedImage];
    [barItem setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:[UIColor whiteColor],UITextAttributeTextColor,[UIFont systemFontOfSize:font] ,UITextAttributeFont,nil] forState:UIControlStateNormal];
    viewController.tabBarItem = barItem;
    UINavigationController *controller = [[UINavigationController alloc] initWithRootViewController:viewController];
    
    return controller;
}

/*
- (void)setSelectedViewController:(UIViewController *)selectedViewController
{
    [super setSelectedViewController:selectedViewController];
    [self setNoHighlightTabBar];
}
*/

/**
 *	设置TabBar选中后不高亮显示
 */
/*
- (void)setNoHighlightTabBar

{
    int tabCount = [self.viewControllers count] > 5 ? 5 : [self.viewControllers count];
    NSArray * tabBarSubviews = [self.tabBar subviews];
    for(int i = [tabBarSubviews count] - 1; i > [tabBarSubviews count] - tabCount - 1; i--)
    {
        for(UIView * v in [[tabBarSubviews objectAtIndex:i] subviews])
        {
            if(v && [NSStringFromClass([v class]) isEqualToString:@"UITabBarSelectionIndicatorView"])
            {
                [v removeFromSuperview];
                break;
            }
        }
    }
}
*/

@end

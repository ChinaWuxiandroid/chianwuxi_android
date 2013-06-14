//
//  LogViewController.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "LogViewController.h"
#import "EliteHuiViewController.h"
#import "RegisterViewController.h"
#import "HJFiosUI.h"

@interface LogViewController ()

@end

@implementation LogViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

#pragma mark UIViewController调用方法
- (void)loadView
{
    self.view = [Util UIViewControllerAndView];
    
    [Util navbarWithView:self.view];
    
    [Util navbarWithBackBtn:self.view andTarget:nil andandAction:nil];
    
    [Util navbarWithBtn:self.view andtitlt:@"注册" andTarget:self andandAction:@selector(registerAction)];
    
    UIButton *logBtn = [HJFiosUI buttonWith:10 andOriginY:300 andSizeX:300 andSizeY:30 andButtonType:UIButtonTypeRoundedRect andButtonBackImg:nil andButtonTitle:@"登陆" andButtonTitleColor:[UIColor blackColor] andTitleFont:15 andTarget:self andandAction:@selector(logbtnAction)];
    [self.view addSubview:logBtn];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark UIButton Action
//登陆按钮
- (void)logbtnAction
{
    EliteHuiViewController *eliteHuiTabBarController = [[EliteHuiViewController alloc] init];
    [eliteHuiTabBarController setModalTransitionStyle:UIModalTransitionStyleFlipHorizontal];
    [self presentViewController:eliteHuiTabBarController animated:YES completion:nil];
}

- (void)registerAction
{
    RegisterViewController *registerViewController = [[RegisterViewController alloc] init];
    [self.navigationController pushViewController:registerViewController animated:YES];
}

@end

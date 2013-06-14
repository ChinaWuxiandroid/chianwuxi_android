//
//  UnitViewController.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "UnitViewController.h"

@interface UnitViewController ()

@end

@implementation UnitViewController

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
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

@end

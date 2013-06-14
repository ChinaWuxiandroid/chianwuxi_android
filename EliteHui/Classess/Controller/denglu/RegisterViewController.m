//
//  RegisterViewController.m
//  EliteHui
//
//  Created by hjf on 13-6-11.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "RegisterViewController.h"
#import "HJFiosUI.h"

@interface RegisterViewController ()

@end

@implementation RegisterViewController

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
    
    [Util navbarWithBackBtn:self.view andTarget:self andandAction:@selector(navbarBackAction)];
    
    NSArray *dataArr = [[NSArray alloc] initWithObjects:@"姓       名",@"单       位",@"手机号码",@"登陆密码",nil];
    
    for (int i = 0; i < [dataArr count]; i++)
    {
        UILabel *dataLabel = [HJFiosUI labelWithdefault:0 andOriginY:i * 35 + 49 andSizeX:80 andSizeY:15 andBackGoundColor:[UIColor clearColor] andTextColor:[UIColor lightGrayColor] andFont:15 andTextAlignment:NSTextAlignmentLeft];
        dataLabel.text = [NSString stringWithFormat:@"%@:",[dataArr objectAtIndex:i]];
        [self.view addSubview:dataLabel];
    }
    
    _userNameField = [HJFiosUI textFieldWithdefault:90 andOriginY:0*35+44 andSizeX:210 andSizeY:30 andTextBorderStyle:UITextBorderStyleBezel andBackGoundColor:[UIColor clearColor] andTextColor:[UIColor blackColor] andPlaceholder:@"请输入用户名" andFont:15 andSecureTextEntry:NO andClearsOnBeginEditing:YES andKeyboardType:UIKeyboardTypeDefault andTextAlignment:NSTextAlignmentLeft andDelegate:self];
    [self.view addSubview:_userNameField];
    
    _userUnitField = [HJFiosUI textFieldWithdefault:90 andOriginY:1*35+44 andSizeX:210 andSizeY:30 andTextBorderStyle:UITextBorderStyleBezel andBackGoundColor:[UIColor clearColor] andTextColor:[UIColor blackColor] andPlaceholder:@"请输入单位" andFont:15 andSecureTextEntry:NO andClearsOnBeginEditing:YES andKeyboardType:UIKeyboardTypeDefault andTextAlignment:NSTextAlignmentLeft andDelegate:self];
    [self.view addSubview:_userUnitField];
    
    _userPhoneField = [HJFiosUI textFieldWithdefault:90 andOriginY:2*35+44 andSizeX:210 andSizeY:30 andTextBorderStyle:UITextBorderStyleBezel andBackGoundColor:[UIColor clearColor] andTextColor:[UIColor blackColor] andPlaceholder:@"请输入用户手机号码" andFont:15 andSecureTextEntry:NO andClearsOnBeginEditing:YES andKeyboardType:UIKeyboardTypeNumberPad andTextAlignment:NSTextAlignmentLeft andDelegate:self];
    [self.view addSubview:_userPhoneField];
    
    _passWordField = [HJFiosUI textFieldWithdefault:90 andOriginY:3*35+44 andSizeX:210 andSizeY:30 andTextBorderStyle:UITextBorderStyleBezel andBackGoundColor:[UIColor clearColor] andTextColor:[UIColor blackColor] andPlaceholder:@"请输入密码" andFont:15 andSecureTextEntry:YES andClearsOnBeginEditing:YES andKeyboardType:UIKeyboardTypeDefault andTextAlignment:NSTextAlignmentLeft andDelegate:self];
    [self.view addSubview:_passWordField];
    
    UITapGestureRecognizer *keyMissTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(keyMissTapAction)];
    [self.view addGestureRecognizer:keyMissTap];
    
    UIButton *registerBtn = [HJFiosUI buttonWith:10 andOriginY:200 andSizeX:300 andSizeY:30 andButtonType:UIButtonTypeRoundedRect andButtonBackImg:nil andButtonTitle:@"注册" andButtonTitleColor:[UIColor blackColor] andTitleFont:15 andTarget:self andandAction:@selector(registerAction)];
    [self.view addSubview:registerBtn];
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
//导航栏返回按钮
- (void)navbarBackAction
{
    [self.navigationController popViewControllerAnimated:YES];
}

//注册按钮
- (void)registerAction
{
    if (_userPhoneField.text == nil || [_userPhoneField.text isEqualToString:@""])
    {
        [SVProgressHUD showErrorWithStatus:@"用户名不能为空!"];
    }
    else if (_userUnitField.text == nil || [_userUnitField.text isEqualToString:@""])
    {
        [SVProgressHUD showErrorWithStatus:@"单位不能为空!"];
    }
    else if ([_userPhoneField.text length]!=11)
    {
        [SVProgressHUD showErrorWithStatus:@"请输入正确的手机号码格式"];
    }
    else if (_passWordField.text == nil || [_passWordField.text isEqualToString:@""])
    {
        [SVProgressHUD showErrorWithStatus:@"密码不能为空"];
    }
    else
    {
        [SVProgressHUD showSuccessWithStatus:@"注册成功"];
    }
}

//单击事件
- (void)keyMissTapAction
{
    [_userNameField resignFirstResponder];
    [_userUnitField resignFirstResponder];
    [_userPhoneField resignFirstResponder];
    [_passWordField resignFirstResponder];
}

#pragma mark UITextFieldDelegate
-(BOOL)textFieldShouldEndEditing:(UITextField *)textField
{
    NSTimeInterval animationDuration = 0.30f;
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:animationDuration];
    CGRect rect = CGRectMake(0.0f, 0.0f, self.view.frame.size.width, self.view.frame.size.height);
    self.view.frame = rect;
    [UIView commitAnimations];
    return YES;
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    CGRect frame = textField.frame;
    int offset = frame.origin.y + 32 - (self.view.frame.size.height - 216.0);
    NSTimeInterval animationDuration = 0.30f;
    [UIView beginAnimations:@"ResizeForKeyBoard" context:nil];
    [UIView setAnimationDuration:animationDuration];
    float width = self.view.frame.size.width;
    float height = self.view.frame.size.height;
    if(offset > 0)
    {
        CGRect rect = CGRectMake(0.0f, -offset,width,height);
        self.view.frame = rect;
    }
    [UIView commitAnimations];
}

@end

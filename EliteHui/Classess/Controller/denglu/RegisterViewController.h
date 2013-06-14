//
//  RegisterViewController.h
//  EliteHui
//
//  Created by hjf on 13-6-11.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

/*===============================*/
//视图控制器 注册页面
/*===============================*/

#import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController <UITextFieldDelegate>

//用户姓名
@property (strong, nonatomic) UITextField        *userNameField;
//用户单位
@property (strong, nonatomic) UITextField        *userUnitField;
//手机号码
@property (strong, nonatomic) UITextField        *userPhoneField;
//登陆密码
@property (strong, nonatomic) UITextField        *passWordField;

@end

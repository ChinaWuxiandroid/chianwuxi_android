//
//  HJFiosUI.m
//  EliteHui
//
//  Created by hjf on 13-6-11.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "HJFiosUI.h"

@implementation HJFiosUI


#pragma mark UITextField的封装方法
/**
 *	创建UITextField
 *	@param orginx UITextField的x坐标值
 *	@param orginy UITextField的y坐标值
 *	@param sizeWidth UITextField的宽度值
 *	@param sizeHight UITextField的高度值
 *	@param BorderStyle UITextField的外观样式
 *	@param BackGroundcolor UITextField的背景颜色
 *	@param textColor UITextField的字体颜色
 *	@param placeholderStr UITextField的灰色字体
 *	@param font UITextField的文字字体大小
 *	@param YESorNO 是否非为密码显示
 *	@param NOorYES 是否再次编辑时清空输入框
 *	@param KeyboardType 键盘样式
 *	@param textAlignment 问题对齐方式
 *	@param delgate 委托
 *	@returns hjfTextField
 */
+ (UITextField *)textFieldWithdefault:(CGFloat )orginx
                           andOriginY:(CGFloat )orginy
                             andSizeX:(CGFloat )sizeWidth
                             andSizeY:(CGFloat )sizeHight
                   andTextBorderStyle:(UITextBorderStyle )BorderStyle
                    andBackGoundColor:(UIColor *)BackGroundcolor
                         andTextColor:(UIColor *)textColor
                       andPlaceholder:(NSString *)placeholderStr
                              andFont:(CGFloat )font
                   andSecureTextEntry:(BOOL )YESorNO
              andClearsOnBeginEditing:(BOOL )NOorYES
                      andKeyboardType:(UIKeyboardType )KeyboardType
                     andTextAlignment:(NSTextAlignment )textAlignment
                          andDelegate:(id)delgate
{
    UITextField *hjfTextField = [[UITextField alloc] initWithFrame:CGRectMake(orginx, orginy, sizeWidth, sizeHight)];//创建UITextField的frame值
    [hjfTextField setBorderStyle:BorderStyle];//UITextField的外观样式
    [hjfTextField setBackgroundColor:BackGroundcolor];//UITextField的背景颜色
    [hjfTextField setTextColor:textColor];//UITextField的自已颜色
    [hjfTextField setPlaceholder:placeholderStr];//UITextField的灰色文字
    [hjfTextField setFont:[UIFont systemFontOfSize:font]];//UITextField的自己大小
    [hjfTextField setSecureTextEntry:YESorNO];//UITextField是否密码显示
    [hjfTextField setAutocorrectionType:UITextAutocorrectionTypeNo];//UITextField是否自动纠错
    [hjfTextField setClearsOnBeginEditing:NOorYES];//UITextField再次编辑是否清空输入框
    [hjfTextField setKeyboardType:KeyboardType];//UITextField设置键盘样式
    [hjfTextField setAutocapitalizationType:UITextAutocapitalizationTypeNone];//UITextField首字母是否大写
    [hjfTextField setReturnKeyType:UIReturnKeyDefault];//UITextFieldreturn键变成什么键
    [hjfTextField setTextAlignment:textAlignment];//UITextField文字对齐方式
    [hjfTextField setDelegate:delgate];//委托
    
    return hjfTextField;
}

#pragma mark UILabel的封装方法
/**
 *	创建UILabel
 *	@param orginx UILabel的x坐标值
 *	@param orginy UILabel的y坐标值
 *	@param sizeWidth UILabel的宽度
 *	@param sizeHight UILabel的高度
 *	@param BackGroundcolor UILabel的背景颜色
 *	@param textColor UILabel的文字颜色
 *	@param font UILabel的文字大小
 *	@param textAlignment UILabel的文字对齐方式
 *	@returns hjfLabel
 */
+ (UILabel *)labelWithdefault:(CGFloat )orginx
                   andOriginY:(CGFloat )orginy
                     andSizeX:(CGFloat )sizeWidth
                     andSizeY:(CGFloat )sizeHight
            andBackGoundColor:(UIColor *)BackGroundcolor
                 andTextColor:(UIColor *)textColor
                      andFont:(CGFloat )font
             andTextAlignment:(NSTextAlignment )textAlignment
{
    UILabel *hjfLabel = [[UILabel alloc] initWithFrame:CGRectMake(orginx, orginy, sizeWidth, sizeHight)];
    [hjfLabel setFont:[UIFont systemFontOfSize:font]];
    [hjfLabel setBackgroundColor:BackGroundcolor];
    [hjfLabel setTextColor:textColor];
    [hjfLabel setTextAlignment:textAlignment];
    
    return hjfLabel;
}

/**
 *	创建UILabel并实现自动换行
 *	@param orginx orginx UILabel的x坐标值
 *	@param orginy orginy UILabel的y坐标值
 *	@param sizeWidth UILabel的宽度
 *	@param sizeHight UILabel的高度
 *	@param BackGroundcolor UILabel的背景颜色
 *	@param textColor UILabel的文字颜色
 *	@param font UILabel的文字大小
 *	@param textAlignment UILabel的文字对齐方式
 *	@param numberOfLines UILabel的行数多少
 *	@param lineBreakMode UILabel自动换行 和 。。。可以出现的地方
 *	@returns hjfLabel
 */
+ (UILabel *)labelWithLines:(CGFloat )orginx
                 andOriginY:(CGFloat )orginy
                   andSizeX:(CGFloat )sizeWidth
                   andSizeY:(CGFloat )sizeHight
          andBackGoundColor:(UIColor *)BackGroundcolor
               andTextColor:(UIColor *)textColor
                    andFont:(CGFloat )font
           andTextAlignment:(NSTextAlignment )textAlignment
           andNumberOfLines:(NSInteger )numberOfLines
           andLineBreakMode:(NSLineBreakMode )lineBreakMode
{
    UILabel *hjfLabel = [[UILabel alloc] initWithFrame:CGRectMake(orginx, orginy, sizeWidth, sizeHight)];
    [hjfLabel setFont:[UIFont systemFontOfSize:font]];
    [hjfLabel setBackgroundColor:BackGroundcolor];
    [hjfLabel setTextColor:textColor];
    [hjfLabel setTextAlignment:textAlignment];
    [hjfLabel setNumberOfLines:numberOfLines];
    [hjfLabel setLineBreakMode:lineBreakMode];
    
    return hjfLabel;
}

#pragma mark UIButton的封装方法
/**
 *	创建UIButton
 *	@param orginx UIButton的x坐标值
 *	@param orginy UIButton的y坐标值
 *	@param sizeWidth UIButton的宽度
 *	@param sizeHight UIButton的高度
 *	@param buttonType UIButton的样式
 *	@param buttonBackImg UIButton的背景图片
 *	@param title UIButton的标题
 *	@param titleColor UIButton的标题颜色
 *	@param targrt 调用self
 *	@param action UIButton的响应事件
 *	@returns hjfButton
 */
+ (UIButton *)buttonWith:(CGFloat )orginx
              andOriginY:(CGFloat )orginy
                andSizeX:(CGFloat )sizeWidth
                andSizeY:(CGFloat )sizeHight
           andButtonType:(UIButtonType )buttonType
        andButtonBackImg:(UIImage *)buttonBackImg
          andButtonTitle:(NSString *)title
     andButtonTitleColor:(UIColor *)titleColor
            andTitleFont:(CGFloat )font
               andTarget:(id)targrt
            andandAction:(SEL)action
{
    UIButton *hjfButton = [UIButton buttonWithType:buttonType];
    [hjfButton setFrame:CGRectMake(orginx, orginy, sizeWidth, sizeHight)];
    [hjfButton setBackgroundImage:buttonBackImg forState:UIControlStateNormal];
    [hjfButton setTitle:title forState:UIControlStateNormal];
    [hjfButton.titleLabel setFont:[UIFont systemFontOfSize:font]];
    [hjfButton setTitleColor:titleColor forState:UIControlStateNormal];
    [hjfButton addTarget:targrt action:action forControlEvents:UIControlEventTouchUpInside];
    
    return hjfButton;
}

@end

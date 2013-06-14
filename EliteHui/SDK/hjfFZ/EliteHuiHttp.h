//
//  EliteHuiHttp.h
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface EliteHuiHttp : NSObject

/**
 * view 加载HUD等待效果的父视图
 * url  需要加载的网络的请求地址
 * path 网络请求地址的后缀参数
 * updataDic 网络请求的传参
 * blok 响应的数据 dataDic 请求成功的数据 error 请求失败的数据
 */
+ (void)EliteHuiHttpWithURL:(UIView *)view andUrl:(NSString *)urlString andPath:(NSString *)path params:(NSDictionary *)updataDic withBlock:(void(^)(NSDictionary *dataDic, NSError *error))block;

/**
 *	网络加载数据
 *	@param view 加载HUD等待效果的父视图
 *	@param hudString HUD提示文字
 *	@param hostString 网络链接主网址
 *	@param headDic 头文件
 *	@param pathString 主网址后缀链接
 *	@param params 上传的请求参数
 *	@param method GET POSE PUT DELETE
 *	@param YESorNO 是否为https加密方式 YES 是的 NO 不是的
 *  @param block 返回参数 dataDic为成功返回的字典 error为网络请求错误
 */
+ (void)EliteHuiHttpWithURL:(UIView *)view
               andHUDString:(NSString *)hudString
              andHostString:(NSString *)hostString
               andHeaderDic:(NSDictionary *)headDic
              andPathString:(NSString *)pathString
                  andparams:(NSDictionary *)params
              andHttpMethod:(NSString *)method
               httpsYESorNO:(BOOL)YESorNO
                  withBlock:(void(^)(NSDictionary *dataDic, NSError *error))block;

@end

//
//  EliteHuiHttp.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

/*====================================*/
//网络请求类
//封装了常接口调用方法,网络请求应该封装在这里
/*====================================*/

#import "EliteHuiHttp.h"

@implementation EliteHuiHttp

/**
 * view 加载HUD等待效果的父视图
 * url  需要加载的网络的请求地址
 * path 网络请求地址的后缀参数
 * updataDic 网络请求的传参
 * blok 响应的数据 dataDic 请求成功的数据 error 请求失败的数据
 */
+ (void)EliteHuiHttpWithURL:(UIView *)view andUrl:(NSString *)urlString andPath:(NSString *)path params:(NSDictionary *)updataDic withBlock:(void(^)(NSDictionary *dataDic, NSError *error))block
{
    [Util showHUD:view andString:@"正在加载"];
    MKNetworkEngine *engine = [[MKNetworkEngine alloc] initWithHostName:urlString customHeaderFields:nil];
    MKNetworkOperation *op = [engine operationWithPath:path params:updataDic httpMethod:@"GET" ssl:NO];
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        [Util removeHUD];
        NSError *error = nil;
        NSDictionary *responseJSON = [NSJSONSerialization JSONObjectWithData:[operation responseData] options:kNilOptions error:&error];
        if (block)
        {
            block(responseJSON,nil);
        }
    }errorHandler:^(MKNetworkOperation *errorOp, NSError* err) {
        NSError *error = err;
        [Util removeHUD];
        if (block)
        {
            block(nil,error);
        }
    }];
    
    [engine enqueueOperation:op];
}

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
 */
+ (void)EliteHuiHttpWithURL:(UIView *)view
               andHUDString:(NSString *)hudString
              andHostString:(NSString *)hostString
               andHeaderDic:(NSDictionary *)headDic
              andPathString:(NSString *)pathString
                  andparams:(NSDictionary *)params
              andHttpMethod:(NSString *)method
               httpsYESorNO:(BOOL)YESorNO
                  withBlock:(void(^)(NSDictionary *dataDic, NSError *error))block
{
    [Util showHUD:view andString:hudString];
    MKNetworkEngine *engine = [[MKNetworkEngine alloc] initWithHostName:hostString customHeaderFields:headDic];
    MKNetworkOperation *op = [engine operationWithPath:pathString params:params httpMethod:method ssl:YESorNO];
    [op addCompletionHandler:^(MKNetworkOperation *operation) {
        [Util removeHUD];
        NSError *error = nil;
        NSDictionary *responseJSON = [NSJSONSerialization JSONObjectWithData:[operation responseData] options:kNilOptions error:&error];
        if (block)
        {
            block(responseJSON,nil);
        }
    }errorHandler:^(MKNetworkOperation *errorOp, NSError* err) {
        NSError *error = err;
        [Util removeHUD];
        if (block)
        {
            block(nil,error);
        }
    }];
    [engine enqueueOperation:op];
}

@end

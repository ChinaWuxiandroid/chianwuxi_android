//
//  HomePageCell.h
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomePageCell : UITableViewCell

//图片
@property (strong, nonatomic) UIImageView      *imgView;
//企业名称
@property (strong, nonatomic) UILabel          *unitLabel;
//内容介绍
@property (strong, nonatomic) UILabel          *dataLabel;

@end

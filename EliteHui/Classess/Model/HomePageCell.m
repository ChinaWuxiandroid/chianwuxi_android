//
//  HomePageCell.m
//  EliteHui
//
//  Created by hjf on 13-6-9.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "HomePageCell.h"

@implementation HomePageCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        _imgView = [[UIImageView alloc] initWithFrame:CGRectMake(5, 5, 80, 60)];
        _imgView.backgroundColor = [UIColor blueColor];
        [self addSubview:_imgView];
        
        _unitLabel = [[UILabel alloc] initWithFrame:CGRectMake(90, 5, 210, 15)];
        _unitLabel.backgroundColor = [UIColor clearColor];
        _unitLabel.font = [UIFont systemFontOfSize:15];
        [self addSubview:_unitLabel];
        
        _dataLabel = [[UILabel alloc] initWithFrame:CGRectMake(90, 30, 210, 30)];
        _dataLabel.backgroundColor = [UIColor clearColor];
        _dataLabel.font = [UIFont systemFontOfSize:12];
        _dataLabel.textColor = [UIColor lightGrayColor];
        _dataLabel.numberOfLines=2;
        _dataLabel.lineBreakMode=NSLineBreakByWordWrapping|NSLineBreakByTruncatingTail;
        [self addSubview:_dataLabel];
        
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];
}

@end

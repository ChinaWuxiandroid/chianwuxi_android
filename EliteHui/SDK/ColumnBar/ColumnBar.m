//
//  ColumnBar.m
//  EliteHui
//
//  Created by hjf on 13-6-13.
//  Copyright (c) 2013年 hjf. All rights reserved.
//

#import "ColumnBar.h"

@implementation ColumnBar

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
        self.userInteractionEnabled = YES;
        
        _myScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, frame.size.width, frame.size.height)];
        _myScrollView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
        _myScrollView.contentInset = UIEdgeInsetsMake(0.0f, 22.0f, 0.0f, 22.0f);
        _myScrollView.delegate = self;
        _myScrollView.showsVerticalScrollIndicator = NO;
        _myScrollView.showsHorizontalScrollIndicator = NO;
        [self addSubview:_myScrollView];
        
        _leftCapImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, _myScrollView.contentInset.left, self.frame.size.height)];
        _leftCapImageView.autoresizingMask = UIViewAutoresizingFlexibleRightMargin;
        [self addSubview:_leftCapImageView];
        
        _rightCapImageView = [[UIImageView alloc] initWithFrame:CGRectMake(self.frame.size.width-_myScrollView.contentInset.right, 0, _myScrollView.contentInset.right, self.frame.size.height)];
        _rightCapImageView.autoresizingMask = UIViewAutoresizingFlexibleLeftMargin;
        [self addSubview:_rightCapImageView];
        
        _moveImageView = [[UIImageView alloc] init];
        _moveImageView.clipsToBounds = YES;
        _moveImageView.layer.cornerRadius = 6;
        [_myScrollView addSubview:_moveImageView];
    }
    return self;
}

#pragma mark 设置 左 右 图片是否隐藏
- (void)setupCaps
{
    if(_myScrollView.contentSize.width <= _myScrollView.frame.size.width - _myScrollView.contentInset.left - _myScrollView.contentInset.right) {
		_leftCapImageView.hidden = YES;
		_rightCapImageView.hidden = YES;
	} else {
		if(_myScrollView.contentOffset.x > (-_myScrollView.contentInset.left)+10.0f) {
			_leftCapImageView.hidden = NO;
		} else {
			_leftCapImageView.hidden = YES;
		}
		
		if((_myScrollView.frame.size.width+_myScrollView.contentOffset.x)+10.0f >= _myScrollView.contentSize.width) {
			_rightCapImageView.hidden = YES;
		} else {
			_rightCapImageView.hidden = NO;
		}
	}
}

#pragma mark 初始化过后加载reloadData函数
//初始化过后加载
- (void)reloadData
{
    if(_myScrollView.subviews && _myScrollView.subviews.count > 0)
		[_myScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
	
	if(!self.dataSource)
		return;
	
	int items = [self.dataSource numberOfTabsInColumnBar:self];
	if (items == 0)
		return;
	
	int x;
	float origin_x = 0;
	for(x = 0; x < items; x++) {
		NSString *name = [self.dataSource columnBar:self titleForTabAtIndex:x];
		
		UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
		[button addTarget:self action:@selector(buttonClicked:) forControlEvents:UIControlEventTouchUpInside];
		CGSize size = [name sizeWithFont:button.titleLabel.font];
		button.frame = CGRectMake(origin_x, 0.0f, size.width, _myScrollView.frame.size.height);
		origin_x += size.width + kSpace;
        
        button.titleLabel.font = [UIFont systemFontOfSize:15];
		[button setTitle:name forState:UIControlStateNormal];
		[button setTitleColor:[UIColor colorWithRed:0 green:0 blue:0 alpha:1] forState:UIControlStateNormal];
        [button setTitleColor:[UIColor colorWithRed:1 green:1 blue:1 alpha:1] forState:UIControlStateSelected];
		[_myScrollView addSubview:button];
	}
	
	_myScrollView.contentSize = CGSizeMake(origin_x, _myScrollView.frame.size.height);
	
	[self setupCaps];
}

#pragma mark 按钮响应函数
- (void)moveToFrame:(CGRect)frame animated:(BOOL)animated
{
    NSTimeInterval duration;
    
    if (animated)
        duration = 0.5;
    else
        duration = 0;
    
    [UIView animateWithDuration:duration animations:^(void) {
        [_moveImageView removeFromSuperview];
        _moveImageView.frame = CGRectMake(frame.origin.x, 6.5, frame.size.width, 24);
        [_myScrollView addSubview:_moveImageView];
    }];
    
    [_myScrollView sendSubviewToBack:_moveImageView];
}

- (void)buttonClicked:(UIButton *)button
{
    [self moveToFrame:button.frame animated:YES];
    
    for (UIButton *btn in _myScrollView.subviews) {
        if ([btn isKindOfClass:[UIButton class]])
            btn.selected = NO;
    }
    
    button.selected = YES;
    
    _selectedIndex = [_myScrollView.subviews indexOfObject:button];
    
    if(self.delegate && [self.delegate respondsToSelector:@selector(columnBar:didSelectedTabAtIndex:)])
		[self.delegate columnBar:self didSelectedTabAtIndex:_selectedIndex];
}

- (void)selectTabAtIndex:(int)index
{
    if(!_myScrollView.subviews || _myScrollView.subviews.count < index+1)
        return;
	
    UIButton *button = (UIButton *)[_myScrollView.subviews objectAtIndex:index];
	CGRect rect = button.frame;
	rect.size.width += 25.0f;
	[_myScrollView scrollRectToVisible:rect animated:YES];
    
	[self setupCaps];
    
    [self moveToFrame:button.frame animated:NO];
    
    [self buttonClicked:button];
}

#pragma mark UIScrollViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)inScrollView
{
	[self setupCaps];
}

@end

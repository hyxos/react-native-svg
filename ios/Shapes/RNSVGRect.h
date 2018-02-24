/**
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <Foundation/Foundation.h>

#import "RNSVGPath.h"

@interface RNSVGRect : RNSVGRenderable

@property (nonatomic, strong) NSString* x;
@property (nonatomic, strong) NSString* y;
@property (nonatomic, strong) NSString* rectwidth;
@property (nonatomic, strong) NSString* rectheight;
@property (nonatomic, strong) NSString* rx;
@property (nonatomic, strong) NSString* ry;

@end

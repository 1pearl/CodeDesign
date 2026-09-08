package com.ivanzhao.design.SRP.impl;

import com.ivanzhao.design.SRP.IVideoUserService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class VipVideoUserService implements IVideoUserService {

    public void definition() {
        System.out.println("VIP用户，视频1080P蓝光");
    }

    public void advertisement() {
        System.out.println("VIP会员，视频无广告");
    }
}

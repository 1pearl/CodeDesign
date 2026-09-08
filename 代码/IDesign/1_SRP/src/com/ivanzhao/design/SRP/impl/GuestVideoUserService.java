package com.ivanzhao.design.SRP.impl;

import com.ivanzhao.design.SRP.IVideoUserService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class GuestVideoUserService implements IVideoUserService {

    public void definition() {
        System.out.println("访客用户，视频480P高清");
    }

    public void advertisement() {
        System.out.println("访客用户，视频有广告");
    }

}

package com.ivanzhao.design.impl;

import com.ivanzhao.design.IVideoService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class GuestVideoUserService implements IVideoService {


    @Override
    public void definition() {
        System.out.println("访客");
    }

    @Override
    public void advertisement() {
        System.out.println("有广告");
    }
}

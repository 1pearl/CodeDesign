package com.ivanzhao.design.impl;

import com.ivanzhao.design.IVideoService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class OrdinaryVideoUserService implements IVideoService {
    @Override
    public void definition() {
        System.out.println("普通用户");
    }

    @Override
    public void advertisement() {
        System.out.println("有广告，少一些");
    }
}

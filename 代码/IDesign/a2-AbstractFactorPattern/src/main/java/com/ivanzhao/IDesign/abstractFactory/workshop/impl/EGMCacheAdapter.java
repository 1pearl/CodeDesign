package com.ivanzhao.IDesign.abstractFactory.workshop.impl;

import com.ivanzhao.IDesign.BaseInfra.redis.cluster.EGM;
import com.ivanzhao.IDesign.abstractFactory.workshop.ICacheAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class EGMCacheAdapter implements ICacheAdapter {

    private EGM egm = new EGM();

    @Override
    public String get(String key) {
        return egm.gain(key);
    }

    @Override
    public void set(String key, String value) {
        egm.set(key,value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        egm.setEx(key,value,timeout,timeUnit);
    }

    @Override
    public void del(String key) {
        egm.delete(key);
    }
}

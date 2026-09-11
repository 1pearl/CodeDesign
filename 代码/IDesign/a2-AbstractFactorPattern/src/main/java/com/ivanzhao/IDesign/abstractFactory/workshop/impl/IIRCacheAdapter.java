package com.ivanzhao.IDesign.abstractFactory.workshop.impl;

import com.ivanzhao.IDesign.BaseInfra.redis.cluster.IIR;
import com.ivanzhao.IDesign.abstractFactory.workshop.ICacheAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class IIRCacheAdapter implements ICacheAdapter {

    private IIR iir = new IIR();

    @Override
    public String get(String key) {
        return iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        iir.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        iir.setExpire(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        iir.del(key);
    }
}

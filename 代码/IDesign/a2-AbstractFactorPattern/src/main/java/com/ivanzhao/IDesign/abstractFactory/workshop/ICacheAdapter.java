package com.ivanzhao.IDesign.abstractFactory.workshop;

import java.util.concurrent.TimeUnit;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface ICacheAdapter {

    String get(String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}

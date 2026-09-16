package com.ivanzhao.Idesign;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Singleton_06 {
    private static final AtomicReference<Singleton_06> INSTANCE = new AtomicReference<>();
    private static Singleton_06 instance;
    private Singleton_06() {}
    public static final Singleton_06 getInstance() {
        for(;;) {
            Singleton_06 instance = INSTANCE.get();
            if(instance != null) return instance;
            INSTANCE.compareAndSet(null, instance = new Singleton_06());
            return INSTANCE.get();
        }
    }
    public static void main(String[] args) {
        System.out.println(Singleton_06.getInstance());
        System.out.println(Singleton_06.getInstance());
    }
}

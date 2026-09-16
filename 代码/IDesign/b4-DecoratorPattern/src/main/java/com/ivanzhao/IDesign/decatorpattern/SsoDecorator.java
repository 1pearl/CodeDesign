package com.ivanzhao.IDesign.decatorpattern;

import com.ivanzhao.IDesign.Infra.HandlerInterceptor;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public abstract class SsoDecorator implements HandlerInterceptor {
    //包装一个原对象，在原对象的基础上增加功能。
    //装饰器必须持有被装饰对象
    HandlerInterceptor handlerInterceptor;

    public SsoDecorator(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    public boolean preHandle(String request, String response, Object handler) {
        //我这个装饰器本身不做额外处理，只负责把调用转发给里面被包装的对象。
        return handlerInterceptor.preHandle(request, response, handler);
    }

}

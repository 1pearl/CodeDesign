package com.ivanzhao.IDesign.Infra;

public interface HandlerInterceptor {

    boolean preHandle(String request, String response, Object handler);

}
package com.ivanzhao.IDesign.decatorpattern;

import com.ivanzhao.IDesign.Infra.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class LoginSsoDecorator extends SsoDecorator {

    private static Map<String, String> authMap = new ConcurrentHashMap<String, String>();

    static {
        authMap.put("huahua", "queryUserInfo");
        authMap.put("doudou", "queryUserInfo");
    }

    public LoginSsoDecorator(HandlerInterceptor handlerInterceptor) {
        super(handlerInterceptor);
    }

    public boolean preHandle(String request, String response, Object handler) {
        boolean success = super.preHandle(request, response, handler);
        if (!success) return false;
        //增加新功能
        String userId = request.substring(8);
        String method = authMap.get(userId);

        // 模拟方法校验
        return "queryUserInfo".equals(method);
    }
}

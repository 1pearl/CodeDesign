package com.ivanzhao.IDesign.PracticeDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class TestJdkAgent {

    public static void main(String[] args) {

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        MyJDKInvocationHandler handler = new MyJDKInvocationHandler(new Dog());
        Target proxyInstance = (Target)Proxy.newProxyInstance(contextClassLoader,
                new Class[]{Target.class},
                handler
                );
        proxyInstance.eat();
    }

    interface Target {
        void eat();
    }

    static class Dog implements Target {
         public void eat() {
             System.out.println("dog eating ...");
         }
    }

    static class MyJDKInvocationHandler implements InvocationHandler {

        private Target target;

        public MyJDKInvocationHandler(Target target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println( "【前置】开始执行：" + method.getName());

            //方法.invoke(类，args);
            Object res = Target.class
                    .getMethod(method.getName(), method.getParameterTypes())
                    .invoke(target, args);

            System.out.println( "【后置】执行后：" + res);

            return res;
        }
    }

}


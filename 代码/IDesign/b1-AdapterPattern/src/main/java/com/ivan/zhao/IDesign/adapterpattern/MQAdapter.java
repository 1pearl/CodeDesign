package com.ivan.zhao.IDesign.adapterpattern;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MQAdapter {

    /**
     * 适配器入口方法 ①
     * 参数：
     * strJson：MQ 消息中的 JSON 字符串
     * link：字段映射关系
     * 作用：
     * 先把 JSON 字符串转换成 Map，
     * 然后继续调用下面的 filter(Map, link) 方法完成真正的适配。
     * 为什么这样做？
     * 因为 MQ 传过来的消息可能是不同系统定义的 JSON，
     * 我们先把 JSON 转成通用的 Map，
     * 后面统一按照 Map 来处理。
     */
    public static OrderUnifiInfor filter(String strJson, Map<String, String> link) throws Exception {

        // JSON.parseObject：
        // 把 JSON 字符串转换成 Map 对象
        // 例如：
        // strJson =
        // {"userId":"1001","orderId":"ORD001","time":"2026-09-12"}
        // 转换后大致相当于：
        // {
        //     "userId" -> "1001",
        //     "orderId" -> "ORD001",
        //     "time"   -> "2026-09-12"
        // }
        Map obj = JSON.parseObject(strJson, Map.class);

        // 调用下面真正负责“字段转换”的 filter 方法
        // 这里实际上完成了：
        // JSON字符串
        //     ↓
        // Map
        //     ↓
        // OrderUnifiInfor
        // 所以这个方法可以理解成一个入口/转换桥梁。
        return filter(obj, link);
    }


    /**
     * 适配器真正执行转换的方法 ②
     * obj：
     * 已经转换成 Map 的 MQ 消息
     * link：
     * “目标对象字段名” 和 “MQ原始字段名”之间的映射关系
     * 例如：
     * link.put("userId", "user_id");
     * link.put("bizId", "order_id");
     * 表示：
     * MQ中的 user_id  → OrderUnifiInfor中的 userId
     * MQ中的 order_id → OrderUnifiInfor中的 bizId
     */
    public static OrderUnifiInfor filter(Map obj, Map<String, String> link) throws Exception {

        // 创建一个统一的 MQ 消息对象
        // 最终所有不同格式的 MQ 消息，
        // 都会被转换成这个统一的数据结构。
        OrderUnifiInfor orderUnifiInfor = new OrderUnifiInfor();

        // 遍历 link 中配置的所有字段
        // 假设：
        // link = {
        //     "userId" -> "user_id",
        //     "bizId"  -> "order_id",
        //     "desc"   -> "message"
        // }
        // 那么 key 依次就是：
        // userId
        // bizId
        // desc
        for (String key : link.keySet()) {
            // 根据 link 中的映射关系，
            // 从 MQ 的 Map 中取出真正的数据。
            // link.get(key)：
            // 获取 MQ 原始字段名
            // obj.get(...)：
            // 根据这个原始字段名，从 MQ 数据中取值。
            // 举例：
            // key = "userId"
            // link.get("userId") = "user_id"
            // 那么：
            // obj.get("user_id")
            // 就是在 MQ 消息中取 user_id 的值。
            Object val = obj.get(link.get(key));

            /*
             * 接下来这一大段是 Java 反射。
             * 目的：
             * 根据字符串形式的字段名，
             * 自动找到 OrderUnifiInfor 对应的 setter 方法，
             * 然后调用这个 setter。
             * 例如：
             * key = "userId"
             * 我们最终希望调用：
             * orderUnifiInfor.setUserId(val.toString());
             * 但是这里没有直接写死 setUserId()，
             * 而是通过反射动态找到它。
             */


            // key.substring(0, 1)
            // 获取 key 的第一个字符
            // 例如：
            // "userId" → "u"
            // .toUpperCase()
            // 转成大写：
            // "u" → "U"
            // key.substring(1)
            // 获取第一个字符之后的部分：
            // "userId" → "serId"
            // 拼接：
            // "set" + "U" + "serId"
            // 最终得到：
            // "setUserId"
            // 也就是说，这行代码根据字段名自动生成 setter 方法名。
            // userId → setUserId
            // bizId  → setBizId
            // desc   → setDesc
            String methodName =
                    "set"
                            + key.substring(0, 1).toUpperCase()
                            + key.substring(1);
            // OrderUnifiInfor.class
            // 表示获取 OrderUnifiInfor 这个 Class 对象。
            // 可以理解成：
            // “我要在 OrderUnifiInfor 这个类中找方法。”
            // getMethod(...)
            // 根据：
            // 1. 方法名
            // 2. 参数类型
            // 查找对应的 public 方法。
            // 例如：
            // methodName = "setUserId"
            // String.class = String 类型参数
            // 那么这里最终找到的就是：
            // public void setUserId(String userId)
            // 返回值是一个 Method 对象，
            // 代表这个 setter 方法。
            Method method = OrderUnifiInfor.class.getMethod(
                    methodName,
                    String.class
            );


            // invoke()：真正调用刚才找到的方法。
            // 第一个参数：
            // orderUnifiInfor
            // 表示：
            // “在哪个对象上调用这个方法？”
            // 第二个参数：
            // val.toString()
            // 表示：
            // “给这个 setter 传什么参数？”
            // 所以：
            // method.invoke(orderUnifiInfor, val.toString());
            // 如果 method 是 setUserId，
            // 那么实际上相当于：
            // orderUnifiInfor.setUserId(val.toString());
            // 如果 method 是 setBizId，
            // 那么实际上相当于：
            // orderUnifiInfor.setBizId(val.toString());
            // 也就是说：
            // 这里用反射，把原本需要写死的 setter 调用变成了动态调用。
            method.invoke(orderUnifiInfor, val.toString());
        }


        // 所有字段转换完成后，
        // 返回已经被填充好的统一 MQ 消息对象。
        return orderUnifiInfor;
    }


}
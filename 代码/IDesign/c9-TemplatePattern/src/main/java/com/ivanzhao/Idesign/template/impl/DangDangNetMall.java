package com.ivanzhao.Idesign.template.impl;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Idesign.template.HttpClient;
import com.ivanzhao.Idesign.template.NetMall;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class DangDangNetMall extends NetMall {

    public DangDangNetMall(String uid, String uPwd) {
        super(uid, uPwd);
    }

    @Override
    protected Boolean login(String uId, String uPwd) {
        logger.info("模拟当当用户登录 uId：{} uPwd：{}", uId, uPwd);
        return true;
    }

    @Override
    protected Map<String, String> reptile(String skuUrl) {
        // 1. 发起 HTTP GET 请求：通过封装好的 HttpClient 工具类请求商品链接 skuUrl，获取目标网页的完整 HTML 源码字符串
        // ============================================================
        // 1. 根据商品 URL 发起 HTTP GET 请求
        // ============================================================
        //
        // skuUrl 是商品页面的 URL，例如：
        // https://xxx.com/product/123
        //
        // HttpClient.doGet(skuUrl)
        // 相当于：
        //   访问这个 URL
        //   ↓
        //   获取服务器返回的网页内容
        //   ↓
        //   将网页内容以 String 的形式返回
        //
        // 假设服务器返回：
        //
        // <html>
        //     <head>
        //         <title>Java编程思想</title>
        //     </head>
        //     <body>...</body>
        // </html>
        //
        // 那么 str 就是整个 HTML 字符串。
        String str = HttpClient.doGet(skuUrl);
        // 2. 编译正则表达式：
        //    - (?<=title\>)：正向后行断言（Lookbehind），匹配以 "title>" 结尾的位置，但不包含 "title>" 本身
        //    - .*：匹配任意字符（贪婪模式，匹配标题内容）
        //    - (?=</title)：正向先行断言（Lookahead），匹配以 "</title" 开头的位置，但不包含 "</title" 本身
        //    整体作用：精确提取 <title>...</title> 标签包裹的中间文本内容
        Pattern p9 = Pattern.compile("(?<=title\\>).*(?=</title)");
        // 3. 创建正则匹配器：将编译好的正则规则应用到前面获取的 HTML 字符串 str 上
        Matcher m9 = p9.matcher(str);
        // 4. 初始化结果容器：使用线程安全的 ConcurrentHashMap 保存解析后的数据（键值对）
        Map<String, String> map = new ConcurrentHashMap();
        // 5. 查找并提取匹配结果：
        //    - m9.find()：扫描输入序列中是否存在下一个匹配该模式的子序列
        //    - m9.group()：返回当前匹配到的子串（即页面标题文本，作为商品名称存入 map）
        if(m9.find()) {
            map.put("name", m9.group());
        }
        // 6. 模拟商品价格：这里没有真正从网页提取价格，而是写死了一个假数据 "300"
        map.put("price", "300");
        // 7. 打印日志：使用占位符 {} 记录爬取结果（商品名、价格、商品链接）
        logger.info("模拟当当商品爬虫解析：{} | {} 元 {}", map.get("name"), map.get("price"), skuUrl);
        // 8. 返回包含商品信息的 Map 集合
        return map;
    }

    @Override
    protected String createBase64(Map<String, String> goodsInfo) {
        // 1. 打印日志：提示当前流程正在执行模拟生成海报的操作
        logger.info("模拟生成当当商品base64海报");

        // 2. 链式调用完成序列化与 Base64 编码：
        //    步骤拆解：
        //    a. JSON.toJSONString(goodsInfo)：
        //       使用 Fastjson / Fastjson2 工具类，将商品属性 Map 序列化为一个标准的 JSON 字符串
        //       例如：{"name":"Java核心技术","price":"300"}
        //    b. .getBytes()：
        //       将上述 JSON 字符串转换为底层二进制字节数组 byte[]（按系统默认字符集，如 UTF-8）
        //    c. Base64.getEncoder()：
        //       获取 Java 标准库 java.util.Base64 提供的基本型编码器（RFC 4648 标准）
        //    d. .encodeToString(...)：
        //       将字节数组转换为纯文本形式的 Base64 编码字符串，通常前端可直接用于传输或作为 Mock 数据展示
        return Base64.getEncoder()
                .encodeToString(JSON.toJSONString(goodsInfo).getBytes());
    }
}

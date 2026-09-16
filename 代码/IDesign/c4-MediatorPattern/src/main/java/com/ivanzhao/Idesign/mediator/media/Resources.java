package com.ivanzhao.Idesign.mediator.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 资源加载辅助工具类
 * <p>
 * 模拟 MyBatis 的 Resources 工具，通过类加载器（ClassLoader）定位并读取 Classpath 下的配置文件（如 XML 配置文件和 Mapper 文件），
 * 为中介者环境的构建（SqlSessionFactoryBuilder）提供字符输入流（Reader）。
 */
public class Resources {

    /**
     * 将 Classpath 路径下的资源文件转换为 Reader 字符流
     *
     * @param resource 资源文件相对路径（如 "mybatis-config-datasource.xml"）
     * @return 字符输入流 Reader
     * @throws IOException 当文件未找到或读取失败时抛出
     */
    public static Reader getResourceAsReader(String resource) throws IOException {
        return new InputStreamReader(getResourceAsStream(resource));
    }

    /**
     * 通过双亲及上下文类加载器尝试查找并获取资源的输入字节流
     *
     * @param resource 资源路径
     * @return 字节输入流 InputStream
     * @throws IOException 当所有类加载器都未能加载到该资源时抛出
     */
    private static InputStream getResourceAsStream(String resource) throws IOException {
        ClassLoader[] classLoaders = getClassLoaders();
        for (ClassLoader classLoader : classLoaders) {
            InputStream inputStream = classLoader.getResourceAsStream(resource);
            if (null != inputStream) {
                return inputStream;
            }
        }
        throw new IOException("Could not find resource " + resource);
    }

    /**
     * 获取类加载器数组（包含系统类加载器和当前线程上下文类加载器）
     *
     * @return 类加载器数组
     */
    private static ClassLoader[] getClassLoaders() {
        return new ClassLoader[]{
                ClassLoader.getSystemClassLoader(),
                Thread.currentThread().getContextClassLoader()};
    }

}
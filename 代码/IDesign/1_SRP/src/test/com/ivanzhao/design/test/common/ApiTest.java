package test.com.ivanzhao.design.test.common;

import com.ivanzhao.design.common.VideoUserService;

public class ApiTest {

    public static void main(String[] args) {
        VideoUserService service = new VideoUserService();
        service.serveGrade("VIP用户");
        service.serveGrade("普通用户");
        service.serveGrade("访客用户");
    }

}
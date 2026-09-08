package test.com.ivanzhao.design.test.SRP;

import com.ivanzhao.design.SRP.IVideoUserService;
import com.ivanzhao.design.SRP.impl.GuestVideoUserService;

public class ApiTest {

    public static void main(String[] args) {
        IVideoUserService guest = new GuestVideoUserService();
        guest.advertisement();
        guest.definition();
        // 其他两个，自己补充
    }

}
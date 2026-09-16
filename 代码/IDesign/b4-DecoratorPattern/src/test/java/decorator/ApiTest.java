package decorator;

import com.ivanzhao.IDesign.Infra.SsoInterceptor;
import com.ivanzhao.IDesign.decatorpattern.LoginSsoDecorator;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_LoginSsoDecorator() {
        LoginSsoDecorator ssoDecorator = new LoginSsoDecorator(new SsoInterceptor());
        String request = "1successhuahua";
        boolean success = ssoDecorator.preHandle(request,"ewcdwt40liuliu","t");
        System.out.println("登录验证" + request + (success ? "放行" : "拦截"));

    }

}
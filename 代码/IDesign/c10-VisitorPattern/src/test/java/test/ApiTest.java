package test;

import com.ivanzhao.Idesign.DataView;
import com.ivanzhao.Idesign.visitor.impl.Parent;
import com.ivanzhao.Idesign.visitor.impl.Principal;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_show(){
        DataView dataView = new DataView();

        logger.info("\r\n家长视角访问：");
        dataView.show(new Parent());

        logger.info("\r\n校长视角访问：");
        dataView.show(new Principal());
    }

}
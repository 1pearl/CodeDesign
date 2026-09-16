package com.ivanzhao.IDesign.test.builderPattern;

import com.ivanzhao.IDesign.builderpattern.Builder;
import org.junit.Test;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ApiTest {

    @Test
    public void test_BuilderPattern() {
        Builder builder = new Builder();
        System.out.println(builder.levelOne(132.52D).getDetail());
        System.out.println(builder.levelTwo(98.52D).getDetail());
        System.out.println(builder.levelThree(85.52D).getDetail());
    }
}

package com.ivanzhao.design.test;

import main.java.com.ivanzhao.design.ICalculationArea;
import main.java.com.ivanzhao.design.impl.CalculationAreaExt;
import org.junit.Test;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class APITest {

    @Test
    public void test_CalculationAreaExt(){
        ICalculationArea area = new CalculationAreaExt();
        double circular = area.circular(10);
        System.out.println(circular);
    }
}

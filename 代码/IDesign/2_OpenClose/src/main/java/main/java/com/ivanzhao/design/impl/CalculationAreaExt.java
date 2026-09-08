package main.java.com.ivanzhao.design.impl;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class CalculationAreaExt extends CalculationArea {
    //但对对圆形的精度进行计算，不破坏原本类的功能完整性，针对性进行修改
    private final static double π = 3.141592653D;

    @Override
    public double circular(double r) {
        return π * r * r;
    }
}

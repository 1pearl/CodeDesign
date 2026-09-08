package main.java.com.ivanzhao.design.impl;

import main.java.com.ivanzhao.design.ICalculationArea;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class CalculationArea implements ICalculationArea {
    private final static double π = 3.14D;

    public double rectangle(double x, double y) {
        return x * y;
    }

    public double triangle(double x, double y, double z) {
        double p = (x + y + z) / 2;
        return Math.sqrt(p * (p - x) * (p - y) * (p - z));
    }

    public double circular(double r) {
        return π * r * r;
    }
}

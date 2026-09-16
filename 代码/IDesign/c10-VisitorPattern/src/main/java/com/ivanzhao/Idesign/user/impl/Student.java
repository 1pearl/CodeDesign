package com.ivanzhao.Idesign.user.impl;

import com.ivanzhao.Idesign.user.User;
import com.ivanzhao.Idesign.visitor.Visitor;

import java.util.Random;

// 学生
public class Student extends User {

    public Student(String name, String identity, String clazz) {
        super(name, identity, clazz);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int ranking() {
        return (int) (Math.random() * 100);
    }

    public int count() {
        return 105 - new Random().nextInt(10);
    }

}
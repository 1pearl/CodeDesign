package com.ivanzhao.Idesign;

import com.ivanzhao.Idesign.user.User;
import com.ivanzhao.Idesign.user.impl.Student;
import com.ivanzhao.Idesign.user.impl.Teacher;
import com.ivanzhao.Idesign.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class DataView {

    List<User> userList = new ArrayList<User>();

    public DataView() {
        userList.add(new Student("谢飞机", "重点班", "一年一班"));
        userList.add(new Student("windy", "重点班", "一年一班"));
        userList.add(new Student("大毛", "普通班", "二年三班"));
        userList.add(new Student("Shing", "普通班", "三年四班"));
        userList.add(new Teacher("BK", "特级教师", "一年一班"));
        userList.add(new Teacher("娜娜Goddess", "特级教师", "一年一班"));
        userList.add(new Teacher("dangdang", "普通教师", "二年三班"));
        userList.add(new Teacher("泽东", "实习教师", "三年四班"));
    }

    public void show(Visitor visitor) {
        for (User user : userList) {
            user.accept(visitor);
        }
    }

}

package com.ivanzhao.Idesign.visitor;

import com.ivanzhao.Idesign.user.impl.Student;
import com.ivanzhao.Idesign.user.impl.Teacher;

public interface Visitor {

    // 访问学生信息
    void visit(Student student);

    // 访问老师信息
    void visit(Teacher teacher);

}
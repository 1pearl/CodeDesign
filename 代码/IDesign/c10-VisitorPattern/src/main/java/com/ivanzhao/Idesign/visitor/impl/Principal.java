package com.ivanzhao.Idesign.visitor.impl;

import com.ivanzhao.Idesign.user.impl.Student;
import com.ivanzhao.Idesign.user.impl.Teacher;
import com.ivanzhao.Idesign.visitor.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Principal implements Visitor {

    private Logger logger = LoggerFactory.getLogger(Principal.class);


    @Override
    public void visit(Student student) {
        logger.info("学生信息 班级：{} 人数：{}", student.clazz, student.count());
    }

    @Override
    public void visit(Teacher teacher) {
        logger.info("学生信息 姓名：{} 班级：{} 升学率：{}", teacher.name, teacher.clazz, teacher.entranceRatio());
    }
}

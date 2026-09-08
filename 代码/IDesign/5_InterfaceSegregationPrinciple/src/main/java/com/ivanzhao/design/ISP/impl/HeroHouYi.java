package com.ivanzhao.design.ISP.impl;

import com.ivanzhao.design.ISP.ISkillArchery;
import com.ivanzhao.design.ISP.ISkillInvisible;
import com.ivanzhao.design.ISP.ISkillSilent;

public class HeroHouYi implements ISkillArchery, ISkillInvisible, ISkillSilent {

    @Override
    public void doArchery() {
        System.out.println("后裔的灼日之矢");
    }

    @Override
    public void doInvisible() {
        System.out.println("后裔的隐身技能");
    }

    @Override
    public void doSilent() {
        System.out.println("后裔的沉默技能");
    }

}
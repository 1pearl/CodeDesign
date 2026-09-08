package com.ivanzhao.test.common;

import com.ivanzhao.design.Common.HeroHouYi;
import com.ivanzhao.design.Common.HeroLianPo;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_ISkill(){
        // 后裔
        HeroHouYi heroHouYi = new HeroHouYi();
        heroHouYi.doArchery();

        // 廉颇
        HeroLianPo heroLianPo = new HeroLianPo();
        heroLianPo.doInvisible();
    }

}
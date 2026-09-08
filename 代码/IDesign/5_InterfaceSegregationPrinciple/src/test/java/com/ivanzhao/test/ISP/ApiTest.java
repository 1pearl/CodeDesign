package com.ivanzhao.test.ISP;

import com.ivanzhao.design.ISP.impl.HeroHouYi;
import com.ivanzhao.design.ISP.impl.HeroLianPo;
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
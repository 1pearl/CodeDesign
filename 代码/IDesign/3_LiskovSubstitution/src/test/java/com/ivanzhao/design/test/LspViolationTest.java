package com.ivanzhao.design.test;

import com.ivanzhao.design.CashCard;
import com.ivanzhao.design.NoWithdrawalCreditCard;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * 用于观察里氏替换原则的反例。
 */
public class LspViolationTest {

    @Test(expected = UnsupportedOperationException.class)
    public void noWithdrawalCreditCardCannotReplaceCashCard() {
        // 调用方只依赖 CashCard，并合理地认为它支持 withdrawal。
        CashCard card = new NoWithdrawalCreditCard("6214567800989876", "2022-03-05");

        // 编译通过，但运行时失败：子类破坏了父类的行为约定。
        card.withdrawal("100002", new BigDecimal("100"));
    }
}

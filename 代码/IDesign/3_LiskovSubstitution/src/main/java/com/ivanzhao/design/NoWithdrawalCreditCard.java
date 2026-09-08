package com.ivanzhao.design;

import java.math.BigDecimal;

/**
 * 反例：错误地把“不能提现的信用卡”设计成 CashCard 的子类。
 *
 * @author Ivan Zhao
 * @version 1.0
 */
public class NoWithdrawalCreditCard extends CashCard {

    public NoWithdrawalCreditCard(String cardNo, String cardDate) {
        super(cardNo, cardDate);
    }

    /**
     * 父类 CashCard 承诺可以提现；该子类却取消此能力，故违反里氏替换原则。
     */
    @Override
    public String withdrawal(String orderId, BigDecimal amount) {
        throw new UnsupportedOperationException("该信用卡不支持提现");
    }
}

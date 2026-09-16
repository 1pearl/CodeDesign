package com.ivan.zhao.IDesign.adapterpattern;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//就像我们前⾯提到随着业务的发展，营销活动本身要修改，不能只是接了MQ就发奖励。因为此时已经
//拉新的越来越多了，需要做⼀些限制。
//因为增加了只有⾸单⽤户才给奖励，也就是你⼀年或者新⼈或者⼀个⽉的第⼀单才给你奖励，⽽不是你
//之前每⼀次下单都给奖励。
//那么就需要对此种⽅式进⾏限制，⽽此时MQ中并没有判断⾸单的属性。只能通过接⼝进⾏查询，⽽拿
//到的接⼝如下；
public interface OrderAdapterService {

    boolean isFirst(String uId);

}

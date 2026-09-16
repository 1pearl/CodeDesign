package com.ivanzhao.Idesign.My.MyMediator;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class OrderMediator implements IMediator{

    private Order order;
    private Inventory inventory;
    private Payment payment;
    private Coupon coupon;

    public OrderMediator(Order order, Inventory inventory, Payment payment, Coupon coupon) {
        this.order = order;
        this.inventory = inventory;
        this.payment = payment;
        this.coupon = coupon;
    }

    @Override
    public void handle(String event) {

        if(event.equals("createOrder")) {
            //1.检查库存
            boolean stockEnough = inventory.checkStock();

            if(!stockEnough) {
                System.out.println("库存不足，订单创建失败");
                return;
            }

            //2.扣减库存
            inventory.deductStock();
            //3.支付
            boolean paySuccess = payment.pay();
            if(!paySuccess) {
                System.out.println("支付失败，订单创建失败！");
            }
            //4.使用优惠券
            coupon.use();
            //5.创建订单
            order.create();

            System.out.println("订单创建完成！");
        }
    }
}

package web.dao;

import web.model.po.OrderItem;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface OrderItemDao {

    /**
     * 添加一个订单里面购买的一项菜品信息
     *
     * @param orderItem 一个订单的一个菜品信息
     * @return
     */
    MyMessage addOrderItem(OrderItem orderItem);

    /**
     * 得到一个订单里面菜品信息的列表
     *
     * @param orderId 订单id
     * @return
     */
    List<OrderItem> getOrderItemOfOrder(int orderId);
}

package web.biz;

import web.model.Order;

import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public interface OrderService {

    /**
     * 记录订单
     *
     * @param order
     * @return
     */
    boolean addOrder(Order order);

    /**
     * 获取个人订单列表
     *
     * @param userId
     * @return
     */
    List<Order> getOrderList(int userId);

    /**
     * 获取订单内容
     *
     * @param  orderId
     * @return
     */
    Order getOrder(int orderId);

}

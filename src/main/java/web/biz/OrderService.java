package web.biz;

import web.model.Order;
import web.tools.MyMessage;

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
     * @param orderId
     * @return
     */
    Order getOrder(int orderId);

    /**
     * 评价菜品
     *
     * @param userID  用户ID
     * @param foodID  食物ID
     * @param comment 评论 1好/0不好
     * @return MyMessage
     */
    MyMessage comment(int userID, int foodID, int comment);

}

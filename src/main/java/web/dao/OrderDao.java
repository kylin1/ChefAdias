package web.dao;

import web.model.po.Order;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface OrderDao {

    /**
     * 新建订单
     *
     * @param order
     * @return
     */
    MyMessage addOrder(Order order);

    /**
     * 获取用户订单列表
     *
     * @param userID
     * @return
     */
    List<Order> getOrderOfUser(int userID);

    /**
     * 按天获取订单历史
     *
     * @param startDate 日期 YYYYMMdd
     * @param endDate 日期 YYYYMMdd
     * @return
     */
    List<Order> getOrderInDay(String startDate,String endDate);

    /**
     * 获取订单信息
     *
     * @param id
     * @return
     */
    Order getOrder(int id);

    /**
     * 设置订单的到达状态
     *
     * @param orderId 订单ID
     * @param isArrived 是否到达
     * @return
     */
    MyMessage setState(int orderId, boolean isArrived);

}

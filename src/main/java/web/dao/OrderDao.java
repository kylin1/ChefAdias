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
     * 按天获取订单历史,日期的开闭是[startDate, endDate)
     *
     * 如果想获取一天的数据,startDate为目标日期,endDate为后一天
     * 例如获取2016-12-07的数据,传入参数为2016-12-07,2016-12-08
     *
     * @param startDate 日期 YYYY-MM-dd
     * @param endDate 日期 YYYY-MM-dd
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

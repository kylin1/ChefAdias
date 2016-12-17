package web.dao;

import web.model.po.CustomOrder;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 16/12/2016.
 * All rights reserved.
 */
public interface CustomOrderDao {

    /**
     * 记录用户自定义菜单的订单
     *
     * @param order 自定义订单
     * @return
     */
    MyMessage addOrder(CustomOrder order);

    /**
     * 得到一个用户的订单信息
     *
     * @param menuId 用户ID
     * @return 自定义菜单的订单
     */
    List<CustomOrder> getOrderOfMenu(int menuId);

    /**
     * 按天获取指定用户的订单历史
     *
     * @param startDate 日期 YYYY-MM-dd
     * @param endDate 日期 YYYY-MM-dd
     * @return
     */
    List<CustomOrder> getOrderInDay(int menuId, String startDate,String endDate);


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
    List<CustomOrder> getOrderInDay(String startDate,String endDate);

    /**
     * 获取订单信息
     *
     * @param id
     * @return
     */
    CustomOrder getOrder(int id);

    /**
     * 设置订单的到达状态
     *
     * @param orderId 订单ID
     * @param isArrived 是否到达
     * @return
     */
    MyMessage setState(int orderId, boolean isArrived);

}

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
     * @param userId 用户ID
     * @return 自定义菜单的订单
     */
    List<CustomOrder> getOrderOfUser(int userId);

}

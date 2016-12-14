package web.biz;

import web.model.po.Order;
import web.model.vo.AddOrderVO;
import web.model.vo.OrderItemVO;
import web.model.vo.UserOrderItemVO;
import web.model.vo.UserOrderVO;
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
    MyMessage addOrder(AddOrderVO order);

    /**
     * 获取个人订单列表
     *
     * @param userId
     * @return
     */
    List<UserOrderItemVO> getOrderList(int userId);

    /**
     * 获取订单内容
     *
     * @param orderId
     * @return
     */
    UserOrderVO getOrder(int orderId);

    /**
     * 评价菜品
     *
     * @param foodID  食物ID
     * @param comment 评论 1好/0不好
     * @return MyMessage
     */
    MyMessage comment(int foodID, int comment);

    /**
     * 自定义菜单下单
     *
     * @param userID
     * @param mmenuID
     * @return
     */
    MyMessage addMOrder(int userID, int mmenuID);
}

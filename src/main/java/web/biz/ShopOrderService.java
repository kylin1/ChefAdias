package web.biz;

import web.model.ShopOrderItemVO;
import web.model.ShopOrderVO;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
public interface ShopOrderService {

    /**
     * 获取订单详情
     *
     * @param orderID 订单ID
     * @return 用于展示在界面的订单详情
     */
    ShopOrderVO getOrder(int orderID);

    /**
     * 按天获取订单列表
     *
     * @param date 日期YYYYMMdd
     * @return 给商户看的订单列表
     */
    List<ShopOrderItemVO> getOrderList(String date);

    /**
     * 设置状态
     *
     * @param orderID 订单ID
     * @param state   订单状态
     * @return MyMessage
     */
    MyMessage setState(int orderID, int state);
}

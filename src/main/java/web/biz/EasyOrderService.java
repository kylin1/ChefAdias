package web.biz;

import web.model.EasyOrder;
import web.model.exceptions.NotFoundException;
import web.tools.MyMessage;

/**
 * Created by Alan on 2016/12/5.
 */
public interface EasyOrderService {

    /**
     * 获取easy order
     *
     * @param userID 用户ID
     * @return EasyOrder
     * @throws NotFoundException 用户ID不存在
     */
    EasyOrder getEasyOrder(int userID) throws NotFoundException;

    /**
     * 添加easy order
     * @param userID     
     * @param orderID
     * @return
     * @throws NotFoundException
     */
    MyMessage addEasyOrder(int userID, int orderID) throws NotFoundException;

}

package web.dao;

import web.model.EasyOrder;
import web.tools.MyMessage;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public interface EasyOrderDao {

    /**
     * 得到一个用户的EasyOrder信息
     *
     * @param userId
     * @return
     */
    EasyOrder getEasyOrderOfUser(int userId);

    /**
     * new EasyOrder
     *
     * @param easyOrder
     * @return
     */
    MyMessage addEasyOrder(EasyOrder easyOrder);

}

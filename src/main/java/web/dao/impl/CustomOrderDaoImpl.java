package web.dao.impl;

import web.dao.CustomOrderDao;
import web.model.po.CustomOrder;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 16/12/2016.
 * All rights reserved.
 */
public class CustomOrderDaoImpl implements CustomOrderDao {

    @Override
    public MyMessage addOrder(CustomOrder order) {
        return null;
    }

    @Override
    public List<CustomOrder> getOrderOfUser(int userId) {
        return null;
    }

}

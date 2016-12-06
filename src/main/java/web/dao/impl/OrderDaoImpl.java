package web.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.OrderDao;
import web.model.po.Order;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Repository("orderDao")
@Transactional
public class OrderDaoImpl implements OrderDao {

    @Override
    public MyMessage addOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> getOrderOfUser(int userID) {
        return null;
    }

    @Override
    public Order getOrder(int id) {
        return null;
    }

    @Override
    public MyMessage setState(int orderId, boolean isArrived) {
        return null;
    }
}

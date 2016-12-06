package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.OrderService;
import web.dao.OrderDao;
import web.model.Order;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Service
public class OrderImpl implements OrderService {

    private final OrderDao dao;

    @Autowired
    public OrderImpl(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean addOrder(Order order) {
        MyMessage myMessage = this.dao.addOrder(order);
        return myMessage.isSuccess();
    }

    @Override
    public List<Order> getOrderList(int userId) {
        return this.dao.getOrderOfUser(userId);
    }

    @Override
    public Order getOrder(int orderId) {
        return this.dao.getOrder(orderId);
    }

    @Override
    public MyMessage comment(int userID, int foodID, int comment) {
        return null;
    }
}

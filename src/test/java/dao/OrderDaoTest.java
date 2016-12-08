package dao;

import org.junit.Test;
import web.dao.impl.OrderDaoImpl;
import web.model.po.Order;
import web.tools.CheckClass;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class OrderDaoTest {

//    @Test
    public void getOrderInDay() throws ClassNotFoundException {
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.getOrderInDay(2,"2016-12-06", "2016-12-07");
        for (Order order : list) {
            CheckClass.checkObject("Order", order);
        }
    }

//    @Test
    public void getOrderOfUser() throws ClassNotFoundException {
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.getOrderOfUser(1);
        for (Order order : list) {
            CheckClass.checkObject("Order", order);
        }
    }

    @Test
    public void getOrder() throws ClassNotFoundException {
        OrderDaoImpl dao = new OrderDaoImpl();
        Order order = dao.getOrder(1);
        CheckClass.checkObject("Order", order);
    }


//    @Test
    public void add() throws ClassNotFoundException {
        OrderDaoImpl dao = new OrderDaoImpl();
        Order order = new Order();
        order.setUser_id(1);
        order.setCreate_time(new Date());

        order.setPrice(new BigDecimal("33.8"));

        order.setIs_finish(1);
        order.setTicket_info(1);
        order.setBowl_info(1);
        order.setPay_type(1);
        dao.addOrder(order);
    }

}

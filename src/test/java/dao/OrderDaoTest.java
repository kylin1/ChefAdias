package dao;

import org.junit.Test;
import web.dao.impl.OrderDaoImpl;
import web.model.po.Order;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class OrderDaoTest {

    @Test
    public void test() throws ClassNotFoundException {
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.getOrderInDay("2016-12-06","2016-12-07");
        for(Order order:list){
            CheckClass.checkObject("Order",order);
        }
    }

}

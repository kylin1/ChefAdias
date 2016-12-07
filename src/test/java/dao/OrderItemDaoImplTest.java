package dao;

import web.dao.impl.OrderItemDaoImpl;
import web.model.vo.OrderItem;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public class OrderItemDaoImplTest {

//    @Test
    public void testAll() throws ClassNotFoundException {
        OrderItemDaoImpl itemDao = new OrderItemDaoImpl();
        List<OrderItem> list = itemDao.getOrderItemOfOrder(2);
        for (OrderItem item:list){
            CheckClass.checkObject("OrderItem",item);
        }
    }
}

package dao;

import org.junit.Test;
import web.dao.impl.OrderItemDaoImpl;
import web.model.po.OrderItem;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public class OrderItemDaoImplTest {

    @Test
    public void testAll() throws ClassNotFoundException {
        OrderItemDaoImpl itemDao = new OrderItemDaoImpl();
        List<OrderItem> list = itemDao.getOrderItemOfOrder(1);
        for (OrderItem item:list){
            CheckClass.checkObject("OrderItem",item);
        }
    }


//    @Test
    public void add() throws ClassNotFoundException {
        OrderItemDaoImpl itemDao = new OrderItemDaoImpl();
        OrderItem item = new OrderItem();
        item.setOrder_id(1);
        item.setFood_id(1);
        item.setFood_num(3);
        itemDao.addOrderItem(item);

    }
}

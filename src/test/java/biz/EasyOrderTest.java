package biz;

import web.biz.impl.EasyOrderImpl;
import web.dao.impl.EasyOrderDaoImpl;
import web.dao.impl.FoodDaoImpl;
import web.dao.impl.OrderDaoImpl;
import web.dao.impl.OrderItemDaoImpl;
import web.model.exceptions.NotFoundException;
import web.model.vo.EasyOrderVO;

/**
 * Created by kylin on 09/12/2016.
 * All rights reserved.
 */
public class EasyOrderTest {

    private EasyOrderImpl order = new EasyOrderImpl();

    public EasyOrderTest() {
        order.setEasyOrderDao(new EasyOrderDaoImpl());
        order.setFoodDao(new FoodDaoImpl());
        order.setOrderDao(new OrderDaoImpl());
        order.setOrderItemDao(new OrderItemDaoImpl());
    }

    //    @Test
    public void testGet() {
        try {
            EasyOrderVO vo = order.getEasyOrder(1);
            System.out.println(vo.getPrice());
            System.out.println(vo.getTime());
            System.out.println(vo.getFood_list().size());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testAdd() {
        try {
            order.addEasyOrder(1, 4);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

}

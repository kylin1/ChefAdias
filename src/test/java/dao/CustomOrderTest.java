package dao;

import web.dao.impl.CustomOrderDaoImpl;
import web.model.po.CustomOrder;
import web.tools.CheckClass;

import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 17/12/2016.
 * All rights reserved.
 */
public class CustomOrderTest {

//    @Test
    public void getOrderInDay() throws ClassNotFoundException {
        CustomOrderDaoImpl dao = new CustomOrderDaoImpl();
        List<CustomOrder> list = dao.getOrderInDay(1,"2016-12-17", "2016-12-18");
        for (CustomOrder order : list) {
            CheckClass.checkObject("CustomOrder", order);
        }
    }

//    @Test
    public void getOrderOfUser() throws ClassNotFoundException {
        CustomOrderDaoImpl dao = new CustomOrderDaoImpl();
        List<CustomOrder> list = dao.getOrderOfMenu(1);
        for (CustomOrder order : list) {
            CheckClass.checkObject("CustomOrder", order);
        }
    }

//    @Test
    public void getOrder() throws ClassNotFoundException {
        CustomOrderDaoImpl dao = new CustomOrderDaoImpl();
        CustomOrder order = dao.getOrder(1);
        CheckClass.checkObject("CustomOrder", order);
    }


//    @Test
    public void add() throws ClassNotFoundException {
        CustomOrderDaoImpl dao = new CustomOrderDaoImpl();
        CustomOrder order = new CustomOrder();

        order.setMenu_id(1);
        order.setCreate_time(new Date());
        order.setIs_finish(1);
        order.setTicket_info(1);
        order.setBowl_info(1);
        order.setPay_type(1);

        dao.addOrder(order);

        System.out.println(order.getId());
    }

//    @Test
    public void update(){
        CustomOrderDaoImpl dao = new CustomOrderDaoImpl();
        dao.setState(3,true);
    }
}

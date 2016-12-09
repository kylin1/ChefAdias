package dao;

import org.junit.Test;
import web.dao.impl.EasyOrderDaoImpl;
import web.model.po.EasyOrder;
import web.tools.CheckClass;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
public class EasyOrderDaoImplTest {

    @Test
    public void test() throws ClassNotFoundException {
        EasyOrderDaoImpl dao = new EasyOrderDaoImpl();
        EasyOrder easyOrder = dao.getEasyOrderOfUser(1);
        CheckClass.checkObject("EasyOrder",easyOrder);
    }

//    @Test
    public void test2() throws ClassNotFoundException {
        EasyOrderDaoImpl dao = new EasyOrderDaoImpl();
        EasyOrder easyOrder = new EasyOrder();
        easyOrder.setUser_id(1);
        easyOrder.setOrder_id(1);
        dao.addEasyOrder(easyOrder);
    }

}

package dao;

import web.dao.impl.CustomMenuDaoImpl;
import web.model.po.CustomMenu;
import web.tools.CheckClass;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class CustomMenuDaoImplTest {

    private CustomMenuDaoImpl dao = new CustomMenuDaoImpl();

//    @Test
    public void add(){
        CustomMenu menu = new CustomMenu();
        menu.setUser_id(2);
        menu.setName("test");

        dao.addCustomMenu(menu);

    }

//    @Test
    public void delete(){
        dao.deleteCustomMenu(4);
        dao.deleteCustomMenu(5);
        dao.deleteCustomMenu(6);
    }
}

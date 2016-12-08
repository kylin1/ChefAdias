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
    public void get() throws ClassNotFoundException {
        CustomMenu menu = dao.getCustomMenu(3);
        CheckClass.checkObject("CustomMenu",menu);
    }

//    @Test
    public void add(){
        CustomMenu menu = new CustomMenu();
        menu.setUser_id(2);
        menu.setName("test");
        menu.setMeal_id(2);
        menu.setMeal_num(2);
        menu.setMeat_id(2);
        menu.setMeat_num(2);
        menu.setVegetable_id(2);
        menu.setVegetable_num(2);
        menu.setSnack_id(2);
        menu.setSnack_num(2);
        menu.setSauce_id(2);
        menu.setSauce_num(2);
        menu.setFlavor_id(2);
        dao.addCustomMenu(menu);

    }

//    @Test
    public void delete(){
        dao.deleteCustomMenu(4);
        dao.deleteCustomMenu(5);
        dao.deleteCustomMenu(6);
    }
}

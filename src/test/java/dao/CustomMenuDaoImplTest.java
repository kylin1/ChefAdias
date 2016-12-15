package dao;

import web.dao.impl.CustomMenuDaoImpl;
import web.model.po.CustomMenu;
import web.tools.CheckClass;

import java.util.Date;
import java.util.List;

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
        menu.setFlavor("test");
        menu.setName("test");
        menu.setTime(new Date());
        dao.addCustomMenu(menu);
    }

//    @Test
    public void get() throws ClassNotFoundException {
        List<CustomMenu> list = dao.getCustomMenuOfUser(1);
        for(CustomMenu menu:list){
            CheckClass.checkObject("CustomMenu",menu);
        }
    }

//    @Test
    public void delete(){
        dao.deleteCustomMenu(5);
    }

//    @Test
    public void update(){
        CustomMenu menu = new CustomMenu();
        menu.setId(1);
        menu.setUser_id(2);
        menu.setFlavor("test");
        menu.setName("test");
        menu.setTime(new Date());
        dao.updateCustomMenu(menu);
    }
}

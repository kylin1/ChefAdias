package dao;

import web.dao.impl.CustomMenuListDaoImpl;
import web.model.po.CustomMenuList;
import web.tools.CheckClass;

import java.util.List;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
public class CustomMenuListDaoTest {

    private CustomMenuListDaoImpl dao = new CustomMenuListDaoImpl();

//    @Test
    public void add(){
        CustomMenuList list = new CustomMenuList();
        list.setMenu_id(1);
        list.setFood_id(1);
        list.setNumber(2);
        dao.addCustomMenuList(list);
    }

//    @Test
    public void get() throws ClassNotFoundException {
        List<CustomMenuList> lists = dao.getMenuListOfMenu(2);
        for (CustomMenuList menu:lists){
            CheckClass.checkObject("CustomMenuList",menu);
        }
    }

//    @Test
    public void update(){
        CustomMenuList list = new CustomMenuList();
        list.setId(1);
        list.setMenu_id(1);
        list.setFood_id(2);
        list.setNumber(3);
        dao.updateCustomMenuList(list);
    }

//    @Test
    public void delete(){
        dao.deleteCustomMenuList(1);
    }

}

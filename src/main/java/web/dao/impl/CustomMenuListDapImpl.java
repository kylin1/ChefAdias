package web.dao.impl;

import web.dao.CustomMenuListDao;
import web.model.po.CustomMenu;
import web.model.po.CustomMenuList;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
public class CustomMenuListDapImpl implements CustomMenuListDao {
    @Override
    public MyMessage addCustomMenuList(CustomMenuList list) {
        return null;
    }

    @Override
    public List<CustomMenu> getCustomMenuOfUser(int userId) {
        return null;
    }

    @Override
    public List<CustomMenuList> getMenuListOfMenu(int menuId) {
        return null;
    }

    @Override
    public MyMessage deleteCustomMenuList(int id) {
        return null;
    }
}

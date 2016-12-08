package web.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CustomMenuDao;
import web.model.po.CustomMenu;
import web.tools.MyMessage;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */

@Transactional
@Repository("customMenuDao")
public class CustomMenuDaoImpl implements CustomMenuDao {
    @Override
    public MyMessage addCustomMenu(CustomMenu customMenu) {
        return null;
    }

    @Override
    public CustomMenu getCustomMenu(int id) {
        return null;
    }

    @Override
    public MyMessage deleteCustomMenu(int id) {
        return null;
    }
}

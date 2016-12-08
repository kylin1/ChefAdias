package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CustomMenuDao;
import web.dao.opearion.CustomMenuOperation;
import web.dao.util.MybatisUtils;
import web.model.po.CustomMenu;
import web.tools.MyMessage;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */

@Transactional
@Repository("customMenuDao")
public class CustomMenuDaoImpl implements CustomMenuDao {

    SqlSession session;

    CustomMenuOperation operation;

    @Override
    public MyMessage addCustomMenu(CustomMenu customMenu) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            this.operation.insert(customMenu);
            this.session.commit();
            myMessage = new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false,1,ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public CustomMenu getCustomMenu(int id) {
        CustomMenu customMenu = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            customMenu = this.operation.get(id);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return customMenu;
    }

    @Override
    public MyMessage deleteCustomMenu(int id) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            this.operation.delete(id);
            this.session.commit();
            myMessage = new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false,1,ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }
}

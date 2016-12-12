package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CustomMenuListDao;
import web.dao.opearion.CustomMenuListOperation;
import web.dao.util.MybatisUtils;
import web.model.po.CustomMenuList;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("customMenuListDao")
public class CustomMenuListDaoImpl implements CustomMenuListDao {

    SqlSession session;

    CustomMenuListOperation operation;

    @Override
    public MyMessage addCustomMenuList(CustomMenuList list) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuListOperation.class);
            this.operation.insert(list);
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
    public List<CustomMenuList> getMenuListOfMenu(int menuId) {
        List<CustomMenuList> result = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuListOperation.class);
            result = this.operation.getMenuListOfMenu(menuId);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return result;
    }

    @Override
    public MyMessage deleteCustomMenuList(int id) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuListOperation.class);
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

    @Override
    public MyMessage updateCustomMenuList(CustomMenuList list) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuListOperation.class);
            this.operation.update(list);
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

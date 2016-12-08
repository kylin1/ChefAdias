package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CustomMenuDao;
import web.dao.opearion.CustomMenuOperation;
import web.dao.util.MybatisUtils;
import web.model.po.CustomMenuFood;
import web.tools.MyMessage;

import java.util.List;

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
    public List<CustomMenuFood> getCustomMenuFood() {
        List<CustomMenuFood> list = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            list = this.operation.getAll();
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return list;
    }

    @Override
    public CustomMenuFood get(int mmenu_foodid) {
        CustomMenuFood food = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            food = this.operation.get(mmenu_foodid);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return food;
    }

    @Override
    public MyMessage add(CustomMenuFood customMenuFood) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            this.operation.add(customMenuFood);
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
    public MyMessage update(CustomMenuFood customMenuFood) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            this.operation.update(customMenuFood);
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
    public MyMessage delete(int mmenu_foodid) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomMenuOperation.class);
            this.operation.delete(mmenu_foodid);
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

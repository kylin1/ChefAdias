package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CustomOrderDao;
import web.dao.opearion.CustomOrderOperation;
import web.dao.util.MybatisUtils;
import web.model.po.CustomOrder;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 16/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("customOrderDao")
public class CustomOrderDaoImpl implements CustomOrderDao {

    SqlSession session;
    CustomOrderOperation operation;

    @Override
    public MyMessage addOrder(CustomOrder CustomOrder) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomOrderOperation.class);
            this.operation.insert(CustomOrder);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public List<CustomOrder> getOrderOfMenu(int menuId) {
        List<CustomOrder> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomOrderOperation.class);
            list = this.operation.getOrderOfMenu(menuId);
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
    public List<CustomOrder> getOrderInDay(int menuId, String startDate, String endDate) {
        List<CustomOrder> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomOrderOperation.class);
            list = this.operation.getOrderOfUserInDay(menuId, startDate, endDate);
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
    public List<CustomOrder> getOrderInDay(String startDate, String endDate) {
        List<CustomOrder> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomOrderOperation.class);
            list = this.operation.getOrderInDay(startDate, endDate);
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
    public CustomOrder getOrder(int id) {
        CustomOrder CustomOrder = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomOrderOperation.class);
            CustomOrder = this.operation.getOrder(id);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return CustomOrder;
    }

    @Override
    public MyMessage setState(int orderId, boolean isArrived) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(CustomOrderOperation.class);
            if(isArrived){
                this.operation.updateState(1,orderId);
            }else{
                this.operation.updateState(0,orderId);
            }
            this.session.commit();
            return new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            return new MyMessage(false,1,ex.getMessage());
        } finally {
            this.session.close();
        }
    }

}

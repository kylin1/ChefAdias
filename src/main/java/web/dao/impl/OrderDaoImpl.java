package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.OrderDao;
import web.dao.opearion.OrderOperation;
import web.dao.util.MybatisUtils;
import web.model.po.Order;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Repository("orderDao")
@Transactional
public class OrderDaoImpl implements OrderDao {

    SqlSession session;

    OrderOperation operation;

    @Override
    public MyMessage addOrder(Order order) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(OrderOperation.class);
            this.operation.save(order);
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
    public List<Order> getOrderOfUser(int userID) {
        List<Order> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(OrderOperation.class);
            list = this.operation.getOrderOfUser(userID);
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
    public Order getOrder(int id) {
        Order order = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(OrderOperation.class);
            order = this.operation.getOrder(id);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
        return order;
    }

    @Override
    public MyMessage setState(int orderId, boolean isArrived) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(OrderOperation.class);
            if(isArrived){
                this.operation.updateState(orderId,1);
            }else{
                this.operation.updateState(orderId,0);
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

package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.OrderItemDao;
import web.dao.opearion.OrderItemOperation;
import web.dao.util.MybatisUtils;
import web.model.po.OrderItem;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("orderItemDao")
public class OrderItemDaoImpl implements OrderItemDao {

    private SqlSession session;
    private OrderItemOperation operation;

    @Override
    public MyMessage addOrderItem(OrderItem orderItem) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(OrderItemOperation.class);
            this.operation.insert(orderItem);
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
    public List<OrderItem> getOrderItemOfOrder(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(OrderItemOperation.class);
            list = this.operation.getOrderItem(orderId);
            this.session.commit();
        }catch (Exception ex){
            this.session.rollback();
        }finally {
            this.session.close();
        }
        return list;
    }
}

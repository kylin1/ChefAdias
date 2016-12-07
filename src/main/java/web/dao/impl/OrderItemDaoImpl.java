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

    private SqlSession sqlSession;
    private OrderItemOperation operation;

    @Override
    public MyMessage addOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemOfOrder(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        try {
            this.sqlSession = MybatisUtils.getSession();
            this.operation = this.sqlSession.getMapper(OrderItemOperation.class);
            list = this.operation.getOrderItem(orderId);
            this.sqlSession.commit();
        }catch (Exception ex){
            this.sqlSession.rollback();
        }finally {
            this.sqlSession.close();
        }
        return list;
    }
}

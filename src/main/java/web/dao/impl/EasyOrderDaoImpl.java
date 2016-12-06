package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.EasyOrderDao;
import web.dao.opearion.EasyOrderOperation;
import web.dao.opearion.OrderItemOperation;
import web.dao.util.MybatisUtils;
import web.model.EasyOrder;
import web.model.OrderItem;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("easyOrderDao")
public class EasyOrderDaoImpl implements EasyOrderDao {

    SqlSession sqlSession;
    EasyOrderOperation operation;
    OrderItemOperation itemOperation;

    @Override
    public EasyOrder getEasyOrderOfUser(int userId) {
        EasyOrder easyOrder = null;
        try {
            this.sqlSession = MybatisUtils.getSession();
            this.operation = this.sqlSession.getMapper(EasyOrderOperation.class);
            this.itemOperation = this.sqlSession.getMapper(OrderItemOperation.class);

            //获取基本信息
            easyOrder = this.operation.getEasyOrder(userId);
            //获取里面的list信息
            List<OrderItem> orderItems = this.itemOperation.getOrderItem(userId);
            easyOrder.setFood_list(orderItems);

            this.sqlSession.commit();
        }catch (Exception ex){
            this.sqlSession.rollback();
        }finally {
            this.sqlSession.close();
        }
        return easyOrder;
    }

    @Override
    public MyMessage addEasyOrder(EasyOrder easyOrder) {
        return null;
    }
}

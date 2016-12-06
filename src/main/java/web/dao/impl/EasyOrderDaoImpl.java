package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.EasyOrderDao;
import web.dao.opearion.EasyOrderOperation;
import web.dao.opearion.OrderItemOperation;
import web.dao.util.MybatisUtils;
import web.model.po.EasyOrder;
import web.model.vo.OrderItem;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("easyOrderDao")
public class EasyOrderDaoImpl implements EasyOrderDao {

    SqlSession session;
    EasyOrderOperation operation;
    OrderItemOperation itemOperation;

    @Override
    public EasyOrder getEasyOrderOfUser(int userId) {
        EasyOrder easyOrder = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(EasyOrderOperation.class);
            this.itemOperation = this.session.getMapper(OrderItemOperation.class);

            //获取基本信息
            easyOrder = this.operation.getEasyOrder(userId);

            //获取里面的list信息
            List<OrderItem> orderItems = this.itemOperation.getOrderItem(userId);
            easyOrder.setFood_list(orderItems);

            this.session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.session.rollback();
        }finally {
            this.session.close();
        }
        return easyOrder;
    }

    private EasyOrder getBasicOfUser(int userId) {
        EasyOrder easyOrder = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(EasyOrderOperation.class);
            this.itemOperation = this.session.getMapper(OrderItemOperation.class);
            easyOrder = this.operation.getEasyOrderBasic(userId);
            this.session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.session.rollback();
        }finally {
            this.session.close();
        }
        return easyOrder;
    }

    @Override
    public MyMessage addEasyOrder(EasyOrder easyOrder) {
        int id = easyOrder.getUser_id();
        EasyOrder old = this.getBasicOfUser(id);
        //不存在则新增
        if(old == null){
            System.out.println("no");
            this.insertEasyOrder(easyOrder);
            return new MyMessage(true);
            //存在则更新
        }else{
            System.out.println("has");
            this.updateEasyOrder(easyOrder);
            return new MyMessage(true);
        }
    }

    /**
     * 用户无easyOrder则新增
     *
     * @param easyOrder
     */
    private void insertEasyOrder(EasyOrder easyOrder) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(EasyOrderOperation.class);
            this.operation.insert(easyOrder);
            this.session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.session.rollback();
        }finally {
            this.session.close();
        }
    }

    /**
     * 更新
     *
     * @param easyOrder
     */
    private void updateEasyOrder(EasyOrder easyOrder) {
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(EasyOrderOperation.class);
            this.operation.update(easyOrder);
            this.session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
        } finally {
            this.session.close();
        }
    }

}

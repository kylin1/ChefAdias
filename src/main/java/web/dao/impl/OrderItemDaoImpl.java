package web.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.OrderItemDao;
import web.model.OrderItem;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("orderItemDao")
public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public MyMessage addOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public List<OrderItem> getaddOrderItemOfOrder(int orderId) {
        return null;
    }
}

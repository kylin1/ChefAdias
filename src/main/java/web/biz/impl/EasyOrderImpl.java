package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.EasyOrderService;
import web.dao.EasyOrderDao;
import web.dao.OrderDao;
import web.dao.OrderItemDao;
import web.model.po.EasyOrder;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.model.po.Order;
import web.model.vo.EasyOrderVO;
import web.model.vo.FoodItemVO;
import web.model.vo.OrderItem;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2016/12/5.
 */
@Service
public class EasyOrderImpl implements EasyOrderService {
    @Autowired
    EasyOrderDao easyOrderDao;
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public EasyOrderVO getEasyOrder(int userID) throws NotFoundException {
        //EasyOrderDAO
        EasyOrder easyOrder = easyOrderDao.getEasyOrderOfUser(userID);
        if (easyOrder == null) {
            throw new NotFoundException(ErrorCode.SERVER);
        }
        int intOrderID = easyOrder.getOrder_id();

        //OrderItemDAO
        List<OrderItem> orderItemList = orderItemDao.getOrderItemOfOrder(intOrderID);
        BigDecimal sumPrice = new BigDecimal(0);
        List<FoodItemVO> foodList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            FoodItemVO foodItemVO =
                    new FoodItemVO(orderItem.getFoodid() + "", orderItem.getName(),
                            orderItem.getPrice(), orderItem.getNum());
            sumPrice = sumPrice.add(orderItem.getPrice());
            foodList.add(foodItemVO);
        }

        //OrderDAO
        Order order = orderDao.getOrder(intOrderID);
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time = format.format(order.getCreate_time());

        return new EasyOrderVO(foodList, sumPrice, time);
    }

    @Override
    public MyMessage addEasyOrder(int userID, int orderID) throws NotFoundException {
        EasyOrder easyOrder = new EasyOrder();
        easyOrder.setOrder_id(orderID);
        easyOrder.setUser_id(userID);
        return easyOrderDao.addEasyOrder(easyOrder);
    }
}

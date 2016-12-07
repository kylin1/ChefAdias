package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.OrderService;
import web.dao.FoodDao;
import web.dao.OrderDao;
import web.dao.OrderItemDao;
import web.model.po.Order;
import web.model.po.OrderItem;
import web.model.vo.*;
import web.tools.MyConverter;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Service
public class OrderImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private FoodDao foodDao;

    @Override
    public MyMessage addOrder(AddOrderVO addOrderVO) {
        //OrderDAO
        Order order = new Order();
        order.setUser_id(addOrderVO.getUserid());
        order.setCreate_time(MyConverter.getDate(addOrderVO.getTime()));
        order.setIsFinish(0);
        order.setBowl_info(addOrderVO.getBowl_info());
        order.setPrice(addOrderVO.getPrice());
        order.setPay_type(addOrderVO.getPay_type());
        order.setTicket_info(addOrderVO.getTicket_info());
        MyMessage orderMessage = orderDao.addOrder(order);

        //OrderItemDAO
        List<OrderItemVO> orderItemList = addOrderVO.getFood_list();
        for (OrderItemVO orderItemVO : orderItemList) {
            String foodID = orderItemVO.getFoodid();
            int num = orderItemVO.getNum();
            //TODO 缺少FoodDAO对于foodInfo（name,price）的操作
            BigDecimal price = new BigDecimal(0);
            String name = "";

            OrderItem orderItem = new OrderItem();
            orderItem.setFoodid(Integer.parseInt(foodID));
            orderItem.setNum(num);
            orderItem.setName(name);
            orderItem.setPrice(price);
            MyMessage orderItemMessage = orderItemDao.addOrderItem(orderItem);
            if (!orderItemMessage.isSuccess()) {
                return orderItemMessage;
            }
        }

        return new MyMessage(orderMessage.isSuccess());
    }

    @Override
    public List<UserOrderItemVO> getOrderList(int userId) {
        List<Order> orderList = orderDao.getOrderOfUser(userId);
        List<UserOrderItemVO> userOrderItemVOList = new ArrayList<>();
        for (Order order : orderList) {
            DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            UserOrderItemVO vo = new UserOrderItemVO(order.getOrder_id() + "", order.getPrice(), format.format(order.getCreate_time()));
            userOrderItemVOList.add(vo);
        }
        return userOrderItemVOList;
    }

    @Override
    public UserOrderVO getOrder(int orderId) {
        //OrderDAO
        Order order = orderDao.getOrder(orderId);
        int intOrderID = order.getOrder_id();
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time = format.format(order.getCreate_time());

        //OrderItemDAO
        List<OrderItem> orderItemList = orderItemDao.getOrderItemOfOrder(intOrderID);
        List<FoodItemVO> foodItemVOList = new ArrayList<>();
        BigDecimal sum = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            FoodItemVO vo = new FoodItemVO(orderItem.getFoodid() + "", orderItem.getName(), orderItem.getPrice(), orderItem.getNum());
            foodItemVOList.add(vo);
            sum = sum.add(orderItem.getPrice());
        }

        return new UserOrderVO(foodItemVOList, sum, time);
    }

    @Override
    public MyMessage comment(int userID, int foodID, int comment) {
        //TODO 缺少comment的DAO操作
        return null;
    }
}

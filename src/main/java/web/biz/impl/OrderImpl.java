package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.OrderService;
import web.dao.FoodDao;
import web.dao.OrderDao;
import web.dao.OrderItemDao;
import web.model.po.Food;
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
        order.setIs_finish(0);
        order.setBowl_info(addOrderVO.getBowl_info());
        order.setPrice(addOrderVO.getPrice());
        order.setPay_type(addOrderVO.getPay_type());
        order.setTicket_info(addOrderVO.getTicket_info());
        MyMessage orderMessage = orderDao.addOrder(order);

        //获取插入的OrderPO id
        int orderId = order.getId();

        //OrderItemDAO
        List<OrderItemVO> orderItemList = addOrderVO.getFood_list();
        for (OrderItemVO orderItemVO : orderItemList) {
            String foodID = orderItemVO.getFoodid();
            int intFoodID = Integer.parseInt(foodID);
            Food food = foodDao.getFood(intFoodID);
            int num = orderItemVO.getNum();

            //订单包含的菜品信息
            OrderItem orderItem = new OrderItem();
            //菜品信息指向父亲order
            orderItem.setOrder_id(orderId);
            orderItem.setFood_id(intFoodID);
            orderItem.setFood_num(num);
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
            UserOrderItemVO vo = new UserOrderItemVO(order.getId() + "", order.getPrice(), format.format(order.getCreate_time()));
            userOrderItemVOList.add(vo);
        }
        return userOrderItemVOList;
    }

    @Override
    public UserOrderVO getOrder(int orderId) {
        //OrderDAO
        Order order = orderDao.getOrder(orderId);
        int intOrderID = order.getId();
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time = format.format(order.getCreate_time());

        //OrderItemDAO
        List<OrderItem> orderItemList = orderItemDao.getOrderItemOfOrder(intOrderID);
        List<FoodItemVO> foodItemVOList = new ArrayList<>();
        BigDecimal sum = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            Food food = foodDao.getFood(orderItem.getFood_id());
            FoodItemVO vo = new FoodItemVO(orderItem.getFood_id() + "", food.getName(), food.getPrice(), orderItem.getFood_num());
            foodItemVOList.add(vo);

            //计算一个OrderItem食物的价格:数量*单价
            int foodNum = orderItem.getFood_num();
            BigDecimal foodPrice = food.getPrice();
            BigDecimal bigFoodNum = new BigDecimal(foodNum);
            BigDecimal onePrice = foodPrice.multiply(bigFoodNum);
            sum = sum.add(onePrice);
        }

        return new UserOrderVO(foodItemVOList, sum, time);
    }

    @Override
    public MyMessage comment(int foodID, int comment) {
        Food food = foodDao.getFood(foodID);
        if (comment == 0) {
            food.setDislike(food.getDislike() + 1);
        } else {
            food.setLike(food.getLike() + 1);
        }
        return foodDao.updateFood(food);
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }
}

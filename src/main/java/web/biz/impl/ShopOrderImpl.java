package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.ShopOrderService;
import web.dao.FoodDao;
import web.dao.OrderDao;
import web.dao.OrderItemDao;
import web.dao.UserDao;
import web.model.po.Food;
import web.model.po.Order;
import web.model.po.OrderItem;
import web.model.po.User;
import web.model.vo.FoodItemVO;
import web.model.vo.ShopOrderItemVO;
import web.model.vo.ShopOrderVO;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class ShopOrderImpl implements ShopOrderService {
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    FoodDao foodDao;

    @Override
    public ShopOrderVO getOrder(int orderID) {
        //OrderDAO
        Order order = orderDao.getOrder(orderID);
        if (order == null) {
            return null;
        }
        int userID = order.getUser_id();

        //UserDAO
        User user = userDao.getUser(userID);
        String username = user.getUsername();
        String addr = user.getAddress();
        String phone = user.getPhone();

        //OrderItemDAO
        List<OrderItem> orderItemList = orderItemDao.getOrderItemOfOrder(orderID);
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

        int type = order.getPay_type();
        String typeString = "";
        if (type == 0) {
            typeString = "货到付款";
        } else {
            typeString = "在线付款";
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new ShopOrderVO(
                username,
                format.format(order.getCreate_time()),
                addr, phone,
                order.getIs_finish(),
                typeString,
                sum,
                foodItemVOList);
    }

    @Override
    public List<ShopOrderItemVO> getOrderList(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"),
                sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        List<ShopOrderItemVO> shopOrderItemVOList = new ArrayList<>();
        try {
            Date today = sdf.parse(dateStr);
            Calendar ca = Calendar.getInstance();
            ca.setTime(today);
            ca.add(Calendar.DATE, 1);
            Date tomorrow = ca.getTime();
            List<Order> orderList = orderDao.getOrderInDay(sdf2.format(today), sdf2.format(tomorrow));

            for (Order order : orderList) {
                int orderID = order.getId();
                int userID = order.getUser_id();
                User user = userDao.getUser(userID);
                String username = user.getUsername();
                BigDecimal price = order.getPrice();
                Date time = order.getCreate_time();
                String addr = user.getAddress();
                int isFinish = order.getIs_finish();
                int type = order.getPay_type();
                String typeString = "";
                if (type == 0) {
                    typeString = "货到付款";
                } else {
                    typeString = "在线付款";
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ShopOrderItemVO shopOrderItemVO = new ShopOrderItemVO(orderID + "", username, format.format(time), addr, isFinish, typeString, price);
                shopOrderItemVOList.add(shopOrderItemVO);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shopOrderItemVOList;
    }

    @Override
    public MyMessage setState(int orderID, int state) {
        return orderDao.setState(orderID, state == 1);
    }
}

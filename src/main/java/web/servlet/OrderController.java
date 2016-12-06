package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.OrderService;
import web.model.po.Order;
import web.model.vo.OrderItem;
import web.model.vo.OrderResponse;
import web.model.exceptions.ErrorCode;
import web.tools.JsonConverter;
import web.tools.MyConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping(
            value = "addOrder",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addOrder(HttpServletRequest request) {
        //读取参数
        String userid = request.getParameter("userid");
        String time = request.getParameter("time");
        String food_list = request.getParameter("food_list");
        String price = request.getParameter("price");
        String ticket_info = request.getParameter("ticket_info");
        String bowl_info = request.getParameter("bowl_info");
        String pay_type = request.getParameter("pay_type");

        try {
            Order order = new Order();

            //类型转换
            int intUserId = MyConverter.getInt(userid);
            Date dateTime = MyConverter.getDate(time);
            List<OrderItem> lst = JsonConverter.getItemList(food_list);
            BigDecimal decimalPrice = MyConverter.getBigDecimal(price);
            int useTicket = MyConverter.getInt(ticket_info);
            int useBowl = MyConverter.getInt(bowl_info);
            int payOnline = MyConverter.getInt(pay_type);

            //设置参数
            order.setUser_id(intUserId);
            order.setCreate_time(dateTime);
            order.setOrderItemList(lst);
            order.setPrice(decimalPrice);
            order.setTicket_info(useTicket);
            order.setBowl_info(useBowl);
            order.setPay_type(payOnline);

            //新增订单
            this.service.addOrder(order);
            return MyResponse.success();

        } catch (IOException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, "下单失败");
        }
    }

    @RequestMapping(
            value = "getOrderList",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getOrderList(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        int intUserID = Integer.parseInt(userID);

        List<Order> orderList = service.getOrderList(intUserID);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orderList) {
            String orderID = order.getOrder_id() + "";
            BigDecimal price = order.getPrice();
            Date date = order.getCreate_time();
            DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            OrderResponse response = new OrderResponse(orderID, price, format.format(date));
            orderResponses.add(response);
        }

        try {
            return MyResponse.success(JsonConverter.jsonOfObject(orderResponses));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, "获取订单失败");
        }
    }

    @RequestMapping(
            value = "getOrder",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getOrder(HttpServletRequest request) {
        String orderID = request.getParameter("orderid");
        int intOrderID = Integer.parseInt(orderID);

        Order order = service.getOrder(intOrderID);

        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Map<String, String> orderMap = new HashMap<>();
        orderMap.put("price", order.getPrice() + "");
        orderMap.put("time", format.format(order.getCreate_time()));
        List<OrderItem> orderItemList = order.getOrderItemList();
        try {
            orderMap.put("food_list", JsonConverter.jsonOfObject(orderItemList));
            return MyResponse.success(orderMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, "获取订单失败");
        }
    }


    @RequestMapping(
            value = "comment",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String comment(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        String foodID = request.getParameter("foodid");
        String comment = request.getParameter("comment");
        int intUserID = Integer.parseInt(userID);
        int intFoodID = Integer.parseInt(foodID);
        int intComment = Integer.parseInt(comment);
        return null;
    }
}
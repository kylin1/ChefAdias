package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.OrderService;
import web.model.po.Order;
import web.model.vo.AddOrderVO;
import web.model.vo.OrderItemVO;
import web.model.exceptions.ErrorCode;
import web.model.vo.UserOrderItemVO;
import web.model.vo.UserOrderVO;
import web.tools.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Controller
@RequestMapping("menu")
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping(
            value = "addOrder",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addOrder(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        String time = request.getParameter("time");
        String food_list = request.getParameter("food_list");
        String price = request.getParameter("price");
        String ticket_info = request.getParameter("ticket_info");
        String bowl_info = request.getParameter("bowl_info");
        String pay_type = request.getParameter("pay_type");

        try {
            //类型转换
            int intUserId = MyConverter.getInt(userid);
            BigDecimal decimalPrice = MyConverter.getBigDecimal(price);
            JsonListConverter<OrderItemVO> jsonListConverter = new JsonListConverter<>();
            List<OrderItemVO> orderItemList = jsonListConverter.getList(food_list, OrderItemVO.class);
            int useTicket = MyConverter.getInt(ticket_info);
            int useBowl = MyConverter.getInt(bowl_info);
            int payType = MyConverter.getInt(pay_type);

            //新增订单
            service.addOrder(new AddOrderVO(intUserId, time, orderItemList, decimalPrice, useTicket, useBowl, payType));
            return MyResponse.success();

        } catch (Exception e) {
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

        List<UserOrderItemVO> userOrderItemList = service.getOrderList(intUserID);
        try {
            return MyResponse.success(JsonConverter.jsonOfObject(userOrderItemList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, "fail to obtain order list");
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

        return MyResponse.success(BeanTool.bean2Map(service.getOrder(intOrderID)));
    }


    @RequestMapping(
            value = "comment",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String comment(HttpServletRequest request) {
        String foodID = request.getParameter("foodid");
        String comment = request.getParameter("comment");
        int intFoodID = Integer.parseInt(foodID);
        int intComment = Integer.parseInt(comment);

        MyMessage myMessage = service.comment(intFoodID, intComment);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to comment");
        }
    }
}
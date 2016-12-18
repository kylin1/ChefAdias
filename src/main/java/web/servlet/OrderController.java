package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String addOrder(@RequestBody AddOrderVO addOrderVO) {
        //新增订单
        MyMessage myMessage = service.addOrder(addOrderVO);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add order");
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
        return MyResponse.success(userOrderItemList);
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

    @RequestMapping(
            value = {"addMOrder"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String addMOrder(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        String menuID = request.getParameter("mmenuid");
        String payType = request.getParameter("pay_type");
        MyMessage myMessage = service.addMOrder(MyConverter.getInt(userID), MyConverter.getInt(payType), MyConverter.getInt(menuID));
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add custom order");
        }
    }
}
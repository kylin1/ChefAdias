package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.OrderService;
import web.model.Order;
import web.model.OrderItem;
import web.tools.JsonConverter;
import web.tools.MyConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping(
            value = "/addOrder",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addOrder(HttpServletRequest request){
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
        }

        return "";
    }
}

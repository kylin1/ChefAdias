package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.TicketService;
import web.model.Ticket;
import web.model.UserTicket;
import web.model.exceptions.ErrorCode;
import web.tools.JsonConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
@Controller
public class TicketController {
    @Autowired
    private TicketService service;

    @RequestMapping(
            value = "getTickInfo",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getTickInfo(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        int intUserID = Integer.parseInt(userID);

        UserTicket userTicket = service.getTicketInfo(intUserID);
        BigDecimal remain = userTicket.getRemain();
        String expire = userTicket.getExpireTime().toString();

        Map<String, String> tickMap = new HashMap<>();
        tickMap.put("remain_money", remain + "");
        tickMap.put("expire", expire);

        return MyResponse.success(tickMap);
    }

    @RequestMapping(
            value = "buyTick",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String buyTick(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        String tickID = request.getParameter("tickid");
        int intUserID = Integer.parseInt(userID);
        int intTickID = Integer.parseInt(tickID);

        boolean isSuccess = service.buyTicket(intUserID, intTickID);
        if (isSuccess) {
            return MyResponse.success(null);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "未购买成功");
        }
    }
}

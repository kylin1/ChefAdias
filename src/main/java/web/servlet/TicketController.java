package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.TicketService;
import web.model.po.UserTicket;
import web.model.exceptions.ErrorCode;
import web.model.vo.TickInfoVO;
import web.tools.BeanTool;
import web.tools.MyMessage;
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

        return MyResponse.success(BeanTool.bean2Map(service.getTicketInfo(intUserID)));
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

        MyMessage myMessage = service.buyTicket(intUserID, intTickID);
        if (myMessage.isSuccess()) {
            return MyResponse.success(null);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "failure in buying ticket");
        }
    }
}

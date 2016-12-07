package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.EasyOrderService;
import web.model.po.EasyOrder;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.model.vo.EasyOrderVO;
import web.tools.BeanTool;
import web.tools.JsonConverter;
import web.tools.MyMessage;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan on 2016/12/5.
 * All rights reserved.
 */
@Controller
@RequestMapping("user")
public class EasyOrderController {
    @Autowired
    private EasyOrderService service;

    @RequestMapping(value = "getEasyOrder", method = RequestMethod.GET)
    @ResponseBody
    public String getEasyOrder(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        int intUserID = Integer.parseInt(userID);

        try {
            EasyOrderVO easyOrderVO = service.getEasyOrder(intUserID);
            return MyResponse.success(BeanTool.bean2Map(easyOrderVO));

        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, "fail to obtain easy order");
        }
    }

    @RequestMapping(value = "modEasyOrder", method = RequestMethod.POST)
    @ResponseBody
    public String modEasyOrder(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        String orderID = request.getParameter("orderid");

        int intUserID = Integer.parseInt(userID);
        int intOrderID = Integer.parseInt(orderID);

        try {
            MyMessage myMessage = service.addEasyOrder(intUserID, intOrderID);
            if (myMessage.isSuccess()) {
                return MyResponse.success(null);
            } else {
                return MyResponse.failure(ErrorCode.SERVER, "修改失败");
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, e.getMessage());
        }
    }
}

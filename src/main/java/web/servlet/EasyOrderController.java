package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.EasyOrderService;
import web.biz.IUserService;
import web.model.EasyOrder;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.tools.JsonConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan on 2016/12/5.
 * All rights reserved.
 */
@Controller
public class EasyOrderController {
    @Autowired
    private EasyOrderService service;

    @RequestMapping(value = "/getEasyOrder", method = RequestMethod.GET)
    @ResponseBody
    public String getEasyOrder(HttpServletRequest request) {
        String userID = request.getParameter("userid");
        int intUserID = Integer.parseInt(userID);

        try {
            EasyOrder easyOrder = service.getEasyOrder(intUserID);
            try {
                Map<String, String> resultMap = new HashMap<>();
                String foodListJson = JsonConverter.jsonOfObject(easyOrder.getFood_list());
                resultMap.put("food_list", foodListJson);
                resultMap.put("price", easyOrder.getPrice() + "");
                resultMap.put("time", easyOrder.getTime());
                return MyResponse.success(resultMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return MyResponse.failure(ErrorCode.SERVER, e.getMessage());
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(ErrorCode.SERVER, e.getMessage());
        }
    }

    @RequestMapping(value = "/addEasyOrder", method = RequestMethod.POST)
    @ResponseBody
    public String addEasyOrder(HttpServletRequest request) {
        return null;
    }
}

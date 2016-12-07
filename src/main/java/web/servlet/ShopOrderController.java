package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.ShopOrderService;
import web.model.vo.ShopOrderItemVO;
import web.model.vo.ShopOrderVO;
import web.model.exceptions.ErrorCode;
import web.tools.BeanTool;
import web.tools.JsonConverter;
import web.tools.MyMessage;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 商户订单
 * Created by Alan on 2016/12/6.
 */
@Controller
@RequestMapping("shop")
public class ShopOrderController {
    @Autowired
    private ShopOrderService service;

    @RequestMapping(
            value = "getOrderList",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getOrderList(HttpServletRequest request) {
        String date = request.getParameter("date");
        List<ShopOrderItemVO> orderList = service.getOrderList(date);

        return MyResponse.success(orderList);
    }

    @RequestMapping(
            value = "getOrder",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getOrder(HttpServletRequest request) {
        String orderID = request.getParameter("orderid");
        int intOrderID = Integer.parseInt(orderID);

        ShopOrderVO shopOrderVO = service.getOrder(intOrderID);
        Map<String, Object> shopOrderMap = BeanTool.bean2Map(shopOrderVO);
        if (shopOrderMap != null) {
            return MyResponse.success(shopOrderMap);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "failure in obtaining order detail");
        }
    }

    @RequestMapping(
            value = "setState",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String setState(HttpServletRequest request) {
        String orderID = request.getParameter("orderid");
        String state = request.getParameter("state");
        int intOrderID = Integer.parseInt(orderID);
        int intState = Integer.parseInt(state);

        MyMessage myMessage = service.setState(intOrderID, intState);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "failure in setting state of arrival");
        }
    }
}

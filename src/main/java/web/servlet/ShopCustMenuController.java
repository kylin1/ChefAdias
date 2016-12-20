package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.biz.CustOrderVO;
import web.biz.CustomerMenuService;
import web.model.exceptions.ErrorCode;
import web.model.vo.CustOrderInfoVO;
import web.tools.MyConverter;
import web.tools.MyMessage;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kylin on 11/12/2016.
 * All rights reserved.
 */
@Controller
@RequestMapping("shop")
public class ShopCustMenuController {

    @Autowired
    private CustomerMenuService customerMenuService;

    @RequestMapping(
            value = "addMMenu",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addMMenu(HttpServletRequest request) {
        String strType = request.getParameter("type");
        String strName = request.getParameter("name");
        String strPrice = request.getParameter("price");

        int type = MyConverter.getInt(strType);
        BigDecimal price = new BigDecimal(strPrice);
        int foodID = customerMenuService.addMMenu(type, strName, price);

        if (foodID != -1) {
            return MyResponse.success(foodID);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "add mmenu failed");
        }
    }

    @RequestMapping(
            value = "uploadCustFoodPic",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String uploadCustFoodPic(@RequestParam(value = "pic") MultipartFile pic, @RequestParam(value = "foodid") String foodID) {
        boolean isSuccess = customerMenuService.uploadCustFoodPic(MyConverter.getInt(foodID), pic);
        if (isSuccess) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to upload pic");
        }
    }

    @RequestMapping(
            value = "getCustOrderList",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getCustOrderList(HttpServletRequest request) {
        String date = request.getParameter("date");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<CustOrderVO> orderList = null;
        try {
            orderList = customerMenuService.getCustOrderList(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (orderList != null) {
            return MyResponse.success(orderList);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to get custom order list");
        }
    }

    @RequestMapping(
            value = "getCustOrder",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getCustOrder(HttpServletRequest request) {
        String orderID = request.getParameter("orderid");
        CustOrderInfoVO orderInfo = customerMenuService.getCustOrder(MyConverter.getInt(orderID));
        if (orderInfo != null) {
            return MyResponse.success(orderInfo);
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to get custom order");
        }
    }

    @RequestMapping(
            value = "modMMenu",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String modMMenu(HttpServletRequest request) {
        String strId = request.getParameter("mmenu_foodid");
        String strType = request.getParameter("type");
        String strName = request.getParameter("name");
        String strPrice = request.getParameter("price");

        int id = MyConverter.getInt(strId);
        int type = MyConverter.getInt(strType);
        BigDecimal price = new BigDecimal(strPrice);
        boolean result = this.customerMenuService.modMMenu(id, type, strName, price);

        if (result) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "update mmenu failed");
        }
    }


    @RequestMapping(
            value = "delMMenu",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String delMMenu(HttpServletRequest request) {
        String strId = request.getParameter("mmenu_foodid");

        int id = MyConverter.getInt(strId);
        boolean result = this.customerMenuService.deleteMMenu(id);

        if (result) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "delete mmenu failed");
        }
    }

    @RequestMapping(
            value = "setCustState",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String setCustState(HttpServletRequest request) {
        String state = request.getParameter("state");
        String orderID = request.getParameter("orderid");
        MyMessage myMessage = customerMenuService.setCustState(MyConverter.getInt(orderID), MyConverter.getInt(state));
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to set custom state");
        }

    }

}

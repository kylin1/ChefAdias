package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.CustomerMenuService;
import web.model.exceptions.ErrorCode;
import web.tools.MyConverter;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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
    public String addMMenu(HttpServletRequest request){
        String strType = request.getParameter("type");
        String strName = request.getParameter("name");
        String strPrice = request.getParameter("price");

        int type = MyConverter.getInt(strType);
        BigDecimal price = new BigDecimal(strPrice);
        boolean result = this.customerMenuService.addMMenu(type,strName,price);

        if(result){
            return MyResponse.success();
        }else{
            return MyResponse.failure(ErrorCode.SERVER,"add mmenu failed");
        }
    }

    @RequestMapping(
            value = "modMMenu",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String modMMenu(HttpServletRequest request){
        String strId = request.getParameter("mmenu_foodid");
        String strType = request.getParameter("type");
        String strName = request.getParameter("name");
        String strPrice = request.getParameter("price");

        int id = MyConverter.getInt(strId);
        int type = MyConverter.getInt(strType);
        BigDecimal price = new BigDecimal(strPrice);
        boolean result = this.customerMenuService.modMMenu(id,type,strName,price);

        if(result){
            return MyResponse.success();
        }else{
            return MyResponse.failure(ErrorCode.SERVER,"update mmenu failed");
        }
    }


    @RequestMapping(
            value = "delMMenu",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String delMMenu(HttpServletRequest request){
        String strId = request.getParameter("mmenu_foodid");

        int id = MyConverter.getInt(strId);
        boolean result = this.customerMenuService.deleteMMenu(id);

        if(result){
            return MyResponse.success();
        }else{
            return MyResponse.failure(ErrorCode.SERVER,"delete mmenu failed");
        }
    }
}

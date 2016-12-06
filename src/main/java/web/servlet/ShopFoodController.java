package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import web.biz.ShopFoodService;
import web.biz.ShopOrderService;
import web.model.exceptions.ErrorCode;
import web.tools.MyMessage;
import web.tools.MyResponse;

import java.util.List;

/**
 * 商户增删改查食物模块
 * Created by Alan on 2016/12/6.
 */
@RequestMapping("shop")
public class ShopFoodController {
    @Autowired
    private ShopFoodService service;

    @RequestMapping(
            value = "addFood",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addFood(@RequestParam(value = "name") String name,
                          @RequestParam(value = "pic") MultipartFile pic,
                          @RequestParam(value = "typeid") String typeID,
                          @RequestParam(value = "price") double price,
                          @RequestParam(value = "extra") List<String> foodIDList) {
        MyMessage myMessage = service.addFood(name, pic, Integer.parseInt(typeID), price, foodIDList);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add food");
        }
    }
}

package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import web.biz.ShopFoodService;
import web.biz.ShopOrderService;
import web.model.exceptions.ErrorCode;
import web.tools.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商户增删改查食物模块
 * Created by Alan on 2016/12/6.
 */
@Controller
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
                          @RequestParam(value = "price") String price,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "extra") String foodIDJsonList) {

        JsonListConverter<String> jsonListConverter = new JsonListConverter<>();
        List<String> foodIDList = jsonListConverter.getList(foodIDJsonList, String.class);
        MyMessage myMessage = service.addFood(name, pic, Integer.parseInt(typeID), MyConverter.getBigDecimal(price), foodIDList, description);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add food");
        }
    }

    @RequestMapping(
            value = "deleteFood",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String deleteFood(HttpServletRequest request) {
        String foodID = request.getParameter("foodid");
        int intFoodID = Integer.parseInt(foodID);

        MyMessage myMessage = service.deleteFood(intFoodID);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to delete food");
        }
    }

    @RequestMapping(
            value = "modFood",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String modFood(@RequestParam(value = "foodid") String foodID,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "pic") MultipartFile pic,
                          @RequestParam(value = "price") String price,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "extra") String foodIDJsonList) {
        JsonListConverter<String> jsonListConverter = new JsonListConverter<>();
        List<String> foodIDList = jsonListConverter.getList(foodIDJsonList, String.class);
        MyMessage myMessage = service.modFood(Integer.parseInt(foodID), name, pic, MyConverter.getBigDecimal(price), foodIDList, description);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to modify food");
        }
    }

    @RequestMapping(
            value = "addType",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String addType(@RequestParam(value = "name") String name, @RequestParam(value = "pic") MultipartFile pic) {
        MyMessage myMessage = service.addType(name, pic);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add type");
        }
    }

    @RequestMapping(
            value = "deleteType",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String deleteType(HttpServletRequest request) {
        String typeID = request.getParameter("typeid");
        int intTypeID = Integer.parseInt(typeID);

        MyMessage myMessage = service.deleteFood(intTypeID);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to delete type");
        }
    }

    @RequestMapping(
            value = "modType",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String modType(@RequestParam(value = "typeid") String typeID, @RequestParam(value = "name") String name, @RequestParam(value = "pic") MultipartFile pic) {
        MyMessage myMessage = service.modType(Integer.parseInt(typeID), name, pic);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to modify type");
        }
    }
}

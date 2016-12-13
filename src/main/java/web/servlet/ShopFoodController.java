package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import web.biz.ShopFoodService;
import web.model.exceptions.ErrorCode;
import web.model.vo.AddFoodVO;
import web.model.vo.ModFoodVO;
import web.tools.*;

import javax.servlet.http.HttpServletRequest;
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
    public String addFood(@RequestBody AddFoodVO addFoodVO) {

        MyMessage myMessage =
                service.addFood(
                        addFoodVO.getName(),
                        Integer.parseInt(addFoodVO.getTypeid()),
                        addFoodVO.getPrice(),
                        addFoodVO.getExtra(),
                        addFoodVO.getDescription());
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to add food");
        }
    }

    @RequestMapping(
            value = "uploadFoodPic",
            method = RequestMethod.POST
    )
    @ResponseBody
    public String uploadFoodPic(@RequestParam(value = "pic") MultipartFile pic, @RequestParam(value = "foodid") String foodID) {
        MyMessage myMessage = service.addFoodPic(MyConverter.getInt(foodID), pic);
        if (myMessage.isSuccess()) {
            return MyResponse.success();
        } else {
            return MyResponse.failure(ErrorCode.SERVER, "fail to upload food picture");
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
    public String modFood(@RequestBody ModFoodVO modFoodVO) {
        MyMessage myMessage =
                service.modFood(
                        Integer.parseInt(modFoodVO.getFoodid()),
                        modFoodVO.getName(),
                        modFoodVO.getPrice(),
                        modFoodVO.getExtra(),
                        modFoodVO.getDescription());
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
    public String addType(@RequestParam(value = "name") String name, @RequestBody MultipartFile pic) {
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

        MyMessage myMessage = service.deleteType(intTypeID);
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

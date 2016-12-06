package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import web.biz.DishService;
import web.model.Food;
import web.model.FoodType;
import web.model.exceptions.ErrorCode;
import web.model.exceptions.NotFoundException;
import web.tools.MyFile;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DishController {

    @Autowired
    private DishService dishManage;

    @RequestMapping(value = "addDish.do", method = RequestMethod.POST)
    public
    @ResponseBody
    String addDish(@RequestParam(value = "name", required = false) String name,
                   @RequestParam(value = "type", required = false) String intType,
                   @RequestParam(value = "price", required = false) String price,
                   @RequestParam(value = "file", required = false) MultipartFile picture,
                   HttpServletRequest request) {

        try {
            int type = Integer.parseInt(intType);
            Food dish = new Food();

            dish.setName(name);

            String path = MyFile.saveFile(picture);
            dish.setPicture(path);
            dish.setPrice(new BigDecimal(price));

            dish.setLike(0);
            dish.setDislike(0);
            dish.setType_id(type);

            this.dishManage.addDish(dish);
            return MyResponse.success(Boolean.TRUE);
        } catch (Exception ex) {
            return MyResponse.failure(ErrorCode.SERVER, ex.getMessage(), Boolean.TRUE);
        }
    }

    @RequestMapping(
            value = {"allDish.do"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String getPostList() {
        List postList = this.dishManage.getAllDish();
        return MyResponse.success(postList);
    }

    @RequestMapping(
            value = {"getMenu"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String getMenu() {
        List<FoodType> menus = this.dishManage.getMenuCategory();
        return MyResponse.success(menus);
    }

    @RequestMapping(
            value = {"getList"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String getDishListInMenu(HttpServletRequest request) {
        String menuid = request.getParameter("menuid");
        int id = Integer.parseInt(menuid);
        try {
            //获取数据
            Map<String,Object> result = new HashMap<>();
            FoodType foodType = this.dishManage.getDishInType(id);
            List<Food> menus = foodType.getFoodList();

            //改变参数名称
            List<Map<String,String>> foodList = new ArrayList<>();
            for (Food food:menus){
                Map<String,String> oneFood = new HashMap<>();
                oneFood.put("foodid",Integer.toString(food.getId()));
                oneFood.put("name",food.getName());
                oneFood.put("pic",food.getPicture());
                oneFood.put("price",food.getPrice().toString());
                oneFood.put("good_num",Integer.toString(food.getLike()));
                oneFood.put("bad_num",Integer.toString(food.getDislike()));
                foodList.add(oneFood);
            }

            //返回参数
            result.put("list", foodList);
            result.put("pic",foodType.getPicture());
            result.put("name",foodType.getName());
            result.put("num",Integer.toString(foodType.getFoodNum()));

            return MyResponse.success(result);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(),e.getMessage(), new ArrayList<>());
        }
    }
}

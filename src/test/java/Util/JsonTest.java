package Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import web.biz.impl.UserImpl;
import web.dao.FoodDao;
import web.dao.impl.FoodDaoImpl;
import web.dao.impl.UserDaoImpl;
import web.model.exceptions.DataConflictException;
import web.model.exceptions.NotFoundException;
import web.model.exceptions.WrongInputException;
import web.model.vo.*;
import web.tools.BeanTool;
import web.tools.JsonListConverter;
import web.tools.MyResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 03/12/2016.
 * All rights reserved.
 */
public class JsonTest {
    @Test
    public void testJson() throws JsonProcessingException {
        FoodDao dishDao = new FoodDaoImpl();
        String result = MyResponse.success(dishDao.getAllFood());
        System.out.println(result);

    }

    @Test
    public void testList2JsonList() {
        FoodItemVO foodItemVO1 = new FoodItemVO("1", "food1", new BigDecimal(1), 1),
                foodItemVO2 = new FoodItemVO("2", "food2", new BigDecimal(2), 1);
        List<FoodItemVO> foodListVOItem = new ArrayList<>();
        foodListVOItem.add(foodItemVO1);
        foodListVOItem.add(foodItemVO2);

        System.out.println(MyResponse.success(foodListVOItem));
    }

    @Test
    public void testJsonList2List() {
        String input = "[{\"foodid\":\"2\",\"num\":1}," +
                "{\"foodid\":\"3\",\"num\":1}," +
                "{\"foodid\":\"4\",\"num\":1}]";

        JsonListConverter<OrderItemVO> jsonListConverter = new JsonListConverter<>();
        List<OrderItemVO> lst = jsonListConverter.getList(input, OrderItemVO.class);

        for (OrderItemVO order : lst) {
            System.out.print(order.getFoodid() + "->");
            System.out.println(order.getNum());
        }
    }


    @Test
    public void testJsonFail() throws JsonProcessingException {
        FoodDao dishDao = new FoodDaoImpl();
        String result = MyResponse.failure("0001", "not found", dishDao.getAllFood());
        System.out.println(result);

    }

    @Test
    public void testLogin() {
        Map<String, String> result = new HashMap<>();
        result.put("userid", "123");
        result.put("username", "1213");
        result.put("avatar", "ava");
        String xx = MyResponse.success(result);
        System.out.println(xx);
    }


    @Test
    public void testRegister2() throws WrongInputException, NotFoundException, DataConflictException {
        UserImpl userService = new UserImpl();
        userService.setUserDao(new UserDaoImpl());
        UserVO newUser = userService.register("123123", "123", "name");
        Map<String, Object> map = BeanTool.bean2Map(newUser);
        System.out.println(map.size());

    }

    @Test
    public void testBeanTool() {
        FoodItemVO foodItemVO1 = new FoodItemVO("1", "food1", new BigDecimal(1), 1),
                foodItemVO2 = new FoodItemVO("2", "food2", new BigDecimal(2), 1);
        List<FoodItemVO> foodListVOItem = new ArrayList<>();
        foodListVOItem.add(foodItemVO1);
        foodListVOItem.add(foodItemVO2);

        EasyOrderVO easyOrderVO = new EasyOrderVO(foodListVOItem, new BigDecimal(3), "2016-11-02");
        Map<String, Object> map = BeanTool.bean2Map(easyOrderVO);
        if (map != null) {
            System.out.println(MyResponse.success(map));
        } else {
            System.out.println("map is null");
        }
    }

    @Test
    public void testBeanConverter() {
        ShopUserExtraVO shopUserExtraVO = new ShopUserExtraVO(1, "1", new BigDecimal(1), "a", "102");
        ShopUserVO shopUserVO = new ShopUserVO("1", "alan", "x.jpg", shopUserExtraVO);
        System.out.println(MyResponse.success(shopUserVO));
        System.out.println(MyResponse.success(shopUserExtraVO));
    }
}

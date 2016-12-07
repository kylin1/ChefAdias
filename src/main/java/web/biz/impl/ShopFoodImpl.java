package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.ShopFoodService;
import web.dao.FoodDao;
import web.model.po.Food;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class ShopFoodImpl implements ShopFoodService {

    @Autowired
    private FoodDao foodDao;

    @Override
    public MyMessage addFood(String name, MultipartFile pic, int typeID, BigDecimal price, List<String> foodIDList, String description) {
        Food food = new Food();
        food.setName(name);
        food.setType_id(typeID);
        food.setPrice(price);
        food.setLike(0);
        food.setDislike(0);
        food.setDescription(description);
//        foodDao.addFood()
        return null;
    }

    @Override
    public MyMessage deleteFood(int foodID) {
        return null;
    }

    @Override
    public MyMessage modFood(String name, MultipartFile pic, double price, List<String> foodIDList) {
        return null;
    }

    @Override
    public MyMessage addType(String name, MultipartFile pic) {
        return null;
    }

    @Override
    public MyMessage deleteType(int typeID) {
        return null;
    }

    @Override
    public MyMessage modType(int typeID, String name, MultipartFile pic) {
        return null;
    }
}

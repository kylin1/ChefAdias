package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.ShopFoodService;
import web.dao.FoodDao;
import web.dao.FoodTypeDao;
import web.model.po.Food;
import web.model.po.FoodType;
import web.tools.MyFile;
import web.tools.MyMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
@Service
public class ShopFoodImpl implements ShopFoodService {

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private FoodTypeDao foodTypeDao;

    @Override
    public MyMessage addFood(String name, MultipartFile pic, int typeID, BigDecimal price, List<String> foodIDList, String description) {
        Food food = new Food();
        food.setName(name);
        food.setType_id(typeID);
        food.setPrice(price);
        food.setLike(0);
        food.setDislike(0);
        food.setDescription(description);
        String newPath = "";
        try {
            newPath = MyFile.saveFile(pic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        food.setPicture(newPath);
//TODO  缺少添加额外菜单的接口
//        foodDao.addFood()
        return null;
    }

    @Override
    public MyMessage deleteFood(int foodID) {
        return foodDao.deleteFood(foodID);
    }

    @Override
    public MyMessage modFood(int foodID, String name, MultipartFile pic, BigDecimal price, List<String> foodIDList, String description) {
        Food food = foodDao.getFood(foodID);
        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);
        String newPath = null;
        try {
            newPath = MyFile.saveFile(pic);
            food.setPicture(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO 缺少添加extraList的接口
        return foodDao.updateFood(food);
    }

    @Override
    public MyMessage addType(String name, MultipartFile pic) {
        FoodType foodType = new FoodType();
        foodType.setName(name);
        try {
            String newPath = MyFile.saveFile(pic);
            foodType.setPicture(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodTypeDao.addFoodType(foodType);
    }

    @Override
    public MyMessage deleteType(int typeID) {
        return foodTypeDao.deleteFoodType(typeID);
    }

    @Override
    public MyMessage modType(int typeID, String name, MultipartFile pic) {
        FoodType foodType = foodTypeDao.getFoodType(typeID);
        try {
            String newPath = MyFile.saveFile(pic);
            foodType.setPicture(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        foodType.setName(name);

        return foodTypeDao.updateFoodType(foodType);
    }

}

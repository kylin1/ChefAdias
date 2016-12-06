package web.biz.impl;

import org.springframework.web.multipart.MultipartFile;
import web.biz.ShopFoodService;
import web.tools.MyMessage;

import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
public class ShopFoodImpl implements ShopFoodService {
    @Override
    public MyMessage addFood(String name, MultipartFile pic, int typeID, double price, List<String> foodIDList) {
        return null;
    }

    @Override
    public MyMessage deleteFood(String foodID) {
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
    public MyMessage modType(String typeID, String name, MultipartFile pic) {
        return null;
    }
}

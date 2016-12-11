package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.CustomerMenuService;
import web.dao.CustomMenuFoodDao;
import web.model.po.CustomMenuFood;
import web.tools.MyMessage;

import java.math.BigDecimal;

/**
 * Created by kylin on 11/12/2016.
 * All rights reserved.
 */
@Service
public class CustomerMenuImpl implements CustomerMenuService {

    @Autowired
    private CustomMenuFoodDao foodDao;

    @Override
    public boolean addMMenu(int type, String name, BigDecimal price) {
        CustomMenuFood food = new CustomMenuFood();
        food.setName(name);
        food.setType(type);
        food.setPrice(price);
        MyMessage myMessage = foodDao.add(food);
        return myMessage.isSuccess();
    }

    @Override
    public boolean modMMenu(int menuId, int type, String name, BigDecimal price) {
        //获取目标数据
        CustomMenuFood food = this.foodDao.get(menuId);
        food.setName(name);
        food.setType(type);
        food.setPrice(price);
        //更新数据
        MyMessage myMessage = foodDao.update(food);
        return myMessage.isSuccess();
    }

    @Override
    public boolean deleteMMenu(int menuId) {
        MyMessage myMessage = foodDao.delete(menuId);
        return myMessage.isSuccess();
    }

    public void setFoodDao(CustomMenuFoodDao foodDao) {
        this.foodDao = foodDao;
    }
}

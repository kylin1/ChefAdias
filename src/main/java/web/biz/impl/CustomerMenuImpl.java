package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.CustOrderItemVO;
import web.biz.CustomerMenuService;
import web.dao.CustomMenuFoodDao;
import web.model.po.CustomMenuFood;
import web.model.vo.CustOrderInfoVO;
import web.tools.MyFile;
import web.tools.MyMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kylin on 11/12/2016.
 * All rights reserved.
 */
@Service
public class CustomerMenuImpl implements CustomerMenuService {

    @Autowired
    private CustomMenuFoodDao foodDao;

    @Override
    public int addMMenu(int type, String name, BigDecimal price) {
        CustomMenuFood food = new CustomMenuFood();
        food.setName(name);
        food.setType(type);
        food.setPrice(price);
        MyMessage myMessage = foodDao.add(food);
        if (myMessage.isSuccess()) {
            return food.getId();
        } else {
            return -1;
        }
    }

    @Override
    public boolean uploadCustFoodPic(int foodID, MultipartFile pic) {
        CustomMenuFood food = foodDao.get(foodID);
        String newPath = "";
        try {
            newPath = MyFile.saveFile(pic);
            food.setPicture(newPath);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    @Override
    public List<CustOrderItemVO> getCustOrderList() {
        //TODO 获取自定义菜单订单列表
        return null;
    }

    @Override
    public CustOrderInfoVO getCustOrder(int orderID) {
        //TODO 获取自定义菜单订单信息
        return null;
    }

    public void setFoodDao(CustomMenuFoodDao foodDao) {
        this.foodDao = foodDao;
    }
}

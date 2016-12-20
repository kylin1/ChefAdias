package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.biz.CustOrderVO;
import web.biz.CustomerMenuService;
import web.dao.*;
import web.model.po.*;
import web.model.vo.CustFoodListItemVO;
import web.model.vo.CustOrderInfoVO;
import web.tools.MyFile;
import web.tools.MyMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 11/12/2016.
 * All rights reserved.
 */
@Service
public class CustomerMenuImpl implements CustomerMenuService {

    @Autowired
    private CustomMenuFoodDao foodDao;

    @Autowired
    private CustomMenuListDao menuListDao;

    @Autowired
    private CustomOrderDao orderDao;

    @Autowired
    private CustomMenuDao menuDao;

    @Autowired
    private UserDao userDao;

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
    public List<CustOrderVO> getCustOrderList(Date date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<CustOrderVO> custOrderList = new ArrayList<>();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, 1);
        Date tomorrow = ca.getTime();
        List<CustomOrder> orderList = orderDao.getOrderInDay(sdf.format(date), sdf.format(tomorrow));
        for (CustomOrder order : orderList) {
            int orderID = order.getId();
            Date time = order.getCreate_time();
            int isFinish = order.getIs_finish();
            int menuID = order.getMenu_id();
            int payType = order.getPay_type();

            List<CustomMenuList> menuList = menuListDao.getMenuListOfMenu(menuID);
            BigDecimal sum = new BigDecimal(0);
            for (CustomMenuList menuItem : menuList) {
                int foodID = menuItem.getFood_id();
                CustomMenuFood food = foodDao.get(foodID);
                sum = sum.add(food.getPrice().multiply(new BigDecimal(menuItem.getNumber())));
            }
            CustomMenu menu = menuDao.getMenuByID(menuID);
            int userID = menu.getUser_id();
            User user = userDao.getUser(userID);

            CustOrderVO custOrderVO = new CustOrderVO(orderID + "", sdf.format(time), user.getUsername(), user.getPhone(), user.getAddress(), isFinish, payType, sum);
            custOrderList.add(custOrderVO);
        }
        return custOrderList;
    }

    @Override
    public CustOrderInfoVO getCustOrder(int orderID) {
        CustomOrder order = orderDao.getOrder(orderID);
        int menuID = order.getMenu_id();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CustomMenu menu = menuDao.getMenuByID(menuID);

        List<CustomMenuList> menuList = menuListDao.getMenuListOfMenu(menuID);
        BigDecimal sum = new BigDecimal(0);
        List<CustFoodListItemVO> foodList = new ArrayList<>();
        for (CustomMenuList menuItem : menuList) {
            int foodID = menuItem.getFood_id();
            CustomMenuFood food = foodDao.get(foodID);
            CustFoodListItemVO foodItem = new CustFoodListItemVO(food.getName(), food.getType(), food.getPrice(), menuItem.getNumber());
            foodList.add(foodItem);
            sum = sum.add(food.getPrice().multiply(new BigDecimal(menuItem.getNumber())));
        }

        int userID = menu.getUser_id();
        User user = userDao.getUser(userID);

        return new CustOrderInfoVO(sdf.format(order.getCreate_time()), user.getUsername(), user.getPhone(), user.getAddress(), order.getIs_finish(), sum, foodList);
    }

    public void setFoodDao(CustomMenuFoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public void setMenuListDao(CustomMenuListDao customMenuListDao) {
        this.menuListDao = customMenuListDao;
    }

    public void setOrderDao(CustomOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setMenuDao(CustomMenuDao customMenuDao) {
        this.menuDao = customMenuDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

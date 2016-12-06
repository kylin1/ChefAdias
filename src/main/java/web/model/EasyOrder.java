package web.model;

import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * Modified by AlanDelip on 05/12/2016.
 * All rights reserved.
 */
public class EasyOrder {

    private int order_id;

    private int user_id;

    //见 FoodList
    private List<OrderItem> food_list;

    //总价
    private double price;

    //下单时间
    private String time;

    public List<OrderItem> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<OrderItem> food_list) {
        this.food_list = food_list;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

package web.model;

import java.math.BigDecimal;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class OrderItem {

    //属于的订单ID
    private int order_id;

    //食物 ID
    private int food_id;

    //食物名
    private String foodName;

    //价格
    private BigDecimal price;

    //数量
    private int food_num;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getFood_num() {
        return food_num;
    }

    public void setFood_num(int food_num) {
        this.food_num = food_num;
    }
}

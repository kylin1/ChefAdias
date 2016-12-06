package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class OrderItem {

    //属于的订单ID
    private int order_id;

    //食物 ID
    private int foodid;

    //食物名
    private String name;

    //价格
    private BigDecimal price;

    //数量
    private int num;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

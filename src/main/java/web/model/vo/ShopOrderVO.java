package web.model.vo;

import web.model.po.OrderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
public class ShopOrderVO {
    private String username;
    private String time;
    private String addr;
    private String phone;
    private int isfinish;
    private int type;
    private BigDecimal price;
    private List<FoodItemVO> food_list;

    public ShopOrderVO(String username, String time, String addr, String phone, int isfinish, int type, BigDecimal price, List<FoodItemVO> food_list) {
        this.username = username;
        this.time = time;
        this.addr = addr;
        this.phone = phone;
        this.isfinish = isfinish;
        this.type = type;
        this.price = price;
        this.food_list = food_list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<FoodItemVO> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<FoodItemVO> food_list) {
        this.food_list = food_list;
    }
}

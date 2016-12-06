package web.model;

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
    private String type;
    private double price;
    private List<OrderItem> food_list;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<OrderItem> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<OrderItem> food_list) {
        this.food_list = food_list;
    }
}

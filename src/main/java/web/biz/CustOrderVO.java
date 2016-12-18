package web.biz;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/15.
 */
public class CustOrderVO {
    String order_id;
    String time;
    String username;
    String phone;
    String addr;
    int isfinish;
    String type;
    BigDecimal price;

    public CustOrderVO() {
    }

    public CustOrderVO(String order_id, String time, String username, String phone, String addr, int isfinish, String type, BigDecimal price) {
        this.order_id = order_id;
        this.time = time;
        this.username = username;
        this.phone = phone;
        this.addr = addr;
        this.isfinish = isfinish;
        this.type = type;
        this.price = price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
package web.biz;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/15.
 */
public class CustOrderVO {
    String orderid;
    String time;
    String username;
    String phone;
    String addr;
    int isfinish;
    int type;
    BigDecimal price;

    public CustOrderVO() {
    }

    public CustOrderVO(String orderid, String time, String username, String phone, String addr, int isfinish, int type, BigDecimal price) {
        this.orderid = orderid;
        this.time = time;
        this.username = username;
        this.phone = phone;
        this.addr = addr;
        this.isfinish = isfinish;
        this.type = type;
        this.price = price;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
}
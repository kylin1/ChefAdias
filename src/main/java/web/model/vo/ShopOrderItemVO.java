package web.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
public class ShopOrderItemVO {
    private String orderid;
    private String username;
    private String time;
    private String addr;
    private int isfinish;
    private String type;
    private BigDecimal price;

    public ShopOrderItemVO(String orderid, String username, String time, String addr, int isfinish, String type, BigDecimal price) {
        this.orderid = orderid;
        this.username = username;
        this.time = time;
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

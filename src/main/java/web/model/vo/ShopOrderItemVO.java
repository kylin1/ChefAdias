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
    private int iscust;
    private int type;
    private BigDecimal price;

    public ShopOrderItemVO(String orderid, String username, String time, String addr, int isfinish, int iscust, int type, BigDecimal price) {
        this.orderid = orderid;
        this.username = username;
        this.time = time;
        this.addr = addr;
        this.isfinish = isfinish;
        this.iscust = iscust;
        this.type = type;
        this.price = price;
    }

    public int getIscust() {
        return iscust;
    }

    public void setIscust(int iscust) {
        this.iscust = iscust;
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

package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/6.
 */
public class OrderResponse {
    private String orderid;
    private BigDecimal price;
    private String time;

    public OrderResponse(String orderid, BigDecimal price, String time) {
        this.orderid = orderid;
        this.price = price;
        this.time = time;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

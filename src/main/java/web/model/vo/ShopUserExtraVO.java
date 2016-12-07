package web.model.vo;

import java.math.BigDecimal;

/**
 * 商户看到的用户额外信息
 * Created by Alan on 2016/12/7.
 */
public class ShopUserExtraVO {
    int bowl;
    String tick;
    BigDecimal price;
    String addr;
    String phone;

    public ShopUserExtraVO(int bowl, String tick, BigDecimal price, String addr, String phone) {
        this.bowl = bowl;
        this.tick = tick;
        this.price = price;
        this.addr = addr;
        this.phone = phone;
    }

    public int getBowl() {
        return bowl;
    }

    public void setBowl(int bowl) {
        this.bowl = bowl;
    }

    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
}

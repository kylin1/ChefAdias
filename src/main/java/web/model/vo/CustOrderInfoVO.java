package web.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/15.
 */
public class CustOrderInfoVO {
    String time;
    String username;
    String phone;
    String addr;
    int isfinish;
    BigDecimal price;
    List<CustFoodListItemVO> custfood_list;

    public CustOrderInfoVO() {
    }

    public CustOrderInfoVO(String time, String username, String phone, String addr, int isfinish, BigDecimal price, List<CustFoodListItemVO> custfood_list) {
        this.time = time;
        this.username = username;
        this.phone = phone;
        this.addr = addr;
        this.isfinish = isfinish;
        this.price = price;
        this.custfood_list = custfood_list;
    }

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<CustFoodListItemVO> getCustfood_list() {
        return custfood_list;
    }

    public void setCustfood_list(List<CustFoodListItemVO> custfood_list) {
        this.custfood_list = custfood_list;
    }
}

package web.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
public class AddOrderVO {
    int userid;
    String time;
    List<OrderItemVO> food_list;
    BigDecimal price;
    int ticket_info;
    int bowl_info;
    int pay_type;

    public AddOrderVO(int userid, String time, List<OrderItemVO> food_list, BigDecimal price, int ticket_info, int bowl_info, int pay_type) {
        this.userid = userid;
        this.time = time;
        this.food_list = food_list;
        this.price = price;
        this.ticket_info = ticket_info;
        this.bowl_info = bowl_info;
        this.pay_type = pay_type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<OrderItemVO> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<OrderItemVO> food_list) {
        this.food_list = food_list;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTicket_info() {
        return ticket_info;
    }

    public void setTicket_info(int ticket_info) {
        this.ticket_info = ticket_info;
    }

    public int getBowl_info() {
        return bowl_info;
    }

    public void setBowl_info(int bowl_info) {
        this.bowl_info = bowl_info;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }
}

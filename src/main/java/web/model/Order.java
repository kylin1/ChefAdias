package web.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class Order {

    //订单ID
    private int order_id;

    //用户 ID
    private int user_id;

    //下单时间
    private Date create_time;

    //总价
    private BigDecimal price;

    //食物列表 OrderList
    private List<OrderItem> orderItemList;

    //是否使用月票 0 未使用/1 使用
    private int ticket_info;

    //是否使用碗
    private int bowl_info;

    //0 货到付款 1 在线付款
    private int pay_type;

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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}

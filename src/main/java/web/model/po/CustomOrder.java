package web.model.po;

import java.util.Date;

/**
 * Created by kylin on 16/12/2016.
 * All rights reserved.
 */
public class CustomOrder {

    private int id;

    //自定义菜单ID
    private int menu_id;

    //下单时间
    private Date create_time;

    //是否送到
    private int is_finish;

    //是否使用月票 0 未使用/1 使用
    private int ticket_info;

    //是否使用碗
    private int bowl_info;

    //0 货到付款 1 在线付款
    private int pay_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public Date getTime() {
        return create_time;
    }

    public void setTime(Date time) {
        this.create_time = time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
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

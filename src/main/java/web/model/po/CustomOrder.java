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
    private Date time;

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
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

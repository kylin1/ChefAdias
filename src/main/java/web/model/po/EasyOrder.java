package web.model.po;

/**
 * Created by kylin on 04/12/2016.
 * Modified by AlanDelip on 05/12/2016.
 * All rights reserved.
 */
public class EasyOrder {

    private int id;

    private int order_id;

    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

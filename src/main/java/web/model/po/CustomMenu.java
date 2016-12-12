package web.model.po;

import java.util.Date;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class CustomMenu {

    private int id;

    //用户ID
    private int user_id;

    //列表名称
    private String name;

    //创建时间
    private Date time;

    //酱料
    private String sauce;

    //风味
    private String flavor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}

package web.model.po;

import java.math.BigDecimal;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class Ticket {

    private int id;

    private String name;

    private String description;

    // 有效的时间,例如30天
    private int expire_time;

    // 每日上限
    private BigDecimal daily_upper;

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public BigDecimal getDaily_upper() {
        return daily_upper;
    }

    public void setDaily_upper(BigDecimal daily_upper) {
        this.daily_upper = daily_upper;
    }

}

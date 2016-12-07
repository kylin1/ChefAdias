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
    private int expire_day;

    // 每日上限
    private BigDecimal daily_upper;

    public int getExpire_day() {
        return expire_day;
    }

    public void setExpire_day(int expire_day) {
        this.expire_day = expire_day;
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

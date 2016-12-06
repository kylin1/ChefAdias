package web.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class Ticket {

    private String name;

    private String description;

    private Date expire_time;

    private BigDecimal daily_upper;

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

    public Date getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Date expire_time) {
        this.expire_time = expire_time;
    }
}

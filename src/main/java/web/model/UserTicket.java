package web.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class UserTicket {

    private int user_id;

    private int ticket_id;

    private Date bought_time;

    //剩余今日金额
    private BigDecimal remain;

    //到期时间
    private Date expireTime;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Date getBought_time() {
        return bought_time;
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public void setRemain(BigDecimal remain) {
        this.remain = remain;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public void setBought_time(Date bought_time) {
        this.bought_time = bought_time;
    }
}



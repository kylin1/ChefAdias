package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/7.
 */
public class TickInfoVO {
    int id;
    BigDecimal remain_money;
    String expire;

    public TickInfoVO(int id, BigDecimal remain_money, String expire) {
        this.id = id;
        this.remain_money = remain_money;
        this.expire = expire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getRemain_money() {
        return remain_money;
    }

    public void setRemain_money(BigDecimal remain_money) {
        this.remain_money = remain_money;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}

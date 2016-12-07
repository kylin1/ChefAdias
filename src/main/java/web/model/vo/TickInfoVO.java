package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/7.
 */
public class TickInfoVO {
    BigDecimal remain_money;
    String expire;

    public TickInfoVO(BigDecimal remain_money, String expire) {
        this.remain_money = remain_money;
        this.expire = expire;
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

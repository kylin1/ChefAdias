package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/14.
 */
public class CustMenuItemVO {
    String mmenuid;
    String name;
    BigDecimal price;

    public CustMenuItemVO(String mmenuid, String name, BigDecimal price) {
        this.mmenuid = mmenuid;
        this.name = name;
        this.price = price;
    }

    public String getMmenuid() {
        return mmenuid;
    }

    public void setMmenuid(String mmenuid) {
        this.mmenuid = mmenuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/15.
 */
public class CustFoodListItemVO {
    String name;
    int type;
    BigDecimal price;
    int num;

    public CustFoodListItemVO() {
    }

    public CustFoodListItemVO(String name, int type, BigDecimal price, int num) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

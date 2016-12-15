package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/14.
 */
public class CustMenuInfoVO {
    int type;
    String foodid;
    String name;
    String pic;
    BigDecimal price;

    public CustMenuInfoVO(int type, String foodid, String name, String pic, BigDecimal price) {
        this.type = type;
        this.foodid = foodid;
        this.name = name;
        this.pic = pic;
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

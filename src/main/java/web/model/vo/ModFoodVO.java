package web.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/13.
 */
public class ModFoodVO {
    String foodid;
    String description;
    String name;
    BigDecimal price;
    List<String> extra;

    public ModFoodVO() {
    }

    public ModFoodVO(String foodid, String description, String name, BigDecimal price, List<String> extra) {
        this.foodid = foodid;
        this.description = description;
        this.name = name;
        this.price = price;
        this.extra = extra;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getExtra() {
        return extra;
    }

    public void setExtra(List<String> extra) {
        this.extra = extra;
    }
}

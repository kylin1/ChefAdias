package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/7.
 */
public class FoodListVO {
    private String foodid;
    private String name;
    private BigDecimal price;
    private int num;

    public FoodListVO(String foodid, String name, BigDecimal price, int num) {
        this.foodid = foodid;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

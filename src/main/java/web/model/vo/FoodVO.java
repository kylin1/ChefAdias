package web.model.vo;

import java.math.BigDecimal;

/**
 * 在一个食物的种类下面
 * 一个菜品的基本信息
 *
 * Created by Alan on 2016/12/7.
 *
 */
public class FoodVO {
    String foodid;
    String name;
    String pic;
    BigDecimal price;
    int good_num;
    int bad_num;

    public FoodVO(String foodid, String name, String pic, BigDecimal price, int good_num, int bad_num) {
        this.foodid = foodid;
        this.name = name;
        this.pic = pic;
        this.price = price;
        this.good_num = good_num;
        this.bad_num = bad_num;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getGood_num() {
        return good_num;
    }

    public void setGood_num(int good_num) {
        this.good_num = good_num;
    }

    public int getBad_num() {
        return bad_num;
    }

    public void setBad_num(int bad_num) {
        this.bad_num = bad_num;
    }
}

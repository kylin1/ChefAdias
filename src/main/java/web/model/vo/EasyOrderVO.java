package web.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
public class EasyOrderVO {
    List<FoodListVO> food_list;
    BigDecimal price;
    String time;

    public EasyOrderVO(List<FoodListVO> food_list, BigDecimal price, String time) {
        this.food_list = food_list;
        this.price = price;
        this.time = time;
    }

    public List<FoodListVO> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<FoodListVO> food_list) {
        this.food_list = food_list;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

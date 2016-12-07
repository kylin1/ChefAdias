package web.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户订单详情界面
 * Created by Alan on 2016/12/7.
 */
public class UserOrderVO {
    private List<FoodItemVO> food_list;
    private BigDecimal price;
    private String time;

    public UserOrderVO(List<FoodItemVO> food_list, BigDecimal price, String time) {
        this.food_list = food_list;
        this.price = price;
        this.time = time;
    }

    public List<FoodItemVO> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<FoodItemVO> food_list) {
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

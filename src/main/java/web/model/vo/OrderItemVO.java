package web.model.vo;

/**
 * Created by Alan on 2016/12/7.
 */
public class OrderItemVO {
    String foodid;
    int num;

    public OrderItemVO(String foodid, int num) {
        this.foodid = foodid;
        this.num = num;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

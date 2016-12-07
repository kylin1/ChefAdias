package web.model.vo;

import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 */
public class FoodVO {
    String pic;
    String name;
    int num;
    List<FoodInfoVO> list;

    public FoodVO(String pic, String name, int num, List<FoodInfoVO> list) {
        this.pic = pic;
        this.name = name;
        this.num = num;
        this.list = list;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<FoodInfoVO> getList() {
        return list;
    }

    public void setList(List<FoodInfoVO> list) {
        this.list = list;
    }
}

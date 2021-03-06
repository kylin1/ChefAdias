package web.model.vo;

import java.util.List;

/**
 * 一个菜品种类的信息
 * 和里面包含的菜品信息
 *
 * Created by Alan on 2016/12/7.
 */
public class FoodTypeVO {
    String pic;
    String name;
    int num;
    List<FoodVO> list;

    public FoodTypeVO(String pic, String name, int num, List<FoodVO> list) {
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

    public List<FoodVO> getList() {
        return list;
    }

    public void setList(List<FoodVO> list) {
        this.list = list;
    }
}

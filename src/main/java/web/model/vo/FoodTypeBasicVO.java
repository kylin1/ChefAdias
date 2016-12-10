package web.model.vo;

/**
 * 菜品种类VO
 *
 * Created by Alan on 2016/12/7.
 */
public class FoodTypeBasicVO {
    String menuid;
    String pic;
    String name;
    int num;

    public FoodTypeBasicVO(String menuid, String pic, String name, int num) {
        this.menuid = menuid;
        this.pic = pic;
        this.name = name;
        this.num = num;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
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
}

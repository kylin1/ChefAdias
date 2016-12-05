package web.model;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class FoodType {

    //分类 ID
    private int id;

    //分类名
    private String name;

    //图片 Url
    private String picture;

    //该分类下菜品数量
    private int foodNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }
}

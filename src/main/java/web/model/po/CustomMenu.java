package web.model.po;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class CustomMenu {

    private int id;

    //用户ID
    private int user_id;

    //列表名称
    private String name;

    //下面是各项食物的ID与数目对
    private int meal_id;

    private int meal_num;

    private int meat_id;

    private int meat_num;

    private int vegetable_id;

    private int vegetable_num;

    private int snack_id;

    private int snack_num;

    private int sauce_id;

    private int sauce_num;

    private int flavor_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public int getMeal_num() {
        return meal_num;
    }

    public void setMeal_num(int meal_num) {
        this.meal_num = meal_num;
    }

    public int getMeat_id() {
        return meat_id;
    }

    public void setMeat_id(int meat_id) {
        this.meat_id = meat_id;
    }

    public int getMeat_num() {
        return meat_num;
    }

    public void setMeat_num(int meat_num) {
        this.meat_num = meat_num;
    }

    public int getVegetable_id() {
        return vegetable_id;
    }

    public void setVegetable_id(int vegetable_id) {
        this.vegetable_id = vegetable_id;
    }

    public int getVegetable_num() {
        return vegetable_num;
    }

    public void setVegetable_num(int vegetable_num) {
        this.vegetable_num = vegetable_num;
    }

    public int getSnack_id() {
        return snack_id;
    }

    public void setSnack_id(int snack_id) {
        this.snack_id = snack_id;
    }

    public int getSnack_num() {
        return snack_num;
    }

    public void setSnack_num(int snack_num) {
        this.snack_num = snack_num;
    }

    public int getSauce_id() {
        return sauce_id;
    }

    public void setSauce_id(int sauce_id) {
        this.sauce_id = sauce_id;
    }

    public int getSauce_num() {
        return sauce_num;
    }

    public void setSauce_num(int sauce_num) {
        this.sauce_num = sauce_num;
    }

    public int getFlavor_id() {
        return flavor_id;
    }

    public void setFlavor_id(int flavor_id) {
        this.flavor_id = flavor_id;
    }
}

package web.model.po;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
public class CustomMenuList {

    private int id;

    //自定义食物列表ID
    private int menu_id;

    //食物
    private int food_id;

    //食物对应数量
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

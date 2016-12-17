package web.model.vo;

/**
 * Created by Alan on 2016/12/14.
 */
public class AddCustMenuVO {
    String userid;
    String name;
    String mealid;
    int meal_num;
    String vegetableid;
    int vegetable_num;
    String meatid;
    int meat_num;
    String snackid;
    int snack_num;
    String sauceid;
    int sauce_num;
    String flavorid;


    public AddCustMenuVO() {
    }

    public AddCustMenuVO(String userid, String name, String mealid, int meal_num, String vegetableid, int vegetable_num, String meatid, int meat_num, String snackid, int snack_num, String sauceid, int sauce_num, String flavorid) {
        this.userid = userid;
        this.name = name;
        this.mealid = mealid;
        this.meal_num = meal_num;
        this.vegetableid = vegetableid;
        this.vegetable_num = vegetable_num;
        this.meatid = meatid;
        this.meat_num = meat_num;
        this.snackid = snackid;
        this.snack_num = snack_num;
        this.sauceid = sauceid;
        this.sauce_num = sauce_num;
        this.flavorid = flavorid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMealid() {
        return mealid;
    }

    public void setMealid(String mealid) {
        this.mealid = mealid;
    }

    public int getMeal_num() {
        return meal_num;
    }

    public void setMeal_num(int meal_num) {
        this.meal_num = meal_num;
    }

    public String getVegetableid() {
        return vegetableid;
    }

    public void setVegetableid(String vegetableid) {
        this.vegetableid = vegetableid;
    }

    public int getVegetable_num() {
        return vegetable_num;
    }

    public void setVegetable_num(int vegetable_num) {
        this.vegetable_num = vegetable_num;
    }

    public String getMeatid() {
        return meatid;
    }

    public void setMeatid(String meatid) {
        this.meatid = meatid;
    }

    public int getMeat_num() {
        return meat_num;
    }

    public void setMeat_num(int meat_num) {
        this.meat_num = meat_num;
    }

    public String getSnackid() {
        return snackid;
    }

    public void setSnackid(String snackid) {
        this.snackid = snackid;
    }

    public int getSnack_num() {
        return snack_num;
    }

    public void setSnack_num(int snack_num) {
        this.snack_num = snack_num;
    }

    public String getSauceid() {
        return sauceid;
    }

    public void setSauceid(String sauceid) {
        this.sauceid = sauceid;
    }

    public int getSauce_num() {
        return sauce_num;
    }

    public void setSauce_num(int sauce_num) {
        this.sauce_num = sauce_num;
    }

    public String getFlavorid() {
        return flavorid;
    }

    public void setFlavorid(String flavorid) {
        this.flavorid = flavorid;
    }
}

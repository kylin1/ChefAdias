package web.model.vo;

import java.math.BigDecimal;

/**
 * Created by Alan on 2016/12/14.
 */
public class CustMenuDetailVO {
    String meal;
    int meal_num;
    String vegetable;
    int vegetable_num;
    String meat;
    int meat_num;
    String snack;
    int snack_num;
    String sauce;
    int sauce_num;
    String flavor;
    BigDecimal price;

    public CustMenuDetailVO() {
    }

    public CustMenuDetailVO(String meal, int meal_num, String vegetable, int vegetable_num, String meat, int meat_num, String snack, int snack_num, String sauce, int sauce_num, String flavor, BigDecimal price) {
        this.meal = meal;
        this.meal_num = meal_num;
        this.vegetable = vegetable;
        this.vegetable_num = vegetable_num;
        this.meat = meat;
        this.meat_num = meat_num;
        this.snack = snack;
        this.snack_num = snack_num;
        this.sauce = sauce;
        this.sauce_num = sauce_num;
        this.flavor = flavor;
        this.price = price;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getMeal_num() {
        return meal_num;
    }

    public void setMeal_num(int meal_num) {
        this.meal_num = meal_num;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }

    public int getVegetable_num() {
        return vegetable_num;
    }

    public void setVegetable_num(int vegetable_num) {
        this.vegetable_num = vegetable_num;
    }

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }

    public int getMeat_num() {
        return meat_num;
    }

    public void setMeat_num(int meat_num) {
        this.meat_num = meat_num;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public int getSnack_num() {
        return snack_num;
    }

    public void setSnack_num(int snack_num) {
        this.snack_num = snack_num;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public int getSauce_num() {
        return sauce_num;
    }

    public void setSauce_num(int sauce_num) {
        this.sauce_num = sauce_num;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

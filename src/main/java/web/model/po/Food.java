package web.model.po;

import java.math.BigDecimal;

/**
 * Created by kylin on 04/12/2016.
 * All rights reserved.
 */
public class Food {

    // 食物 ID
    private int id;

    // 食物名
    private String name;

    // 食物照片
    private String picture;

    // 食物价格
    private BigDecimal price;

    // 好评数量
    private int like;

    // 差评数量
    private int dislike;

    // 食物所处种类的外键
    private int type_id;

    // 额外描述信息
    private String description;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setDescription(String description) {
        this.description = description;
    }
}

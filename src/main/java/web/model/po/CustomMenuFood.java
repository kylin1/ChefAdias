package web.model.po;

import java.math.BigDecimal;

/**
 * Created by kylin on 08/12/2016.
 * All rights reserved.
 */
public class CustomMenuFood {

    //自定义菜单食物 ID
    private int id;

    //类型1 主食/2 肉类/3 蔬菜/4 小菜/5 酱料/6 风味
    private int type;

    //分类菜名
    private String name;

    //价格
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

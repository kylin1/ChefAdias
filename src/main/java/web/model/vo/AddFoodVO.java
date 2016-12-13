package web.model.vo;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/13.
 */
public class AddFoodVO {
    String name;
    String typeid;
    BigDecimal price;
    String description;
    List<String> extra;

    public AddFoodVO() {
    }

    public AddFoodVO(String name, String typeid, BigDecimal price, String description, List<String> extra) {
        this.name = name;
        this.typeid = typeid;
        this.price = price;
        this.description = description;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getExtra() {
        return extra;
    }

    public void setExtra(List<String> extra) {
        this.extra = extra;
    }

}

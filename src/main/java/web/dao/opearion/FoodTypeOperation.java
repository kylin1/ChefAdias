//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package web.dao.opearion;

import org.apache.ibatis.annotations.Select;
import web.model.FoodType;

import java.util.List;

public interface FoodTypeOperation {

    @Select({"select * from food_type"})
    List<FoodType> getDishMenu();

    @Select({"select * from food_type where `id` = #{id}"})
    FoodType getFoodType(int id);

    @Select({"select count(*) from food where type_id=#{type}"})
    int getDishNumInType(int type);
}

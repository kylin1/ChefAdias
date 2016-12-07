//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package web.dao.opearion;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import web.model.po.FoodType;

import java.util.List;

public interface FoodTypeOperation {

    @Select({"select * from food_type"})
    List<FoodType> getDishMenu();

    @Select({"select * from food_type where `id` = #{id}"})
    FoodType getFoodType(int id);

    @Select({"select count(*) from food where type_id=#{type}"})
    int getDishNumInType(int type);

    @Insert({"insert into `food_type` ( `name`, `picture`) values (#{name}, #{picture})"})
    void save(FoodType newFoodType);

    @Delete({"delete from `food_type` where `id` = #{id}"})
    void delete(int id);

    @Update({"update `food_type` set `name`=#{name}, `picture`=#{picture} where `id`=#{id} "})
    void update(FoodType foodType);
}

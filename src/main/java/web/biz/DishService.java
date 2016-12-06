//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package web.biz;

import web.model.po.Food;
import web.model.po.FoodType;
import web.model.exceptions.NotFoundException;
import web.tools.MyMessage;

import java.util.List;

public interface DishService {


    /**
     * 获取菜品分类
     * @return
     */
    List<FoodType> getMenuCategory();

    /**
     * 获取所有菜单列表
     *
     * @return
     */
    List<Food> getAllDish();

    /**
     * 获取一个种类的菜单列表
     *
     * @param type 分类 ID
     * @return
     * @throws NotFoundException
     */
    FoodType getDishInType(int type) throws NotFoundException;

    MyMessage addDish(Food food);

}

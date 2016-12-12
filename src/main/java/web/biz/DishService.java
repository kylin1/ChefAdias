//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package web.biz;

import web.model.exceptions.NotFoundException;
import web.model.po.Food;
import web.model.vo.FoodVO;
import web.model.vo.FoodTypeBasicVO;
import web.model.vo.FoodTypeVO;
import web.tools.MyMessage;

import java.util.List;

public interface DishService {


    /**
     * 获取菜品分类
     *
     * @return
     */
    List<FoodTypeBasicVO> getMenuCategory();

    /**
     * 获取所有菜单列表
     *
     * @return
     */
    List<FoodVO> getAllDish();

    /**
     * 获取一个种类的菜单列表
     *
     * @param type 分类 ID
     * @return
     * @throws NotFoundException
     */
    FoodTypeVO getDishInType(int type) throws NotFoundException;

    MyMessage addDish(Food food);

}

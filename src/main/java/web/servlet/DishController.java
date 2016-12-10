package web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.biz.DishService;
import web.model.exceptions.NotFoundException;
import web.model.vo.FoodTypeBasicVO;
import web.model.vo.FoodTypeVO;
import web.tools.BeanTool;
import web.tools.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("menu")
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping(
            value = {"allDish"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String getAllDish() {
        List postList = this.dishService.getAllDish();
        return MyResponse.success(postList);
    }

    @RequestMapping(
            value = {"getMenu"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String getMenu() {
        List<FoodTypeBasicVO> menus = this.dishService.getMenuCategory();
        return MyResponse.success(menus);
    }

    @RequestMapping(
            value = {"getList"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public String getDishListInMenu(HttpServletRequest request) {
        String menuid = request.getParameter("menuid");
        int id = Integer.parseInt(menuid);
        try {
            FoodTypeVO foodTypeVO = dishService.getDishInType(id);
            return MyResponse.success(BeanTool.bean2Map(foodTypeVO));

        } catch (NotFoundException e) {
            e.printStackTrace();
            return MyResponse.failure(e.getErrorCode(), e.getMessage());
        }
    }
}

package web.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.biz.UserCustMenuService;
import web.dao.CustomMenuDao;
import web.dao.CustomMenuFoodDao;
import web.dao.CustomMenuListDao;
import web.model.po.CustomMenu;
import web.model.po.CustomMenuFood;
import web.model.po.CustomMenuList;
import web.model.vo.AddCustMenuVO;
import web.model.vo.CustMenuDetailVO;
import web.model.vo.CustMenuInfoVO;
import web.model.vo.CustMenuItemVO;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2016/12/14.
 */
@Service
public class UserMenuImpl implements UserCustMenuService {

    @Autowired
    CustomMenuDao customMenuDao;
    @Autowired
    CustomMenuFoodDao customMenuFoodDao;
    @Autowired
    CustomMenuListDao customMenuListDao;

    @Override
    public List<CustMenuItemVO> getMList(int userID) {
        List<CustomMenu> menuList = customMenuDao.getCustomMenuOfUser(userID);
        List<CustMenuItemVO> menuVOList = new ArrayList<>();
        for (CustomMenu menuItem : menuList) {
            List<CustomMenuList> linkMenuList = customMenuListDao.getMenuListOfMenu(menuItem.getId());
            BigDecimal sum = new BigDecimal(0);
            for (CustomMenuList customItem : linkMenuList) {
                int custFoodID = customItem.getFood_id();
                CustomMenuFood menuFood = customMenuFoodDao.get(custFoodID);
                sum = sum.add(menuFood.getPrice());
            }
            CustMenuItemVO custMenuItemVO = new CustMenuItemVO(menuItem.getId() + "", menuItem.getName(), sum);
            menuVOList.add(custMenuItemVO);
        }
        return menuVOList;
    }

    @Override
    public List<CustMenuInfoVO> getMMenuInfo() {
        List<CustomMenuFood> menuFoodList = customMenuFoodDao.getCustomMenuFood();
        List<CustMenuInfoVO> menuInfoList = new ArrayList<>();
        for (CustomMenuFood foodItem : menuFoodList) {
            CustMenuInfoVO menuInfoVO = new CustMenuInfoVO(foodItem.getType(), foodItem.getName(), foodItem.getPrice());
            menuInfoList.add(menuInfoVO);
        }
        return menuInfoList;
    }

    @Override
    public CustMenuDetailVO getMMenu(int mmenuID) {
        List<CustomMenuList> meunList = customMenuListDao.getMenuListOfMenu(mmenuID);
        BigDecimal sum = new BigDecimal(0);
        CustMenuDetailVO custMenuDetailVO = new CustMenuDetailVO();
        for (CustomMenuList menu : meunList) {
            int custFoodID = menu.getFood_id();
            CustomMenuFood menuFood = customMenuFoodDao.get(custFoodID);
            sum = sum.add(menuFood.getPrice());
            CustomMenuFood food = customMenuFoodDao.get(menu.getFood_id());

            int type = food.getType();
            int num = menu.getNumber();
            switch (type) {
                case 1:
                    custMenuDetailVO.setMeal(food.getName());
                    custMenuDetailVO.setMeal_num(num);
                    break;
                case 2:
                    custMenuDetailVO.setMeat(food.getName());
                    custMenuDetailVO.setMeat_num(num);
                    break;
                case 3:
                    custMenuDetailVO.setVegetable(food.getName());
                    custMenuDetailVO.setVegetable_num(num);
                    break;
                case 4:
                    custMenuDetailVO.setSnack(food.getName());
                    custMenuDetailVO.setSnack_num(num);
                    break;
                case 5:
                    custMenuDetailVO.setSauce(food.getName());
                    custMenuDetailVO.setSauce_num(num);
                    break;
                case 6:
                    custMenuDetailVO.setFlavor(food.getName());
                    break;
                default:
                    break;
            }
        }
        return custMenuDetailVO;
    }

    @Override
    public MyMessage deleteMMenu(int mmenuID) {
        return customMenuDao.deleteCustomMenu(mmenuID);
    }

    @Override
    public MyMessage addMMenu(AddCustMenuVO addMMenuVO) {
        CustomMenu customMenu = new CustomMenu();
        customMenu.setFlavor(addMMenuVO.getFlavor());

        MyMessage myMessage = customMenuDao.addCustomMenu(customMenu);
        return null;
    }
}

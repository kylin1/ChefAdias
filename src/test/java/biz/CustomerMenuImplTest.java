package biz;

import com.google.gson.Gson;
import org.junit.Test;
import web.biz.impl.CustomerMenuImpl;
import web.biz.impl.OrderImpl;
import web.biz.impl.UserMenuImpl;
import web.dao.impl.*;
import web.model.po.CustomMenu;
import web.model.vo.AddCustMenuVO;
import web.model.vo.CustMenuDetailVO;
import web.model.vo.CustMenuInfoVO;
import web.model.vo.CustMenuItemVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kylin on 11/12/2016.
 * All rights reserved.
 */
public class CustomerMenuImplTest {

    private CustomerMenuImpl service = new CustomerMenuImpl();
    private UserMenuImpl userMenu = new UserMenuImpl();
    private OrderImpl orderImpl = new OrderImpl();

    public CustomerMenuImplTest() {
        this.service.setFoodDao(new CustomMenuFoodDaoImpl());
        userMenu.setCustomMenuDao(new CustomMenuDaoImpl());
        userMenu.setCustomMenuFoodDao(new CustomMenuFoodDaoImpl());
        userMenu.setCustomMenuListDao(new CustomMenuListDaoImpl());
    }

    @Test
    public void add() {
        this.service.addMMenu(1, "test", new BigDecimal("12"));
    }


    @Test
    public void update() {
        this.service.modMMenu(14, 2, "test2", new BigDecimal("22"));
    }

    @Test
    public void delete() {
        this.service.deleteMMenu(14);
    }

    @Test
    public void getMList() {
        List<CustMenuItemVO> menuList = userMenu.getMList(1);
        Gson gson = new Gson();
        System.out.println(gson.toJson(menuList));
    }

    @Test
    public void getMMenuInfo() {
        List<CustMenuInfoVO> infoList = userMenu.getMMenuInfo();
        Gson gson = new Gson();
        System.out.println(gson.toJson(infoList));
    }

    @Test
    public void getMMenu() {
        CustMenuDetailVO detailVO = userMenu.getMMenu(1);
        Gson gson = new Gson();
        System.out.println(gson.toJson(detailVO));
    }

    @Test
    public void addMMenu() {
        AddCustMenuVO addCustMenuVO = new AddCustMenuVO(
                "1",
                "new menu1",
                "1",
                1,
                "5",
                2,
                "3",
                1,
                "7",
                2,
                "9",
                1,
                "11"
        );
        userMenu.addMMenu(addCustMenuVO);
    }

    @Test
    public void deleteMMenu() {
        userMenu.deleteMMenu(3);
    }

    @Test
    public void addMOrder() {

    }
}

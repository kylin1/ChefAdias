package biz;

import org.junit.Test;
import web.biz.impl.CustomerMenuImpl;
import web.dao.impl.CustomMenuFoodDaoImpl;

import java.math.BigDecimal;

/**
 * Created by kylin on 11/12/2016.
 * All rights reserved.
 */
public class CustomerMenuImplTest {

    private CustomerMenuImpl service = new CustomerMenuImpl();

    public CustomerMenuImplTest(){
        this.service.setFoodDao(new CustomMenuFoodDaoImpl());
    }

//    @Test
    public void add(){
        this.service.addMMenu(1,"test",new BigDecimal("12"));
    }


//    @Test
    public void update(){
        this.service.modMMenu(14,2,"test2",new BigDecimal("22"));
    }

//    @Test
    public void delete(){
        this.service.deleteMMenu(14);
    }

}

package biz;

import org.junit.Test;
import web.biz.impl.ShopUserImpl;
import web.dao.impl.BowlDaoImpl;

/**
 * Created by kylin on 12/12/2016.
 * All rights reserved.
 */
public class ShopUserTest {

    private ShopUserImpl service = new ShopUserImpl();

    public ShopUserTest(){
        service.setBowlDao(new BowlDaoImpl());

    }

    @Test
    public void test(){
        service.setBowl(1,1);
    }

}

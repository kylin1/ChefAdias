package Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import web.dao.FoodDao;
import web.dao.impl.FoodDaoImpl;
import web.model.OrderItem;
import web.tools.JsonConverter;
import web.tools.MyResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 03/12/2016.
 * All rights reserved.
 */
public class JsonTest {

    @Test
    public void testJson() throws JsonProcessingException {
        FoodDao dishDao = new FoodDaoImpl();
        String result = MyResponse.success(dishDao.getAllFood());
        System.out.println(result);

    }

    @Test
    public void testconvert() throws IOException {
        String input = "[{\"foodid\":\"2\",\"num\":1}," +
                "{\"foodid\":\"3\",\"num\":1}," +
                "{\"foodid\":\"4\",\"num\":1}]";

        List<OrderItem> lst = JsonConverter.getItemList(input);

        System.out.println(lst.size());
        for (OrderItem order : lst) {
            System.out.print(order.getFood_id()+"->");
            System.out.println(order.getFood_num());
        }
    }



    @Test
    public void testJsonFail() throws JsonProcessingException {
        FoodDao dishDao = new FoodDaoImpl();
        String result = MyResponse.failure("0001", "not found", dishDao.getAllFood());
        System.out.println(result);

    }

    @Test
    public void testLogin() {
        Map<String, String> result = new HashMap<>();
        result.put("userid", "123");
        result.put("username", "1213");
        result.put("avatar", "ava");
        String xx = MyResponse.success(result);
        System.out.println(xx);
    }

}

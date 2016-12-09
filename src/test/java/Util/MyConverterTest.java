package Util;

import org.junit.Test;
import web.tools.MyConverter;

/**
 * Created by kylin on 09/12/2016.
 * All rights reserved.
 */
public class MyConverterTest {

    @Test
    public void test(){
        System.out.println(MyConverter.getDate("2015-12-02 12:12:12"));
    }
}

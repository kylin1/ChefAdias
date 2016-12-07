package Util;

import org.junit.Test;
import web.tools.InputDefender;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class DefenderTest {

    @Test
    public void testDate(){
        InputDefender.checkDate("123");
    }
}

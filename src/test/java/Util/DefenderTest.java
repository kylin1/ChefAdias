package Util;

import org.junit.Test;
import web.tools.InputDefender;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class DefenderTest {

//    @Test
    public void testDate(){
        assert !InputDefender.checkDate("123");
        assert !InputDefender.checkDate("2015-123-23");
        assert InputDefender.checkDate("2015-12-03");
    }
}

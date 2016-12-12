package web.tools;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kylin on 05/12/2016.
 * All rights reserved.
 */
public class MyConverter {

    public static Date getDate(String input){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(input);
        }catch (Exception ex){
            //TODO 服务器端验证
            return new Date();
        }
    }

    public static int getInt(String input){
        try {
            return Integer.parseInt(input);
        }catch (Exception ex){
            //TODO 服务器端验证
            return -1;
        }
    }

    public static BigDecimal getBigDecimal(String input){
        try {
            return new BigDecimal(input);
        }catch (Exception ex){
            return new BigDecimal(-1);
        }
    }
}

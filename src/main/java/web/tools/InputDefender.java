package web.tools;

/**
 * Created by kylin on 07/12/2016.
 * All rights reserved.
 */
public class InputDefender {

    /**
     * 检查date是否是符合格式要求的字符串
     * 格式为YYYY-MM-dd
     *
     * @param date
     * @return
     */
    public static boolean checkDate(String date){
        return date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    }

}

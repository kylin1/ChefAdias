package web.tools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;

/**
 * Created by kylin on 03/12/2016.
 * All rights reserved.
 */
public class MyResponse {

    /**
     * 正确,无参数返回
     *
     * @return JSON数据
     */
    public static String success() {
        return success(null);
    }

    /**
     * 获取数据正常返回json字符串
     *
     * @param data 数据
     * @return JSON数据
     */
    public static String success(Object data) {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        JsonElement element = gson.toJsonTree(data);
        object.addProperty("condition", "success");
        object.add("data", element);
        return object.toString();
    }


    /**
     * 获取数据异常，返回错误提示信息
     *
     * @param errorCode 错误代码
     * @param message   用户提示信息
     * @param data      数据
     * @return JSON数据
     */
    public static String failure(String errorCode, String message, Object data) {
        JSONObject object = new JSONObject();
        object.put("condition", "fail");
        object.put("error_code", errorCode);
        object.put("message", message);
        object.put("data", data);
        return object.toString();
    }

    /**
     * 获取数据异常，返回错误提示信息
     *
     * @param errorCode 错误代码
     * @param message   用户提示信息
     * @return JSON数据
     */
    public static String failure(String errorCode, String message) {
        JSONObject object = new JSONObject();
        object.put("condition", "fail");
        object.put("error_code", errorCode);
        object.put("message", message);
        return object.toString();
    }

}

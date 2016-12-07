package web.tools;

import net.sf.json.JSONArray;
import web.model.po.OrderItem;
import web.model.vo.OrderItemVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2016/12/7.
 *
 * @param <T>
 */
public class JsonListConverter<T> {
    /**
     * jsonStringList<T>转List<T>
     *
     * @param jsonStr
     * @param clazz
     * @return List<T>
     */
    public List<T> getList(String jsonStr, Class clazz) {
        List<T> list = new ArrayList<T>();
        JSONArray array = JSONArray.fromObject(jsonStr);
        return JSONArray.toList(array, clazz);
    }
}

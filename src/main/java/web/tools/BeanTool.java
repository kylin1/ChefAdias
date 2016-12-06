package web.tools;

import org.jetbrains.annotations.Nullable;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 利用反射转Bean为Map，用作Http的Response
 * Created by Alan on 2016/12/6.
 */
public class BeanTool {
    /**
     * Bean转Map，形成field:getField()的json格式String
     *
     * @param obj 实体
     * @return map
     */
    @Nullable
    public static Map<String, String> bean2Map(Object obj) {
        Map<String, String> beanMap = new HashMap<>();
        Class entity = obj.getClass();
        try {
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entity);
                Method method = pd.getReadMethod();
                beanMap.put(field.getName(), method.invoke(obj).toString());
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return beanMap;
    }
}
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
     * <p>
     * 2016/11/27 Modified by AlanDelip 能够处理1层嵌套的List
     *
     * @param obj 实体
     * @return map
     */
    @Nullable
    public static Map<String, Object> bean2Map(Object obj) {
        Map<String, Object> beanMap = new HashMap<>();
        Class entity = obj.getClass();
        try {
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entity);
                Method method = pd.getReadMethod();

                if (field.getGenericType().toString().contains("java.util.List")) {
                    beanMap.put(field.getName(), method.invoke(obj));
                } else {
                    beanMap.put(field.getName(), method.invoke(obj).toString());
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return beanMap;
    }
}
package web.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import web.model.po.OrderItem;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JsonConverter {

    public static final ObjectMapper mapper = new ObjectMapper();


    public static JavaType getCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }


    /**
     * List转JsonList
     *
     * @param object 输入
     * @return JSON字符串结果
     * @throws JsonProcessingException
     */
    public static String jsonOfObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

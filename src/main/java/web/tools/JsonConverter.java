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
     * JSON转换辅助函数
     *
     * @param object 输入
     * @return JSON字符串结果
     * @throws JsonProcessingException
     */
    public static String jsonOfObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static List<OrderItem> getItemList(String input) throws IOException {
        input = input.replace("foodid", "food_id");
        input = input.replace("num", "food_num");
        JavaType listType = getCollectionType(List.class, OrderItem.class);
        return mapper.readValue(input, listType);
    }

}

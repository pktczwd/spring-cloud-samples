package org.pankai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by pankai on 2016/11/23.
 */
public class JSONConverter {

    private final static ObjectMapper INSTANCE = new ObjectMapper();

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        INSTANCE.setDateFormat(dateFormat);
        INSTANCE.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
    }

    public static <T> T fromJson(Class<T> targetClass, String value, Class<?>... elementClasses) {
        try {
            if ((elementClasses == null) || (elementClasses.length == 0)) {
                return INSTANCE.readValue(value, targetClass);
            } else {
                return INSTANCE.readValue(value, INSTANCE.getTypeFactory().constructParametrizedType(targetClass, targetClass, elementClasses));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object value) {
        try {
            return INSTANCE.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

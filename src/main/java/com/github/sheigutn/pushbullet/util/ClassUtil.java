package com.github.sheigutn.pushbullet.util;

import com.github.sheigutn.pushbullet.http.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {

    /**
     * Searches for the request class with the response type
     * @param input The class to start with
     * @return The parameterized type it ended with or null
     */
    public static ParameterizedType searchForSuperclassWithResponseType(Class<? extends Request> input) {
        Type genericSuperType = input.getGenericSuperclass();
        while (!(genericSuperType instanceof ParameterizedType) && !genericSuperType.equals(Object.class)) {
            genericSuperType = ((Class<?>) genericSuperType).getGenericSuperclass();
        }
        if(genericSuperType instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperType);
        }
        return null;
    }

    /**
     * Searches for the request class with the {@link org.apache.http.client.methods.HttpUriRequest} type
     * @param input The class to start with
     * @return The parameterized type it ended with or null
     */
    public static ParameterizedType searchForSuperclassWithHttpUriRequestType(Class<? extends Request> input) {
        Type genericSuperType = input.getGenericSuperclass();
        ParameterizedType type = null;
        while (!(genericSuperType instanceof ParameterizedType) && !genericSuperType.equals(Object.class)) {
            genericSuperType = ((Class<?>) genericSuperType).getGenericSuperclass();
        }
        while((genericSuperType instanceof ParameterizedType) && ((type = (ParameterizedType) genericSuperType)).getActualTypeArguments().length < 2) {
            genericSuperType = ((Class<?>) ((ParameterizedType) genericSuperType).getRawType()).getGenericSuperclass();
        }
        return type;
    }
}

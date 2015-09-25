package com.github.sheigutn.pushbullet.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FunctionUtil {

    /**
     * Returns an object of a list where at least one result of the functions matches with the value<br>
     * The U param of the functions is given by another function<br>
     * Yeah, I know, it's complicated
     * @param objects        The object list
     * @param mapperFunction The function
     * @param value          The value to match
     * @param functions      The functions array
     * @param <T>            The object type
     * @param <U>            The mapping type
     * @param <V>            The result type of the array's functions
     * @return An object of the list or null
     */
    @SafeVarargs
    public static <T, U, V> T getFirstWithFunctionWithCondition(List<T> objects, Function<T, U> mapperFunction, V value, Function<U, V>... functions) {
        Optional<T> first = objects.stream()
                .filter(object -> {
                    U methodVal = mapperFunction.apply(object);
                    boolean has = false;
                    for (Function<? super U, V> function : functions) {
                        has = has || value.equals(function.apply(methodVal));
                    }
                    return has;
                }).findFirst();
        return first.isPresent() ? first.get() : null;
    }

    /**
     * Returns an object of a list where at least one result of the functions matches with the value<br>
     * @param objects   The object list
     * @param value     The value to match
     * @param functions The functions array
     * @param <T>       The object type
     * @param <U>       The result type of the array's functions
     * @return An object of the list or null
     */
    @SafeVarargs
    public static <T, U> T getFirstWithCondition(List<T> objects, U value, Function<? super T, U>... functions) {
        Optional<T> first = objects.stream()
                .filter(object -> {
                    boolean has = false;
                    for (Function<? super T, U> function : functions) {
                        has = has || value.equals(function.apply(object));
                    }
                    return has;
                }).findFirst();
        return first.isPresent() ? first.get() : null;
    }
}

package com.github.sheigutn.pushbullet.util;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.ListResponse;
import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.http.defaults.get.ListEverythingRequest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil {

    /**
     * Returns a distinct list from an array
     * @param input The input array
     * @param <T>   The array type
     * @return A distinct list
     */
    public static <T> List<T> distinctList(T[] input) {
        return distinctList(Arrays.asList(input));
    }

    /**
     * Returns a distinct list from a list
     * @param input The input list
     * @param <T>   The list type
     * @return A distinct list
     */
    public static <T> List<T> distinctList(List<T> input) {
        if(input.isEmpty()) return input;
        return input.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Returns a full list of a request that calls /v2/*s (e.g. /v2/pushes, /v2/devices, etc.)
     * @param pushbullet The pushbullet instance
     * @param request    The request object
     * @param function   The function of {@link ListResponse}
     * @param <T>        The result type of the function
     * @return A result object with the result type {@link T}
     */
    public static <T> List<T> fullList(Pushbullet pushbullet, ListItemsRequest request, Function<ListResponse, List<T>> function) {
        ListResponse response = pushbullet.executeRequest(request);
        List<T> returnList = function.apply(response);
        while(response.getCursor() != null) {
            response = pushbullet.executeRequest(request.setCursor(response.getCursor()));
            returnList.addAll(function.apply(response));
        }
        return returnList;
    }

    /**
     * Returns a complete {@link ListResponse} by calling /v2/everything
     * @param pushbullet The pushbullet instance
     * @return A complete {@link ListResponse} object
     */
    public static ListResponse completeListResponse(Pushbullet pushbullet) {
        ListItemsRequest request = new ListEverythingRequest();
        ListResponse response = pushbullet.executeRequest(request);
        ListResponse tempResponse = response;
        while(tempResponse.getCursor() != null) {
            tempResponse = pushbullet.executeRequest(request.setCursor(tempResponse.getCursor()));
            response.addAll(tempResponse);
        }
        response.setCursor(null);
        return response;
    }
}

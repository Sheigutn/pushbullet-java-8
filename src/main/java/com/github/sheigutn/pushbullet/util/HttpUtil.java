package com.github.sheigutn.pushbullet.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class HttpUtil {

    /**
     * Get a header value of {@link HttpResponse} or a default value
     * @param response     The response to get the header from
     * @param headerName   The name of the header
     * @param defaultValue The default value
     * @return The header value or the default value
     */
    public static String getHeaderValue(HttpResponse response, String headerName, String defaultValue) {
        Header header;
        return ((header = response.getFirstHeader(headerName)) != null) ? header.getValue() : defaultValue;
    }
}

package com.github.sheigutn.pushbullet.http;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.util.ListUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true, fluent = true)
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ListRequestBuilder<T> {

    /**
     * The default API array length limit for the list requests
     */
    private final static int DEFAULT_LIMIT = 500;

    /**
     * The {@link Pushbullet} instance
     */
    private final Pushbullet pushbullet;

    /**
     * The request type
     */
    private final ListRequestType<T> request;

    /**
     * The cursor of the request
     */
    private String cursor;

    /**
     * Whether only active items should be included in the list
     */
    private boolean onlyShowActiveItems = true;

    /**
     * Whether only items that were modified after the specified timestamp should be included in the list
     */
    private double modifiedAfter;

    /**
     * The maximum length of the requested list
     */
    private int limit = DEFAULT_LIMIT;

    /**
     * Whether the list should automatically make new requests when the cursor is not null and include the items in the list
     */
    private boolean completeList = false;

    /**
     * Returns a new {@link ListRequestBuilder} instance
     * @param pushbullet      The {@link Pushbullet} instance to use
     * @param listRequestType The type of the request to be used
     * @param <T>             The generic type of the list
     * @return
     */
    public static <T> ListRequestBuilder<T> of(Pushbullet pushbullet, ListRequestType<T> listRequestType) {
        return new ListRequestBuilder<>(pushbullet, listRequestType);
    }

    /**
     * Sets the minimum timestamp for items
     * @param modifiedAfter The minimum timestamp
     * @return This {@link ListRequestBuilder}
     */
    public ListRequestBuilder<T> modifiedAfter(double modifiedAfter) {
        this.modifiedAfter = Math.max(modifiedAfter, 0);
        return this;
    }

    /**
     * Sets the maximum length of the requested list,
     * if completeList is true and cursor is not null,
     * new items will automatically be appended to the list when {@link #execute()} was used
     * @param limit The max limit, maximum = 500
     * @return This {@link ListRequestBuilder}
     */
    public ListRequestBuilder<T> limit(int limit) {
        limit = Math.max(limit, 0);
        this.limit = Math.min(limit, DEFAULT_LIMIT);
        return this;
    }

    /**
     * Executes the request and returns the list
     * @return The list of items
     */
    @SneakyThrows
    public List<T> execute() {
        ListItemsRequest itemRequest = request.getRequestType().newInstance();
        itemRequest
                .setCursor(cursor)
                .setModifiedAfter(modifiedAfter)
                .setLimit(limit)
                .setOnlyShowActiveItems(onlyShowActiveItems);
        return completeList ? ListUtil.fullList(pushbullet, itemRequest, request.getFunction()) : request.getFunction().apply(pushbullet.executeRequest(itemRequest));
    }
}

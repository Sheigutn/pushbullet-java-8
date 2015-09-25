package com.github.sheigutn.pushbullet.http.defaults;

import com.github.sheigutn.pushbullet.items.ListResponse;
import lombok.Data;
import lombok.experimental.Accessors;
import com.github.sheigutn.pushbullet.http.GetRequest;
import org.apache.http.client.utils.URIBuilder;

@Accessors(chain = true)
@Data
public class ListItemsRequest extends GetRequest<ListResponse> {

    private Number modifiedAfter;

    private boolean onlyShowActiveItems = true;

    private String cursor;

    private Number limit;

    public ListItemsRequest(String relativePath) {
        super(relativePath);
    }

    @Override
    public void applyParameters(URIBuilder builder) {
        addParam("modified_after", modifiedAfter, builder);
        addParam("active", onlyShowActiveItems, builder);
        addParam("cursor", cursor, builder);
        addParam("limit", limit, builder);
    }
}

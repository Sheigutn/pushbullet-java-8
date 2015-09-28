package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.Urls;
import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;

public class ListBlocksRequest extends ListItemsRequest {

    public ListBlocksRequest() {
        super(Urls.BLOCKS);
    }
}

package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class ListEverythingRequest extends ListItemsRequest {

    public ListEverythingRequest() {
        super(Urls.EVERYTHING);
    }
}

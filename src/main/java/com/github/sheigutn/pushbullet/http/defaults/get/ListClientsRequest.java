package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.util.Urls;

public class ListClientsRequest extends ListItemsRequest {

    public ListClientsRequest() {
        super(Urls.CLIENTS);
    }
}

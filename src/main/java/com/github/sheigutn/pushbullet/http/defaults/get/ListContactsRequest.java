package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class ListContactsRequest extends ListItemsRequest {

    public ListContactsRequest() {
        super(Urls.CONTACTS);
    }
}

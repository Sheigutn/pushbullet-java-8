package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import lombok.Data;

@Data
public class ListSubscriptionsRequest extends ListItemsRequest {

    public ListSubscriptionsRequest() {
        super(Urls.SUBSCRIPTIONS);
    }
}

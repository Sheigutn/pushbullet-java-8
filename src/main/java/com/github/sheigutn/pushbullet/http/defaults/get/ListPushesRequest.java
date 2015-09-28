package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.http.Urls;
import lombok.Data;

@Data
public class ListPushesRequest extends ListItemsRequest {

    public ListPushesRequest() {
        super(Urls.PUSHES);
    }
}

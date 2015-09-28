package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class DeleteAllPushesRequest extends DeleteRequest<Void> {

    public DeleteAllPushesRequest() {
        super(Urls.PUSHES);
    }
}

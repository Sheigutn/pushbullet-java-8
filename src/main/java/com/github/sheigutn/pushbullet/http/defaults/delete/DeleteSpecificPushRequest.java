package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class DeleteSpecificPushRequest extends DeleteRequest<Void> {

    public DeleteSpecificPushRequest(String pushIdentity) {
        super(Urls.PUSHES + "/" + pushIdentity);
    }
}

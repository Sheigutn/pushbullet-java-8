package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.http.DeleteRequest;

public class DeleteSpecificClientRequest extends DeleteRequest<Void> {

    public DeleteSpecificClientRequest(String clientIdentity) {
        super(Urls.CLIENTS + "/" + clientIdentity);
    }
}

package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class DeleteSpecificChannelRequest extends DeleteRequest<Void> {

    public DeleteSpecificChannelRequest(String channelIdentity) {
        super(Urls.CHANNELS + "/" + channelIdentity);
    }
}

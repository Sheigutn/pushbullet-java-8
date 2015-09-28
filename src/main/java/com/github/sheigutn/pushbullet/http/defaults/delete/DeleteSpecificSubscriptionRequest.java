package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class DeleteSpecificSubscriptionRequest extends DeleteRequest<Void> {

    public DeleteSpecificSubscriptionRequest(String channelIdentity) {
        super(Urls.SUBSCRIPTIONS + "/" + channelIdentity);
    }
}

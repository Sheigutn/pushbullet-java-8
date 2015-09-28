package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.http.Urls;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class SendPushToChannelRequest extends PostRequest<Void> {

    private SendablePush push;

    public SendPushToChannelRequest(SendablePush push) {
        super(Urls.PUSHES);
        this.push = push;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJsonTree(push, push.getClass()), post);
    }
}

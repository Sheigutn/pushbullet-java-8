package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.github.sheigutn.pushbullet.http.Urls;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class SendPushRequest extends PostRequest<Push> {

    private SendablePush object;

    public SendPushRequest(SendablePush push) {
        super(Urls.PUSHES);
        this.object = push;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJsonTree(object, object.getClass()), post);
    }
}

package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.http.Urls;
import com.github.sheigutn.pushbullet.items.channel.Subscription;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class SubscribeToChannelRequest extends PostRequest<Subscription> {

    @SerializedName("channel_tag")
    private String channelTag;

    public SubscribeToChannelRequest(String channelTag) {
        super(Urls.SUBSCRIPTIONS);
        this.channelTag = channelTag;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

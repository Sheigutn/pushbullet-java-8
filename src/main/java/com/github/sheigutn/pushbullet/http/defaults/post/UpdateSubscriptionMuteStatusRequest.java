package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.items.channel.Subscription;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class UpdateSubscriptionMuteStatusRequest extends PostRequest<Subscription> {

    @SerializedName("muted")
    private boolean mute;

    public UpdateSubscriptionMuteStatusRequest(String subscriptionIdentity, boolean mute) {
        super(Urls.SUBSCRIPTIONS + "/" + subscriptionIdentity);
        this.mute = mute;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

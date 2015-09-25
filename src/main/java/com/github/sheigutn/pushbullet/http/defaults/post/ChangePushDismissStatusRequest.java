package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.items.push.sent.SentPush;
import com.github.sheigutn.pushbullet.util.Urls;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.http.client.methods.HttpPost;

public class ChangePushDismissStatusRequest extends PostRequest<SentPush> {

    @SerializedName("dismissed")
    private boolean dismiss;

    public ChangePushDismissStatusRequest(String pushIdentity, boolean dismiss) {
        super(Urls.PUSHES + "/" + pushIdentity);
        this.dismiss = dismiss;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

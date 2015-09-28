package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.http.Urls;
import com.github.sheigutn.pushbullet.items.chat.Chat;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.http.client.methods.HttpPost;

public class UpdateChatMuteStatusRequest extends PostRequest<Chat> {

    @SerializedName("muted")
    private boolean mute;

    public UpdateChatMuteStatusRequest(String chatIdentity, boolean mute) {
        super(Urls.CHATS + "/" + chatIdentity);
        this.mute = mute;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

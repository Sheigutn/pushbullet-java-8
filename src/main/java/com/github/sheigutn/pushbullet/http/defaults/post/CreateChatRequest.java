package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.items.chat.Chat;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class CreateChatRequest extends PostRequest<Chat> {

    private String email;

    public CreateChatRequest(String email) {
        super(Urls.CHATS);
        this.email = email;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

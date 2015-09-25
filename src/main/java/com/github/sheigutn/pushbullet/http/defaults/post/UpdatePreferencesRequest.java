package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.items.user.CurrentUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class UpdatePreferencesRequest extends PostRequest<CurrentUser> {

    private JsonObject preferences;

    public UpdatePreferencesRequest(JsonObject preferences) {
        super(Urls.CURRENT_USER);
        this.preferences = preferences;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

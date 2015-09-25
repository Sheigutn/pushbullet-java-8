package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.items.user.Contact;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
@Deprecated
public class UpdateContactRequest extends PostRequest<Contact> {

    private String name;

    public UpdateContactRequest(String contactIdentity, String name) {
        super(Urls.CONTACTS + "/" + contactIdentity);
        this.name = name;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

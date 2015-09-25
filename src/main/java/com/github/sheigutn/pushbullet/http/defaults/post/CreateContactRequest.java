package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.items.user.Contact;
import com.github.sheigutn.pushbullet.util.Urls;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
@Deprecated
public class CreateContactRequest extends PostRequest<Contact> {

    private String name;

    private String email;

    public CreateContactRequest(String name, String email) {
        super(Urls.CONTACTS);
        this.name = name;
        this.email = email;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

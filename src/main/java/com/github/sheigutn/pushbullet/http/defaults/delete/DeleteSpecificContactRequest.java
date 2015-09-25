package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.http.DeleteRequest;

public class DeleteSpecificContactRequest extends DeleteRequest<Void> {

    public DeleteSpecificContactRequest(String contactIdentity) {
        super(Urls.CONTACTS + "/" + contactIdentity);
    }
}

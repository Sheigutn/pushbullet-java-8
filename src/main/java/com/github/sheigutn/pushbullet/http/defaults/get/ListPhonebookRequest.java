package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.GetRequest;
import com.github.sheigutn.pushbullet.items.device.Phonebook;
import com.github.sheigutn.pushbullet.util.Urls;

public class ListPhonebookRequest extends GetRequest<Phonebook> {

    public ListPhonebookRequest(String deviceIdentity) {
        super(Urls.PHONEBOOK + deviceIdentity);
    }
}

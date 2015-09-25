package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.util.Urls;

public class DeleteSpecificAccountRequest extends DeleteRequest<Void> {

    public DeleteSpecificAccountRequest(String accountIdentity) {
        super(Urls.ACCOUNTS + "/" + accountIdentity);
    }
}

package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.http.DeleteRequest;

public class DeleteSpecificGrantRequest extends DeleteRequest<Void> {

    public DeleteSpecificGrantRequest(String grantIdentity) {
        super(Urls.GRANTS + "/" + grantIdentity);
    }
}

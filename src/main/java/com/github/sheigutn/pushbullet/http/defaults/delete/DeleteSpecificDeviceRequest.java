package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.http.DeleteRequest;

public class DeleteSpecificDeviceRequest extends DeleteRequest<Void> {

    public DeleteSpecificDeviceRequest(String deviceIdentity) {
        super(Urls.DEVICES + "/" + deviceIdentity);
    }
}

package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.http.Urls;

/**
 * @author Flo
 */
public class DeleteSpecificBlockRequest extends DeleteRequest<Void> {

    public DeleteSpecificBlockRequest(String blockIdentity) {
        super(Urls.BLOCKS + "/" + blockIdentity);
    }
}

package com.github.sheigutn.pushbullet.http.defaults.delete;

import com.github.sheigutn.pushbullet.http.DeleteRequest;
import com.github.sheigutn.pushbullet.http.Urls;

public class DeleteSpecificChatRequest extends DeleteRequest<Void> {

    public DeleteSpecificChatRequest(String chatIdentity) {
        super(Urls.CHATS + "/" + chatIdentity);
    }


}

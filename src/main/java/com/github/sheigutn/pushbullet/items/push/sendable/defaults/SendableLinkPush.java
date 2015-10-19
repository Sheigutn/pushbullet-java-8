package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.PushType;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@ToString(callSuper = true)
public class SendableLinkPush extends SendablePush {

    /**
     * The title of this push
     */
    private String title;

    /**
     * The body of this push
     */
    private String body;

    private String url;

    public SendableLinkPush() {
        super(PushType.LINK);
    }

    public SendableLinkPush(String title, String body, String url) {
        this();
        this.title = title;
        this.body = body;
        this.url = url;
    }
}

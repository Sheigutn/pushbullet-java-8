package com.github.sheigutn.pushbullet.items.push.sent.defaults;

import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.github.sheigutn.pushbullet.items.push.PushType;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@ToString(callSuper = true)
public class LinkPush extends Push {

    private LinkPush() {
        setType(PushType.LINK);
    }

    /**
     * The title of the push
     */
    private String title;

    /**
     * The body of the push
     */
    private String body;

    /**
     * The link of this push
     */
    private String url;
}

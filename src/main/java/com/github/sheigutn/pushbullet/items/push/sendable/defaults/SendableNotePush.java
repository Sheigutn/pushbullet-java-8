package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@ToString(callSuper = true)
public class SendableNotePush extends SendablePush {

    /**
     * The title for this push
     */
    private String title;

    /**
     * The body for this push
     */
    private String body;

    public SendableNotePush() {
        super(PushType.NOTE);
    }

    /**
     *
     * @param title The title for this push
     * @param body  The body for this push
     */
    public SendableNotePush(String title, String body) {
        this();
        this.title = title;
        this.body = body;
    }
}

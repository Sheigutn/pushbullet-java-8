package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class NotePush extends SendablePush {

    /**
     * The title for this push
     */
    private String title;

    /**
     * The body for this push
     */
    private String body;

    public NotePush() {
        super(PushType.NOTE);
    }

    /**
     *
     * @param title The title for this push
     * @param body  The body for this push
     */
    public NotePush(String title, String body) {
        this();
        this.title = title;
        this.body = body;
    }
}

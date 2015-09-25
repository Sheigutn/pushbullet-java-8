package com.github.sheigutn.pushbullet.items.push.sent.defaults;

import com.github.sheigutn.pushbullet.items.push.sent.SentPush;
import com.github.sheigutn.pushbullet.items.push.PushType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SentNotePush extends SentPush {

    private SentNotePush() {
        setType(PushType.NOTE);
    }

    /**
     * The title of the note
     */
    private String title;

    /**
     * The body of the note
     */
    private String body;
}

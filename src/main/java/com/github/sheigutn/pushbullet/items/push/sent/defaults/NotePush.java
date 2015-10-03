package com.github.sheigutn.pushbullet.items.push.sent.defaults;

import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.github.sheigutn.pushbullet.items.push.PushType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class NotePush extends Push {

    private NotePush() {
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

package com.github.sheigutn.pushbullet.items.push.sent;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
public class ListItem {

    /**
     * Whether the item is checked or not
     */
    private boolean checked;

    /**
     * The text of the item
     */
    private String text;
}

package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
@Deprecated
public class ListPush extends SendablePush {

    /**
     * The title for this push
     */
    private String title;

    /**
     * The list items for this push
     */
    private List<String> items;

    public ListPush() {
        super(PushType.LIST);
    }

    /**
     *
     * @param title The title for this push
     * @param items The items for this push
     */
    public ListPush(String title, List<String> items) {
        this();
        this.title = title;
        this.items = items;
    }
}

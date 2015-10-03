package com.github.sheigutn.pushbullet.items.push.sent.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sent.ListItem;
import com.github.sheigutn.pushbullet.items.push.sent.Push;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
@Deprecated
public class ListPush extends Push {

    private ListPush() {
        setType(PushType.LIST);
    }

    /**
     * The title of the push
     */
    private String title;

    /**
     * The items of the list
     */
    private List<ListItem> items;

    @Override
    public String getBody() {
        return null;
    }
}

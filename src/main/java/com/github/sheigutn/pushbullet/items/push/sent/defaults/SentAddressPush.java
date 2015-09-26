package com.github.sheigutn.pushbullet.items.push.sent.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sent.SentPush;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Deprecated
public class SentAddressPush extends SentPush {

    private SentAddressPush() {
        setType(PushType.ADDRESS);
    }

    /**
     * The name of the address
     */
    private String name;

    /**
     * The address
     */
    private String address;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getBody() {
        return address;
    }
}

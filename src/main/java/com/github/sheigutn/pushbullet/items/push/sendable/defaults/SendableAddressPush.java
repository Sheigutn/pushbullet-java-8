package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@ToString(callSuper = true)
@Deprecated
public class SendableAddressPush extends SendablePush {

    /**
     * The name of the address
     */
    private String name;

    /**
     * The address
     */
    private String address;

    public SendableAddressPush() {
        super(PushType.ADDRESS);
    }

    public SendableAddressPush(String name, String address) {
        this();
        this.name = name;
        this.address = address;
    }
}

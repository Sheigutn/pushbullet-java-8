package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
@Deprecated
public class AddressPush extends SendablePush {

    /**
     * The name of the address
     */
    private String name;

    /**
     * The address
     */
    private String address;

    public AddressPush() {
        super(PushType.ADDRESS);
    }

    public AddressPush(String name, String address) {
        this();
        this.name = name;
        this.address = address;
    }
}

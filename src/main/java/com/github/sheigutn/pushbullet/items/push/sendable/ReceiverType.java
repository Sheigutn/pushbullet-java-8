package com.github.sheigutn.pushbullet.items.push.sendable;

public enum ReceiverType {

    /**
     * Used if the receiver should be a device
     */
    DEVICE,

    /**
     * Used if the receiver is a user
     */
    EMAIL,

    /**
     * Used if the receiver should be one of your channels
     */
    CHANNEL,

    /**
     * Used if the receiver should be all the users that granted access to one of your oauth clients
     */
    OAUTH_CLIENT;
}

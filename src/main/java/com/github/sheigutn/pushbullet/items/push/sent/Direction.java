package com.github.sheigutn.pushbullet.items.push.sent;

import com.google.gson.annotations.SerializedName;

public enum Direction {

    /**
     * Internal push, e.g. from device 1 to device 2
     */
    @SerializedName("self")
    SELF,

    /**
     * Push to other users or own channels
     */
    @SerializedName("outgoing")
    OUTGOING,

    /**
     * Push from other users, channels or granted clients
     */
    @SerializedName("incoming")
    INCOMING;
}

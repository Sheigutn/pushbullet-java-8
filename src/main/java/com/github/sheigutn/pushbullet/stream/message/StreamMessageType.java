package com.github.sheigutn.pushbullet.stream.message;

import com.google.gson.annotations.SerializedName;

public enum StreamMessageType {

    @SerializedName("nop")
    NOP,

    @SerializedName("push")
    PUSH,

    @SerializedName("tickle")
    TICKLE;
}

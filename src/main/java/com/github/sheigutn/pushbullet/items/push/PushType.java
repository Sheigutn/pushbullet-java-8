package com.github.sheigutn.pushbullet.items.push;

import com.google.gson.annotations.SerializedName;

/**
 * @author Florian
 */
public enum PushType {

    /**
     * Used for note pushes
     */
    @SerializedName("note")
    NOTE,

    /**
     * Used for link pushes
     */
    @SerializedName("link")
    LINK,

    /**
     * Used for file pushes
     */
    @SerializedName("file")
    FILE,

    /**
     * Used for list pushes
     */
    @SerializedName("list")
    LIST,

    /**
     * Used for address pushes
     */
    @SerializedName("address")
    ADDRESS;
}
package com.github.sheigutn.pushbullet.items;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class PushbulletIdentifiable extends PushbulletContainer {

    /**
     * The identity of this object
     */
    @SerializedName("iden")
    private String identity;
}

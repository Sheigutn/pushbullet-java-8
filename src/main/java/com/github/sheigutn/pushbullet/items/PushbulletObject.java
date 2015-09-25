package com.github.sheigutn.pushbullet.items;

import lombok.Data;

@Data
public abstract class PushbulletObject extends PushbulletIdentifiable {

    /**
     * Creation timestamp of this object
     */
    private Number created;

    /**
     * Last modification timestamp of this object
     */
    private Number modified;

    /**
     * Whether this object is active or not
     */
    private boolean active = true;
}

package com.github.sheigutn.pushbullet.items;

import com.github.sheigutn.pushbullet.Pushbullet;
import lombok.Data;

@Data
public abstract class PushbulletContainer {

    /**
     * The pushbullet instance that this object is linked to
     */
    private transient Pushbullet pushbullet;
}

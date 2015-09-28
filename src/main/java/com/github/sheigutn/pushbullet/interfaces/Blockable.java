package com.github.sheigutn.pushbullet.interfaces;

@FunctionalInterface
public interface Blockable {

    /**
     * Used to block this object (most likely a user)
     */
    void block();
}

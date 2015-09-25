package com.github.sheigutn.pushbullet.interfaces;

@FunctionalInterface
public interface Dismissable {

    /**
     * Dismiss the push if it is currently shown
     */
    void dismiss();
}

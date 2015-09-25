package com.github.sheigutn.pushbullet.stream;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.stream.message.StreamMessage;

@FunctionalInterface
public interface PushbulletWebsocketListener {

    /**
     * Called when a new message is received
     * @param pushbullet The pushbullet instance
     * @param message    The received message
     */
    void handle(Pushbullet pushbullet, StreamMessage message);
}

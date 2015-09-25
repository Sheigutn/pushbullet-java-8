package com.github.sheigutn.pushbullet.interfaces;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.http.defaults.post.SendPushRequest;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.*;
import com.github.sheigutn.pushbullet.items.push.sent.SentPush;

import java.util.List;

public interface Pushable {

    /**
     * Used to send a push to this object
     * @param push The push to send to
     * @return An instance of the sent push
     */
    SentPush push(SendablePush push);

    /**
     * Used to send a push to this object
     * @param pushbullet The pushbullet instance
     * @param push       The push to send
     * @return An instance of the sent push
     */
    default SentPush sendPush(Pushbullet pushbullet, SendablePush push) {
        return pushbullet.executeRequest(new SendPushRequest(push));
    }

    /**
     * Used to push a link to this object
     * @param title The title of the push
     * @param body  The body of the push
     * @param url   The link of the push
     * @return An instance of the sent push
     */
    default SentPush pushLink(String title, String body, String url) {
        return push(new LinkPush(title, body, url));
    }

    /**
     * Used to push an address to this object
     * @param name    The name of the address
     * @param address The address
     * @return An instance of the sent push
     */
    @Deprecated
    default SentPush pushAddress(String name, String address) {
        return push(new AddressPush(name, address));
    }

    /**
     * Used to push a note to this object
     * @param title The title of the note
     * @param body  The body of the note
     * @return An instance of the sent push
     */
    default SentPush pushNote(String title, String body) {
        return push(new NotePush(title, body));
    }

    /**
     * Used to push a list to this object
     * @param title The title of the list
     * @param items The items of the list
     * @return An instance of the sent push
     */
    @Deprecated
    default SentPush pushList(String title, List<String> items) {
        return push(new ListPush(title, items));
    }

    /**
     * Used to push a file to this object
     * @param body The body of this push
     * @param file The file of this push
     * @return An instance of the sent push
     */
    default SentPush pushFile(String body, UploadFile file) {
        return push(new FilePush(body, file));
    }
}

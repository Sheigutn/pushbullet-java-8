package com.github.sheigutn.pushbullet.items.channel;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificChannelRequest;
import com.github.sheigutn.pushbullet.http.defaults.post.SendPushRequest;
import com.github.sheigutn.pushbullet.http.defaults.post.SendPushToChannelRequest;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.github.sheigutn.pushbullet.items.push.sendable.ReceiverType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.*;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OwnChannel extends PushbulletObject implements Deletable {

    /**
     * The tag of this channel
     */
    private String tag;

    /**
     * The name of this channel
     */
    private String name;

    /**
     * The description of this channel
     */
    private String description;

    /**
     * The url to the image of this channel
     */
    @SerializedName("image_url")
    private String imageUrl;

    public void push(SendablePush push) {
        if(!isActive()) return;
        push = push.clone().setReceiver(ReceiverType.CHANNEL, tag);
        getPushbullet().executeRequest(new SendPushToChannelRequest(push));
    }

    public void sendPush(Pushbullet pushbullet, SendablePush push) {
        pushbullet.executeRequest(new SendPushRequest(push));
    }

    /**
     * Used to push a link to this channel
     * @param title The title of the push
     * @param body  The body of the push
     * @param url   The link of the push
     */
    public void pushLink(String title, String body, String url) {
        push(new SendableLinkPush(title, body, url));
    }

    /**
     * Used to push an address to this channel
     * @param name    The name of the address
     * @param address The address
     */
    @Deprecated
    public void pushAddress(String name, String address) {
        push(new SendableAddressPush(name, address));
    }

    /**
     * Used to push a note to this channel
     * @param title The title of the note
     * @param body  The body of the note
     */
    public void pushNote(String title, String body) {
        push(new SendableNotePush(title, body));
    }

    /**
     * Used to push a list to this channel
     * @param title The title of the list
     * @param items The items of the list
     */
    @Deprecated
    public void pushList(String title, List<String> items) {
        push(new SendableListPush(title, items));
    }

    /**
     * Used to push a file to this
     * @param body The body of this push
     * @param file The file of this push
     */
    public void pushFile(String body, UploadFile file) {
        push(new SendableFilePush(body, file));
    }

    @Override
    public void delete() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new DeleteSpecificChannelRequest(getIdentity()));
        setActive(false);
    }
}

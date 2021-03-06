package com.github.sheigutn.pushbullet.items.user;

import com.github.sheigutn.pushbullet.http.defaults.post.BlockUserRequest;
import com.github.sheigutn.pushbullet.interfaces.Blockable;
import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.github.sheigutn.pushbullet.interfaces.Pushable;
import com.github.sheigutn.pushbullet.items.PushbulletIdentifiable;
import com.github.sheigutn.pushbullet.items.push.sendable.ReceiverType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUser extends PushbulletIdentifiable implements Pushable, Blockable {

    /**
     * The type of this user
     */
    private String type;

    /**
     * The email of this user
     */
    private String email;

    /**
     * The normalized email of this user
     */
    @SerializedName("email_normalized")
    private String normalizedEmail;

    /**
     * The name of this user
     */
    private String name;

    /**
     * The url to the image of this user
     */
    @SerializedName("image_url")
    private String imageUrl;

    @Override
    public Push push(SendablePush push) {
        push = push.clone();
        push.setReceiver(ReceiverType.EMAIL, getEmail());
        return sendPush(getPushbullet(), push);
    }

    /**
     * Used to block this user
     */
    @Override
    public void block() {
        getPushbullet().executeRequest(new BlockUserRequest(getEmail()));
    }
}

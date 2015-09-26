package com.github.sheigutn.pushbullet.items.user;

import com.github.sheigutn.pushbullet.items.push.sent.SentPush;
import com.github.sheigutn.pushbullet.interfaces.Pushable;
import com.github.sheigutn.pushbullet.items.PushbulletIdentifiable;
import com.github.sheigutn.pushbullet.items.push.sendable.ReceiverType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUser extends PushbulletIdentifiable implements Pushable {

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
    public SentPush push(SendablePush push) {
        push = push.clone();
        push.setReceiver(ReceiverType.EMAIL, getEmail());
        return sendPush(getPushbullet(), push);
    }
}

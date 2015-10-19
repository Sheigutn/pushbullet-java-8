package com.github.sheigutn.pushbullet.items.user;

import com.github.sheigutn.pushbullet.http.defaults.post.BlockUserRequest;
import com.github.sheigutn.pushbullet.interfaces.Blockable;
import com.github.sheigutn.pushbullet.interfaces.Pushable;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.github.sheigutn.pushbullet.items.push.sendable.ReceiverType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends PushbulletObject implements Pushable, Blockable {

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
        if(!isActive()) return null;
        push = push.clone();
        push.setReceiver(ReceiverType.EMAIL, getEmail());
        return sendPush(getPushbullet(), push);
    }

    @Override
    public void block() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new BlockUserRequest(getEmail()));
    }
}

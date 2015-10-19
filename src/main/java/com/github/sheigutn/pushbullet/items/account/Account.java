package com.github.sheigutn.pushbullet.items.account;

import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificAccountRequest;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account extends PushbulletObject implements Deletable {

    /**
     * The type of this account
     */
    private String type;

    /**
     * The id of this account
     */
    private String id;

    /**
     * The email of this account
     */
    private String email;

    /**
     * The normalized email of this account
     */
    @SerializedName("email_normalized")
    private String normalizedEmail;

    /**
     * The name of this account
     */
    private String name;

    /**
     * The url to the image of this account
     */
    @SerializedName("image_url")
    private String imageUrl;

    @Override
    public void delete() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new DeleteSpecificAccountRequest(getIdentity()));
        setActive(false);
    }
}

package com.github.sheigutn.pushbullet.items.user;

import com.github.sheigutn.pushbullet.items.PushbulletIdentifiable;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockedUser extends PushbulletIdentifiable {

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
}

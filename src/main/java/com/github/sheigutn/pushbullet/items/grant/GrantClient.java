package com.github.sheigutn.pushbullet.items.grant;

import com.github.sheigutn.pushbullet.items.PushbulletIdentifiable;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GrantClient extends PushbulletIdentifiable {

    /**
     * The name of this granted client
     */
    private String name;

    /**
     * The url to the image of this granted client
     */
    @SerializedName("image_url")
    private String imageUrl;

    /**
     * The url to the website of this granted client
     */
    @SerializedName("website_url")
    private String websiteUrl;
}

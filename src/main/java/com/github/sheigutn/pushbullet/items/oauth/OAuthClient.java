package com.github.sheigutn.pushbullet.items.oauth;

import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificClientRequest;
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
public class OAuthClient extends PushbulletObject implements Deletable {

    /**
     * The name of this client
     */
    private String name;

    /**
     * The url to the image of this client
     */
    @SerializedName("image_url")
    private String imageUrl;

    /**
     * The url to the website of this client
     */
    @SerializedName("website_url")
    private String websiteUrl;

    /**
     * The uri users should be redirected to
     */
    @SerializedName("redirect_uri")
    private String redirectUri;

    /**
     * The id of this client
     */
    @SerializedName("client_id")
    private String clientId;

    /**
     * The secret of this client
     */
    @SerializedName("client_secret")
    private String clientSecret;

    /**
     * The allowed origin of this client
     */
    @SerializedName("allowed_origin")
    private String allowedOrigin;

    @Override
    public void delete() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new DeleteSpecificClientRequest(getIdentity()));
        setActive(false);
    }
}

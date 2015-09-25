package com.github.sheigutn.pushbullet.items.channel;

import com.github.sheigutn.pushbullet.http.defaults.post.SubscribeToChannelRequest;
import com.github.sheigutn.pushbullet.interfaces.Subscribable;
import com.github.sheigutn.pushbullet.items.PushbulletIdentifiable;
import com.github.sheigutn.pushbullet.items.push.sent.SentPush;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.NONE)
public class ChannelInfo extends PushbulletIdentifiable implements Subscribable {

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

    /**
     * The url to the website of this channel
     */
    @SerializedName("website_url")
    private String websiteUrl;

    /**
     * The subscriber count of this channel
     */
    @SerializedName("subscriber_count")
    private int subscriberCount;

    /**
     * The recent pushes of this channel
     */
    @SerializedName("recent_pushes")
    private List<SentPush> recentPushes;

    public void subscribe() {
        getPushbullet().executeRequest(new SubscribeToChannelRequest(tag));
        subscriberCount++;
    }
}

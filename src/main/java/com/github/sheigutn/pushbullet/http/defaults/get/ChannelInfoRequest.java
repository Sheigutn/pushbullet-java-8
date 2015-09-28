package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.GetRequest;
import com.github.sheigutn.pushbullet.items.channel.ChannelInfo;
import com.github.sheigutn.pushbullet.http.Urls;
import lombok.Data;
import org.apache.http.client.utils.URIBuilder;

@Data
public class ChannelInfoRequest extends GetRequest<ChannelInfo> {

    private String channelTag;

    private boolean noRecentPushes;

    public ChannelInfoRequest(String channelTag, boolean noRecentPushes) {
        super(Urls.CHANNEL_INFO);
        this.channelTag = channelTag;
        this.noRecentPushes = noRecentPushes;
    }

    @Override
    public void applyParameters(URIBuilder builder) {
        addParam("tag", channelTag, builder);
        addParam("no_recent_pushes", noRecentPushes, builder);
    }
}

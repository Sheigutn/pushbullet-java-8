package com.github.sheigutn.pushbullet.items.channel;

import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificSubscriptionRequest;
import com.github.sheigutn.pushbullet.interfaces.Unsubscribable;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.github.sheigutn.pushbullet.http.defaults.post.UpdateSubscriptionMuteStatusRequest;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import com.github.sheigutn.pushbullet.interfaces.Mutable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.NONE)
public class Subscription extends PushbulletObject implements Deletable, Mutable, Unsubscribable {

    /**
     * The channel info of this subscription
     */
    private ChannelInfo channel;

    /**
     * Whether this subscription is muted
     */
    private boolean muted;

    @Override
    public void unsubscribe() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new DeleteSpecificSubscriptionRequest(getIdentity()));
        setActive(false);
    }

    @Override
    public void delete() {
        unsubscribe();
    }

    public void mute() {
        updateMute(true);
    }

    public void unmute() {
        updateMute(false);
    }

    private void updateMute(boolean muteStatus) {
        getPushbullet().executeRequest(new UpdateSubscriptionMuteStatusRequest(getIdentity(), muteStatus));
        muted = muteStatus;
    }
}

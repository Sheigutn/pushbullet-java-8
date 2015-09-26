package com.github.sheigutn.pushbullet.items.push.sent;

import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificPushRequest;
import com.github.sheigutn.pushbullet.http.defaults.post.ChangePushDismissStatusRequest;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import com.github.sheigutn.pushbullet.interfaces.Dismissable;
import com.github.sheigutn.pushbullet.items.push.PushType;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SentPush extends PushbulletObject implements Deletable, Dismissable {

    /**
     * The type of the push
     */
    private PushType type;

    /**
     * If the has already been dimissed
     */
    private boolean dismissed;

    /**
     * The direction the push is sent to
     */
    private Direction direction;

    /**
     * The name of the sender
     */
    @SerializedName("sender_name")
    private String senderName;

    /**
     * The identity of the channel the push was sent to
     */
    @SerializedName("channel_iden")
    private String channelIdentity;

    /**
     * The client identity
     */
    @SerializedName("client_iden")
    private String clientIdentity;

    /**
     * The identity of the device the push was sent to
     */
    @SerializedName("target_device_iden")
    private String targetDeviceIdentity;

    /**
     * The sender's identity
     */
    @SerializedName("sender_iden")
    private String senderIdentity;

    /**
     * The sender's email
     */
    @SerializedName("sender_email")
    private String senderEmail;

    /**
     * The sender's normalized email
     */
    @SerializedName("sender_email_normalized")
    private String normalizedSenderEmail;

    /**
     * The receiver's identity
     */
    @SerializedName("receiver_iden")
    private String receiverIdentity;

    /**
     * The receiver's email
     */
    @SerializedName("receiver_email")
    private String receiverEmail;

    /**
     * The receiver's normalized email
     */
    @SerializedName("receiver_email_normalized")
    private String normalizedReceiverEmail;

    /**
     * The list of awake apps the push was sent to when it was created
     */
    @SerializedName("awake_app_guids")
    private List<String> awakeAppGuids;

    /**
     * A unique identifier for the push, set by the client
     */
    private String guid;

    @Override
    public void delete() {
        if(!isActive()) return;
        DeleteSpecificPushRequest deleteSpecificPushRequest = new DeleteSpecificPushRequest(getIdentity());
        getPushbullet().executeRequest(deleteSpecificPushRequest);
        setActive(false);
    }

    /**
     * Show the push again if it was already dismissed
     */
    public void show() {
        if(!isDismissed()) return;
        getPushbullet().executeRequest(new ChangePushDismissStatusRequest(getIdentity(), false));
        setDismissed(false);
    }

    @Override
    public void dismiss() {
        if(isDismissed()) return;
        getPushbullet().executeRequest(new ChangePushDismissStatusRequest(getIdentity(), true));
        setDismissed(true);
    }
}

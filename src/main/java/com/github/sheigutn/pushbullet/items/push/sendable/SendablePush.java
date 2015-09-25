package com.github.sheigutn.pushbullet.items.push.sendable;

import com.github.sheigutn.pushbullet.items.push.PushType;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public abstract class SendablePush implements Cloneable {

    private final PushType type;

    /**
     * The identity of the device the push should be sent to
     */
    @SerializedName("device_iden")
    @Setter(AccessLevel.NONE)
    private String deviceIdentity;

    /**
     * The email of the user the push should be sent to
     */
    @SerializedName("email")
    @Setter(AccessLevel.NONE)
    private String email;

    /**
     * The tag of the channel the push should be sent to
     */
    @SerializedName("channel_tag")
    @Setter(AccessLevel.NONE)
    private String channelTag;

    /**
     * The identity of the oauth client the push should be sent to
     */
    @SerializedName("client_iden")
    @Setter(AccessLevel.NONE)
    private String clientIdentity;

    /**
     * A unique identifier for this push
     */
    private String guid;

    /**
     * Used to set a receiver for this push
     * @param type  The type of receiver this push should be sent to
     * @param value The channel tag / email / client identity or device identity
     * @return The object the method was executed on
     */
    public SendablePush setReceiver(ReceiverType type, String value) {
        clearReceivers();
        switch (type) {
            case DEVICE:
                deviceIdentity = value;
                break;
            case EMAIL:
                email = value;
                break;
            case CHANNEL:
                channelTag = value;
                break;
            case OAUTH_CLIENT:
                clientIdentity = value;
                break;
        }
        return this;
    }

    /**
     * Used to clear all receivers of this push
     */
    public void clearReceivers() {
        this.deviceIdentity = null;
        this.email = null;
        this.channelTag = null;
        this.clientIdentity = null;
    }

    /**
     * Clone this push
     * @return A clone of this object
     */
    @Override
    @SneakyThrows
    public SendablePush clone() {
        return (SendablePush) super.clone();
    }
}

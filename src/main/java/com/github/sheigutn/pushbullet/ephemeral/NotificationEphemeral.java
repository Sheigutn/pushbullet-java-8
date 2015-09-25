package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class NotificationEphemeral extends Ephemeral {

    public NotificationEphemeral() {
        setType(EphemeralType.NOTIFICATION);
    }

    /**
     * The body of this notification
     */
    private String body;

    /**
     * The icon of this notification, encoded in base64
     */
    private String icon;

    /**
     * The title of this notification
     */
    private String title;

    /**
     * The identity of the device this notification came from
     */
    @SerializedName("source_device_iden")
    private String sourceDeviceIdentity;

    /**
     * The name of the application
     */
    @SerializedName("application_name")
    private String applicationName;

    /**
     * The package name of the app
     */
    @SerializedName("package_name")
    private String packageName;

    /**
     * Whether this notification is dismissable
     */
    private boolean dismissable;

    /**
     * The notification id
     */
    @SerializedName("notification_id")
    private String notificationId;

    /**
     * The notification tag
     */
    @SerializedName("notification_tag")
    private String notificationTag;

    /**
     * Whether the device this notification came from is rooted
     */
    @SerializedName("has_root")
    private boolean rooted;

    /**
     * The version of the installed app
     */
    @SerializedName("client_version")
    private int clientVersion;
}

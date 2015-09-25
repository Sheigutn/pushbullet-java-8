package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class DismissalEphemeral extends Ephemeral {

    public DismissalEphemeral() {
        setType(EphemeralType.DISMISSAL);
    }

    /**
     * The package name of the app
     */
    @SerializedName("package_name")
    private String packageName;

    /**
     * The nofication id
     */
    @SerializedName("notification_id")
    private String notificationId;

    /**
     * The notification tag
     */
    @SerializedName("notification_tag")
    private String notificationTag;
}

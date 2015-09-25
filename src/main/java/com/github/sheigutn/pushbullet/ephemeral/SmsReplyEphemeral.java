package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class SmsReplyEphemeral extends Ephemeral {

    public SmsReplyEphemeral() {
        setType(EphemeralType.SMS_REPLY);
    }

    /**
     * The package name of the app
     */
    @SerializedName("package_name")
    private String packageName;

    /**
     * The identity of the targeted device
     */
    @SerializedName("target_device_iden")
    private String targetDeviceIdentity;

    /**
     * The phone number for the sms
     */
    @SerializedName("conversation_iden")
    private String phoneNumber;

    /**
     * The message of the sms
     */
    private String message;
}

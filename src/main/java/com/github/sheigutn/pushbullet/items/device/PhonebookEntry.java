package com.github.sheigutn.pushbullet.items.device;

import com.github.sheigutn.pushbullet.items.PushbulletContainer;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhonebookEntry extends PushbulletContainer {

    /**
     * The name of the contact
     */
    private String name;

    /**
     * The phone number of this contact
     */
    private String phone;

    /**
     * The type of phone of this contact
     */
    @SerializedName("phone_type")
    private String phoneType;

    /**
     * The device that this contact is linked to
     */
    @Setter(AccessLevel.PROTECTED)
    private Device device;

    /**
     * Send a sms to this contact
     * @param body The body of the sms
     */
    public void sendSMS(String body) {
        device.sendSMS(getPhone(), body);
    }
}

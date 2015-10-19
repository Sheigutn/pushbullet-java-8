package com.github.sheigutn.pushbullet.items.device;

import com.github.sheigutn.pushbullet.ephemeral.ClipEphemeral;
import com.github.sheigutn.pushbullet.ephemeral.SmsReplyEphemeral;
import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificDeviceRequest;
import com.github.sheigutn.pushbullet.http.defaults.get.ListPhonebookRequest;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import com.github.sheigutn.pushbullet.interfaces.Pushable;
import com.github.sheigutn.pushbullet.interfaces.SmsSendable;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.github.sheigutn.pushbullet.items.push.sendable.ReceiverType;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Device extends PushbulletObject implements Deletable, Pushable, SmsSendable {

    /**
     * The nickname of this device
     */
    private String nickname;

    /**
     * Whether the nickname of this device has been generated
     */
    @SerializedName("generated_nickname")
    private boolean nicknameGenerated;

    /**
     * The type of this device
     */
    private String type;

    /**
     * The kind of this device
     */
    private String kind;

    /**
     * The icon of this device
     */
    private String icon;

    /**
     * Whether it is possible to push to this device
     */
    private boolean pushable;

    /**
     * The push token for this device
     */
    @SerializedName("push_token")
    private String pushToken;

    /**
     * The app version of the pushbullet app on this device
     */
    @SerializedName("app_version")
    private int appVersion;

    /**
     * The fingerprint of this device
     */
    @SerializedName("fingerprint")
    private String fingerPrint;

    /**
     * The manufacturer of this device
     */
    private String manufacturer;

    /**
     * The model of this device
     */
    private String model;

    @SerializedName("has_sms")
    private boolean mobileDeviceWithSms;

    public Push push(SendablePush push) {
        if(!isPushable()) return null;
        push = push.clone();
        push.setReceiver(ReceiverType.DEVICE, getIdentity());
        return sendPush(getPushbullet(), push);
    }

    /**
     * Sent a text to the clipboard of the other registered devices that have universal copy paste enabled
     * @param content The text to
     * @param toTypes The types of the devices that should receive the text, leave empty for all
     */
    public void sendToOtherClipboards(String content, String... toTypes) {
        ClipEphemeral push = new ClipEphemeral();
        push.setBody(content)
                .setSourceDeviceIdentity(getIdentity());
        getPushbullet().pushEphemeral(push, toTypes);
    }

    /**
     * Returns all pushes that this device has received
     * @return A list of pushes that this device has received
     */
    public List<Push> getReceivedPushes() {
        return getPushbullet().getAllPushes().stream().filter(push -> getIdentity().equals(push.getTargetDeviceIdentity())).collect(Collectors.toList());
    }

    /**
     * Returns the phonebook of this device
     * @return The phonebook of this device
     */
    public Phonebook getPhonebook() {
        Phonebook phonebook = getPushbullet().executeRequest(new ListPhonebookRequest(getIdentity()));
        phonebook.setDevice(this);
        return phonebook;
    }

    @Override
    public void delete() {
        if(!isActive()) return;
        DeleteSpecificDeviceRequest deleteSpecificDeviceRequest = new DeleteSpecificDeviceRequest(getIdentity());
        getPushbullet().executeRequest(deleteSpecificDeviceRequest);
        setActive(false);
    }

    @Override
    public void sendSMS(String number, String body) {
        if(!isActive()) return;
        if(!isMobileDeviceWithSms()) return;
        SmsReplyEphemeral smsReplyEphemeralPush = new SmsReplyEphemeral();
        smsReplyEphemeralPush
                .setMessage(body)
                .setPackageName("com.pushbullet.android")
                .setPhoneNumber(number)
                .setTargetDeviceIdentity(getIdentity())
                .setSourceUserIdentity(getPushbullet().getCurrentUser().getIdentity());
        getPushbullet().pushEphemeral(smsReplyEphemeralPush);
    }
}

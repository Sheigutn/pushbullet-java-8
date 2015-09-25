package com.github.sheigutn.pushbullet.interfaces;

public interface SmsSendable {

    /**
     * Used to send a sms to this object
     * @param number The number to send this sms to
     * @param body   The content of the sms
     */
    void sendSMS(String number, String body);
}

package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public
class SmsChangedEphemeral extends Ephemeral
{

	public
	SmsChangedEphemeral()
	{
		setType(EphemeralType.SMS_CHANGED);
	}

	/**
	 * The identity of the originating device
	 */
	@SerializedName("source_device_iden")
	private String sourceDeviceIdentity;

	/**
	 * An array of SMS messages.
	 */
	private SmsNotification[] notifications;
}

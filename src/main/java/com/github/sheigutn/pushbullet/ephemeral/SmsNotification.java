package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Note, the originating phone number is not always available through the pushbullet API.
 */
@Accessors(chain = true)
@Data
public
class SmsNotification
{
	@SerializedName("thread_id")
	private String threadId;

	/**
	 * The person/company name that sent this text message (if the sender has an entry in the device's
	 * contacts database), otherwise this is usually the originating phone number (of varying degrees
	 * of normalization).
	 *
	 * e.g. "Hubby"
	 */
	private String title;

	/**
	 * The actual SMS/text message.
	 *
	 * e.g. "Hello"
	 */
	private String body;

	/**
	 * The unix-time (in seconds from 1970) that this message was received by the remote device.
	 *
	 * e.g. 1545606829
	 */
	private Long timestamp;

}

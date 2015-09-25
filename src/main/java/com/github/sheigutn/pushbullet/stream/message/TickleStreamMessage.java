package com.github.sheigutn.pushbullet.stream.message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class TickleStreamMessage extends StreamMessage {

    public TickleStreamMessage() {
        setType(StreamMessageType.TICKLE);
    }

    /**
     * The sub type of this tickle message<br>
     * "push" if something at /v2/pushes has changed<br>
     * "device" if something at /v2/devices has changed
     */
    @SerializedName("subtype")
    private String subType;
}

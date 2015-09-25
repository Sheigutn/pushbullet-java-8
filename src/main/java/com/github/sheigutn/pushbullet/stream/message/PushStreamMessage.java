package com.github.sheigutn.pushbullet.stream.message;

import com.github.sheigutn.pushbullet.ephemeral.Ephemeral;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PushStreamMessage extends StreamMessage {

    public PushStreamMessage() {
        setType(StreamMessageType.PUSH);
    }

    /**
     * The ephemeral that has been transferred with this message
     */
    private Ephemeral push;

    /**
     * The list of targets this message was or should be transferred to
     */
    private List<String> targets;
}

package com.github.sheigutn.pushbullet.stream.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class StreamMessage {

    /**
     * The type of this stream message
     */
    @Setter(AccessLevel.PROTECTED)
    private StreamMessageType type;
}

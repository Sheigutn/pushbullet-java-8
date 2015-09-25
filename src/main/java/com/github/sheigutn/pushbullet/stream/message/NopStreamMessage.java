package com.github.sheigutn.pushbullet.stream.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class NopStreamMessage extends StreamMessage {

    public NopStreamMessage() {
        setType(StreamMessageType.NOP);
    }
}

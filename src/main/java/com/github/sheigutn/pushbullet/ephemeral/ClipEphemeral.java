package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class ClipEphemeral extends Ephemeral {

    public ClipEphemeral() {
        setType(EphemeralType.CLIP);
    }

    /**
     * The content of this clip
     */
    private String body;

    /**
     * The identity of the device this clip came from
     */
    @SerializedName("source_device_iden")
    private String sourceDeviceIdentity;
}

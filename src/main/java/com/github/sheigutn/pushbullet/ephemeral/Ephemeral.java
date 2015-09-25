package com.github.sheigutn.pushbullet.ephemeral;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Ephemeral {

    /**
     * The type of this ephemeral
     */
    @Setter(AccessLevel.PROTECTED)
    private EphemeralType type;

    /**
     * The identity of the user this ephemeral came from
     */
    @SerializedName("source_user_iden")
    private String sourceUserIdentity;

    public enum EphemeralType {

        /**
         * Used for SMS replies
         */
        @SerializedName("messaging_extension_reply")
        SMS_REPLY,

        /**
         * Used for universal copy paste
         */
        @SerializedName("clip")
        CLIP,

        /**
         * Used for notification mirroring
         */
        @SerializedName("mirror")
        NOTIFICATION,

        /**
         * Used for notification dismissal
         */
        @SerializedName("dismissal")
        DISMISSAL;
    }
}

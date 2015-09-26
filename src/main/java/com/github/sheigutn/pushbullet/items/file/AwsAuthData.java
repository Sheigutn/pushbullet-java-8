package com.github.sheigutn.pushbullet.items.file;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AwsAuthData {

    /**
     * The AWS access key id
     */
    @SerializedName("awsaccesskeyid")
    private String awsAccessKeyId;

    /**
     * The access control list for AWS
     */
    @SerializedName("acl")
    private String accessControlList;

    /**
     * The key for AWS
     */
    private String key;

    /**
     * The signature for AWS
     */
    private String signature;

    /**
     * The policy for AWS
     */
    private String policy;

    /**
     * The content type for AWS
     */
    @SerializedName("content-type")
    private String contentType;
}

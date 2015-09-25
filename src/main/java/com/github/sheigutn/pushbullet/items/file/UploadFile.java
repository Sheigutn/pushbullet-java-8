package com.github.sheigutn.pushbullet.items.file;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UploadFile {

    /**
     * The mime type of this file
     */
    @SerializedName("file_type")
    private String fileType;

    /**
     * The name of this file
     */
    @SerializedName("file_name")
    private String fileName;

    /**
     * The url to this file
     */
    @SerializedName("file_url")
    private String fileUrl;

    /**
     * The upload url that this file should be uploaded to
     */
    @SerializedName("upload_url")
    private String uploadUrl;

    /**
     * The data for the upload to AWS
     */
    private AwsAuthData data;
}

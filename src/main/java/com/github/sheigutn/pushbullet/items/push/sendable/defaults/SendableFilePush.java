package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.github.sheigutn.pushbullet.items.push.PushType;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@ToString(callSuper = true)
public class SendableFilePush extends SendablePush {

    /**
     * The body for this push
     */
    private String body;

    /**
     * The mime type of the file
     */
    @SerializedName("file_type")
    private String fileType;

    /**
     * The name of the file
     */
    @SerializedName("file_name")
    private String fileName;

    /**
     * The url of the file
     */
    @SerializedName("file_url")
    private String fileUrl;

    public SendableFilePush() {
        super(PushType.FILE);
    }

    /**
     *
     * @param body The body for this messagse
     * @param file The file to send
     */
    public SendableFilePush(String body, UploadFile file) {
        this();
        this.body = body;
        setFile(file);
    }

    /**
     * @param file The file to send
     * @return The object this method was executed on
     */
    public SendableFilePush setFile(UploadFile file) {
        this.fileType = file.getFileType();
        this.fileName = file.getFileName();
        this.fileUrl = file.getFileUrl();
        return this;
    }
}

package com.github.sheigutn.pushbullet.items.push.sendable.defaults;

import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.github.sheigutn.pushbullet.items.push.PushType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class FilePush extends SendablePush {

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

    public FilePush() {
        super(PushType.FILE);
    }

    /**
     *
     * @param body The body for this messagse
     * @param file The file to send
     */
    public FilePush(String body, UploadFile file) {
        this();
        this.body = body;
        setFile(file);
    }

    /**
     * @param file The file to send
     * @return The object this method was executed on
     */
    public FilePush setFile(UploadFile file) {
        this.fileType = file.getFileType();
        this.fileName = file.getFileName();
        this.fileUrl = file.getFileUrl();
        return this;
    }
}

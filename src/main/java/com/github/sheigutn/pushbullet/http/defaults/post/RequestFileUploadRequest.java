package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpPost;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

@Data
public class RequestFileUploadRequest extends PostRequest<UploadFile> {

    @SerializedName("file_name")
    private String fileName;

    @SerializedName("file_type")
    private String fileType;

    public RequestFileUploadRequest(File file) {
        super(Urls.UPLOAD_REQUEST);
        this.fileName = file.getName();
        this.fileType = new MimetypesFileTypeMap().getContentType(file);
    }

    @Override
    @SneakyThrows
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

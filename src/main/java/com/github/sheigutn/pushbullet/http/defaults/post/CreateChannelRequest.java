package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.items.channel.OwnChannel;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.github.sheigutn.pushbullet.util.Urls;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class CreateChannelRequest extends PostRequest<OwnChannel> {

    private String tag;

    private String name;

    private String description;

    @SerializedName("image_url")
    private String imageUrl;

    public CreateChannelRequest(String tag, String name, String description) {
        super(Urls.CHANNELS);
        this.tag = tag;
        this.name = name;
        this.description = description;
    }

    public CreateChannelRequest(String tag, String name, String description, String imageUrl) {
        this(tag, name, description);
        this.imageUrl = imageUrl;
    }

    public CreateChannelRequest(String tag, String name, String description, UploadFile file) {
        this(tag, name, description, file.getFileUrl());
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

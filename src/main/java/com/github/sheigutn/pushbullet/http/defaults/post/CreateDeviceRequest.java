package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.util.Urls;
import com.github.sheigutn.pushbullet.items.device.Device;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

@Data
public class CreateDeviceRequest extends PostRequest<Device> {

    private String nickname;

    private String type;

    public CreateDeviceRequest(String nickname, String type) {
        super(Urls.DEVICES);
        this.nickname = nickname;
        this.type = type;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

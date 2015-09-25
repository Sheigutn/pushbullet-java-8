package com.github.sheigutn.pushbullet.gson;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.PushbulletContainer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.gsonfire.PostProcessor;
import lombok.Data;

@Data
public class PushbulletContainerPostProcessor implements PostProcessor<PushbulletContainer> {

    private final Pushbullet pushbullet;

    @Override
    public void postDeserialize(PushbulletContainer result, JsonElement src, Gson gson) {
        if(result == null) return;
        result.setPushbullet(pushbullet);
    }

    @Override
    public void postSerialize(JsonElement result, PushbulletContainer src, Gson gson) {

    }
}

package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.stream.message.PushStreamMessage;
import com.github.sheigutn.pushbullet.ephemeral.Ephemeral;
import com.github.sheigutn.pushbullet.util.ListUtil;
import com.github.sheigutn.pushbullet.util.Urls;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.client.methods.HttpPost;

import java.util.List;

@Data
public class SendEphemeralRequest extends PostRequest<Void> {

    private Ephemeral ephemeral;

    private String[] deviceTypes;

    public SendEphemeralRequest(Ephemeral ephemeral) {
        super(Urls.EPHEMERALS);
        this.ephemeral = ephemeral;
    }

    public SendEphemeralRequest(Ephemeral ephemeral, String... deviceTypes) {
        this(ephemeral);
        this.deviceTypes = deviceTypes;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        PushStreamMessage streamMessage = new PushStreamMessage();
        streamMessage.setPush(ephemeral);
        List<String> deviceTypes = ListUtil.distinctList(getDeviceTypes());
        if(!deviceTypes.isEmpty()) {
            streamMessage.setTargets(deviceTypes);
        }
        setJsonBody(gson.toJson(streamMessage), post);
    }
}

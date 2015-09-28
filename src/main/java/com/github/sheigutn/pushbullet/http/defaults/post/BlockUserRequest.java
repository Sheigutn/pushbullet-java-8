package com.github.sheigutn.pushbullet.http.defaults.post;

import com.github.sheigutn.pushbullet.http.PostRequest;
import com.github.sheigutn.pushbullet.http.Urls;
import com.github.sheigutn.pushbullet.items.block.Block;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;

public class BlockUserRequest extends PostRequest<Block> {

    private String email;

    public BlockUserRequest(String userEmail) {
        super(Urls.BLOCKS);
        this.email = userEmail;
    }

    @Override
    public void applyBody(Gson gson, HttpPost post) {
        setJsonBody(gson.toJson(this), post);
    }
}

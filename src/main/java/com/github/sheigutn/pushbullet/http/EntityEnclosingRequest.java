package com.github.sheigutn.pushbullet.http;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public abstract class EntityEnclosingRequest<TResult, TMessage extends HttpEntityEnclosingRequestBase> extends Request<TResult, TMessage> {

    public EntityEnclosingRequest(String relativePath) {
        super(relativePath);
    }

    public void applyBody(Gson gson, TMessage message) {}
}

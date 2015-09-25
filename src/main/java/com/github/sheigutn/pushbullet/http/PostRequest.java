package com.github.sheigutn.pushbullet.http;

import org.apache.http.client.methods.HttpPost;

public class PostRequest<TResult> extends EntityEnclosingRequest<TResult, HttpPost> {

    public PostRequest(String relativePath) {
        super(relativePath);
    }
}

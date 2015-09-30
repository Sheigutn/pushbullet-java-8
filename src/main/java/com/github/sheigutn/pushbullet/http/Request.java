package com.github.sheigutn.pushbullet.http;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

@Getter
@RequiredArgsConstructor
public abstract class Request<TResult, TMessage extends HttpUriRequest> {

    /**
     * The relative path for the request
     */
    private final transient String relativePath;

    /**
     * Used to apply parameters to the {@link URIBuilder}
     * @param builder The builder to apply parameters to
     */
    public void applyParameters(URIBuilder builder) {}

    protected void addParam(String key, Object value, URIBuilder builder) {
        if(value != null) builder.addParameter(key, value.toString());
    }

    protected void setJsonBody(String jsonBody, TMessage message) {
        if(!(message instanceof HttpEntityEnclosingRequest)) return;
        StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        HttpEntityEnclosingRequest request = ((HttpEntityEnclosingRequest) message);
        request.setEntity(entity);
    }

    protected void setJsonBody(@NonNull JsonElement element, TMessage message) {
        setJsonBody(element.toString(), message);
    }
}

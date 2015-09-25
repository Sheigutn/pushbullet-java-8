package com.github.sheigutn.pushbullet.exception;

import lombok.Getter;

public class PushbulletApiException extends RuntimeException {

    /**
     * The api error that occured after during an api request
     */
    @Getter
    private final PushbulletApiError error;

    public PushbulletApiException(PushbulletApiError error) {
        super(error.getMessage());
        this.error = error;
    }
}

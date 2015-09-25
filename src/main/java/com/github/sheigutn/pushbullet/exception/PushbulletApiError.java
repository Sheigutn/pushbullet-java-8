package com.github.sheigutn.pushbullet.exception;

import lombok.Data;

@Data
public class PushbulletApiError {

    /**
     * The type of this error
     */
    private final String type;

    /**
     * The message of this error
     */
    private final String message;

    /**
     * :3
     */
    private final String cat;

    public String toString() {
        return message;
    }

}

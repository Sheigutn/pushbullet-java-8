package com.github.sheigutn.pushbullet.http.defaults.get;

import com.github.sheigutn.pushbullet.http.GetRequest;
import com.github.sheigutn.pushbullet.http.Urls;
import com.github.sheigutn.pushbullet.items.user.CurrentUser;

public class CurrentUserInfoRequest extends GetRequest<CurrentUser> {

    public CurrentUserInfoRequest() {
        super(Urls.CURRENT_USER);
    }
}

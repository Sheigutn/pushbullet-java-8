package com.github.sheigutn.pushbullet.items.grant;

import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificGrantRequest;
import com.github.sheigutn.pushbullet.items.PushbulletObject;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Grant extends PushbulletObject implements Deletable {

    /**
     * The "granted client" of this grant
     */
    private GrantClient client;

    @Override
    public void delete() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new DeleteSpecificGrantRequest(getIdentity()));
        setActive(false);
    }
}

package com.github.sheigutn.pushbullet.items.user;

import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteSpecificContactRequest;
import com.github.sheigutn.pushbullet.interfaces.Deletable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Deprecated
public class Contact extends User implements Deletable {

    /**
     * The status of this contact
     */
    private String status;

    @Override
    public void delete() {
        if(!isActive()) return;
        getPushbullet().executeRequest(new DeleteSpecificContactRequest(getIdentity()));
        setActive(false);
    }
}

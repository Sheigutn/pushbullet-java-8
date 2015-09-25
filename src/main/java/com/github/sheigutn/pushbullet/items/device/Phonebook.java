package com.github.sheigutn.pushbullet.items.device;

import com.github.sheigutn.pushbullet.items.PushbulletContainer;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.NONE)
public class Phonebook extends PushbulletContainer {

    /**
     * The entries of the phonebook
     */
    @SerializedName("phonebook")
    private List<PhonebookEntry> entries;

    /**
     * The device that this phonebook is linked to
     */
    private Device device;

    /**
     * Sets the device of this phonebook
     * @param device The device this phonebook should be linked to
     */
    protected void setDevice(Device device) {
        this.device = device;
        entries.forEach(entry -> entry.setDevice(device));
    }
}

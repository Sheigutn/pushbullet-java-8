package com.github.sheigutn.pushbullet.items;

import com.github.sheigutn.pushbullet.items.account.Account;
import com.github.sheigutn.pushbullet.items.block.Block;
import com.github.sheigutn.pushbullet.items.channel.OwnChannel;
import com.github.sheigutn.pushbullet.items.channel.Subscription;
import com.github.sheigutn.pushbullet.items.chat.Chat;
import com.github.sheigutn.pushbullet.items.device.Device;
import com.github.sheigutn.pushbullet.items.grant.Grant;
import com.github.sheigutn.pushbullet.items.oauth.OAuthClient;
import com.github.sheigutn.pushbullet.items.push.sent.Push;
import com.github.sheigutn.pushbullet.items.user.Contact;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListResponse {

    /**
     * The list of accounts
     */
    private List<Account> accounts;

    /**
     * The list of blocked users
     */
    private List<Block> blocks;

    /**
     * The list of own channels
     */
    private List<OwnChannel> channels;

    /**
     * The list of own chats
     */
    private List<Chat> chats;

    /**
     * The list of created oauth clients
     */
    private List<OAuthClient> clients;

    /**
     * The list of contacts
     */
    private List<Contact> contacts;

    /**
     * The list of devices
     */
    private List<Device> devices;

    /**
     * The list of granted clients
     */
    private List<Grant> grants;

    /**
     * The list of sent or received pushes
     */
    private List<Push> pushes;

    /**
     * The list of subscriptions
     */
    private List<Subscription> subscriptions;

    /**
     * Cursor for pagination
     */
    private String cursor;

    public ListResponse addAll(ListResponse other) {
        accounts.addAll(other.getAccounts());
        channels.addAll(other.getChannels());
        chats.addAll(other.getChats());
        clients.addAll(other.getClients());
        contacts.addAll(other.getContacts());
        devices.addAll(other.getDevices());
        grants.addAll(other.getGrants());
        pushes.addAll(other.getPushes());
        subscriptions.addAll(other.getSubscriptions());
        return this;
    }
}

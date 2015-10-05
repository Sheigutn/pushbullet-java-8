package com.github.sheigutn.pushbullet.http;

import com.github.sheigutn.pushbullet.http.defaults.ListItemsRequest;
import com.github.sheigutn.pushbullet.http.defaults.get.*;
import com.github.sheigutn.pushbullet.items.ListResponse;
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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ListRequestType<TResponseType> {

    /**
     * The request class
     */
    private final Class<? extends ListItemsRequest> requestType;

    /**
     * The function to use for the result
     */
    private final Function<ListResponse, List<TResponseType>> function;

    /**
     * Lists all accounts
     */
    public final static ListRequestType<Account> LIST_ACCOUNTS = new ListRequestType<>(ListAccountsRequest.class, ListResponse::getAccounts);

    /**
     * Lists all blocks
     */
    public final static ListRequestType<Block> LIST_BLOCKS = new ListRequestType<>(ListBlocksRequest.class, ListResponse::getBlocks);

    /**
     * Lists all own channels
     */
    public final static ListRequestType<OwnChannel> LIST_CHANNELS = new ListRequestType<>(ListChannelsRequest.class, ListResponse::getChannels);

    /**
     * Lists all chats
     */
    public final static ListRequestType<Chat> LIST_CHATS = new ListRequestType<>(ListChatsRequest.class, ListResponse::getChats);

    /**
     * Lists all oauth clients
     */
    public final static ListRequestType<OAuthClient> LIST_CLIENTS = new ListRequestType<>(ListClientsRequest.class, ListResponse::getClients);

    /**
     * Lists all contacts
     */
    public final static ListRequestType<Contact> LIST_CONTACTS = new ListRequestType<>(ListContactsRequest.class, ListResponse::getContacts);

    /**
     * Lists all devices
     */
    public final static ListRequestType<Device> LIST_DEVICES = new ListRequestType<>(ListDevicesRequest.class, ListResponse::getDevices);

    /**
     * Lists all grants
     */
    public final static ListRequestType<Grant> LIST_GRANTS = new ListRequestType<>(ListGrantsRequest.class, ListResponse::getGrants);

    /**
     * Lists all pushes
     */
    public final static ListRequestType<Push> LIST_PUSHES = new ListRequestType<>(ListPushesRequest.class, ListResponse::getPushes);

    /**
     * Lists all subscriptions
     */
    public final static ListRequestType<Subscription> LIST_SUBSCRIPTIONS = new ListRequestType<>(ListSubscriptionsRequest.class, ListResponse::getSubscriptions);

}

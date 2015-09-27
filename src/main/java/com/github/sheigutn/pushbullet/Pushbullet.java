package com.github.sheigutn.pushbullet;

import com.github.sheigutn.pushbullet.cryptography.Encryption;
import com.github.sheigutn.pushbullet.ephemeral.*;
import com.github.sheigutn.pushbullet.exception.PushbulletApiError;
import com.github.sheigutn.pushbullet.exception.PushbulletApiException;
import com.github.sheigutn.pushbullet.gson.PushbulletContainerPostProcessor;
import com.github.sheigutn.pushbullet.gson.RuntimeTypeAdapterFactory;
import com.github.sheigutn.pushbullet.http.EntityEnclosingRequest;
import com.github.sheigutn.pushbullet.http.Request;
import com.github.sheigutn.pushbullet.http.defaults.delete.DeleteAllPushesRequest;
import com.github.sheigutn.pushbullet.http.defaults.get.*;
import com.github.sheigutn.pushbullet.http.defaults.post.*;
import com.github.sheigutn.pushbullet.interfaces.Pushable;
import com.github.sheigutn.pushbullet.items.ListResponse;
import com.github.sheigutn.pushbullet.items.PushbulletContainer;
import com.github.sheigutn.pushbullet.items.account.Account;
import com.github.sheigutn.pushbullet.items.channel.ChannelInfo;
import com.github.sheigutn.pushbullet.items.channel.OwnChannel;
import com.github.sheigutn.pushbullet.items.channel.Subscription;
import com.github.sheigutn.pushbullet.items.chat.Chat;
import com.github.sheigutn.pushbullet.items.device.Device;
import com.github.sheigutn.pushbullet.items.file.AwsAuthData;
import com.github.sheigutn.pushbullet.items.file.UploadFile;
import com.github.sheigutn.pushbullet.items.grant.Grant;
import com.github.sheigutn.pushbullet.items.grant.GrantClient;
import com.github.sheigutn.pushbullet.items.oauth.OAuthClient;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.*;
import com.github.sheigutn.pushbullet.items.push.sent.SentPush;
import com.github.sheigutn.pushbullet.items.push.sent.defaults.*;
import com.github.sheigutn.pushbullet.items.user.ChatUser;
import com.github.sheigutn.pushbullet.items.user.Contact;
import com.github.sheigutn.pushbullet.items.user.CurrentUser;
import com.github.sheigutn.pushbullet.stream.PushbulletWebsocketClient;
import com.github.sheigutn.pushbullet.stream.message.NopStreamMessage;
import com.github.sheigutn.pushbullet.stream.message.PushStreamMessage;
import com.github.sheigutn.pushbullet.stream.message.StreamMessage;
import com.github.sheigutn.pushbullet.stream.message.TickleStreamMessage;
import com.github.sheigutn.pushbullet.util.ClassUtil;
import com.github.sheigutn.pushbullet.util.FunctionUtil;
import com.github.sheigutn.pushbullet.util.HttpUtil;
import com.github.sheigutn.pushbullet.util.ListUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import io.gsonfire.GsonFireBuilder;
import lombok.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Pushbullet implements Pushable {

    /**
     * Base api url for requests
     */
    private static final String API_BASE_URL = "https://api.pushbullet.com/v2";

    /**
     * Access token required for API requests
     */
    @NonNull
    private final String accessToken;

    /**
     * Gson instance for usage with json bodies
     */
    private final Gson gson = new GsonFireBuilder()
            .registerPostProcessor(PushbulletContainer.class, new PushbulletContainerPostProcessor(this))
            .createGsonBuilder()
            .registerTypeAdapterFactory(
                    RuntimeTypeAdapterFactory.of(StreamMessage.class)
                            .registerSubtype(NopStreamMessage.class, "nop")
                            .registerSubtype(TickleStreamMessage.class, "tickle")
                            .registerSubtype(PushStreamMessage.class, "push"))
            .registerTypeAdapterFactory(
                    RuntimeTypeAdapterFactory.of(SentPush.class)
                            .registerSubtype(SentListPush.class, "list")
                            .registerSubtype(SentLinkPush.class, "link")
                            .registerSubtype(SentFilePush.class, "file")
                            .registerSubtype(SentAddressPush.class, "address")
                            .registerSubtype(SentNotePush.class, "note"))
            .registerTypeAdapterFactory(
                    RuntimeTypeAdapterFactory.of(SendablePush.class)
                            .registerSubtype(ListPush.class, "list")
                            .registerSubtype(LinkPush.class, "link")
                            .registerSubtype(FilePush.class, "file")
                            .registerSubtype(AddressPush.class, "address")
                            .registerSubtype(NotePush.class, "note"))
            .registerTypeAdapterFactory(
                    RuntimeTypeAdapterFactory.of(Ephemeral.class)
                            .registerSubtype(ClipEphemeral.class, "clip")
                            .registerSubtype(DismissalEphemeral.class, "dismissal")
                            .registerSubtype(NotificationEphemeral.class, "mirror")
                            .registerSubtype(SmsReplyEphemeral.class, "message_extension_reply"))
            .create();

    /**
     * ExecutorService for asynchronous methods
     */
    @Getter(AccessLevel.NONE)
    private final ExecutorService executorService = Executors.newCachedThreadPool((runnable) -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    });

    /**
     * Limit of operations available to this user
     */
    private int rateLimit;

    /**
     * Operations remaining. 1 Request + 1 Database operation = 5 operations (1 + 1 * 4)
     */
    private int rateRemaining;

    /**
     * Reset timestamp in UNIX seconds since epoch
     */
    private long resetTimestamp;

    /**
     * Used for {@link #getNewPushes()}
     */
    @Getter(AccessLevel.NONE)
    private Number newestModifiedAfter = 0;

    /**
     * @return The user that has the API token
     */
    public CurrentUser getCurrentUser() {
        return executeRequest(new CurrentUserInfoRequest());
    }

    /**
     * Broadcast a String to all device that have universal copy paste enabled
     * @param content The body to broadcast
     * @param types The types of devices that should clip this message
     */
    public void broadcastToClipboards(String content, String... types) {
        pushEphemeral(new ClipEphemeral().setBody(content), types);
    }

    /**
     * Get all accounts of this user
     * @return The list of accounts registered for this user
     */
    public List<Account> getAccounts() {
        return ListUtil.fullList(this, new ListAccountsRequest(), ListResponse::getAccounts);
    }

    /**
     * Get an account with this name or identity
     * @param nameOrIdentity The name or identity of the account
     * @return The account with this name or identity
     */
    public Account getAccount(String nameOrIdentity) {
        return FunctionUtil.getFirstWithCondition(getAccounts(), nameOrIdentity, Account::getName, Account::getIdentity);
    }

    /**
     * Returns channel information for the channel with the specified tag
     * @param tag The specified tag
     * @return A {@link ChannelInfo} Object
     */
    public ChannelInfo getChannelInfo(String tag) {
        return getChannelInfo(tag, false);
    }

    /**
     * Returns channel information for the channel with the specified tag
     * @param tag The specified tag
     * @param noRecentPushes Include recent pushes of this channel in the response
     * @return A {@link ChannelInfo} Object
     */
    public ChannelInfo getChannelInfo(String tag, boolean noRecentPushes) {
        return executeRequest(new ChannelInfoRequest(tag, noRecentPushes));
    }

    /**
     * Create a channel with the specified tag
     * @param tag The tag for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel createChannel(String tag) {
        return createChannel(tag, null);
    }

    /**
     * Create a channel with the specified tag and name
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel createChannel(String tag, String name) {
        return createChannel(tag, name, null);
    }

    /**
     * Create a channel with the specified tag, name and description
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @param description The description for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel createChannel(String tag, String name, String description) {
        return createChannel(tag, name, description, (String) null);
    }

    /**
     * Create a channel with the specified tag, name, description and image file
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @param description The description for this channel
     * @param imageFile   The image file for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel createChannel(String tag, String name, String description, UploadFile imageFile) {
        return createChannel(tag, name, description, imageFile.getFileUrl());
    }

    /**
     * Create a channel with the specified tag, name, description and image url
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @param description The description for this channel
     * @param imageUrl    The image url for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel createChannel(String tag, String name, String description, String imageUrl) {
        return executeRequest(new CreateChannelRequest(tag, name, description, imageUrl));
    }

    /**
     * Get or create a channel with the specified tag
     * @param tag         The tag for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel getOrCreateChannel(String nameOrTagOrIdentity, String tag) {
        return getOrCreateChannel(nameOrTagOrIdentity, tag, null);
    }

    /**
     * Get or create a channel with the specified tag and name
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel getOrCreateChannel(String nameOrTagOrIdentity, String tag, String name) {
        return getOrCreateChannel(nameOrTagOrIdentity, tag, name, null);
    }

    /**
     * Get or create a channel with the specified tag, name and description
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @param description The description for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel getOrCreateChannel(String nameOrTagOrIdentity, String tag, String name, String description) {
        return getOrCreateChannel(nameOrTagOrIdentity, tag, name, description, (String) null);
    }

    /**
     * Get or create a channel with the specified tag, name, description and image file
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @param description The description for this channel
     * @param imageFile   The image file for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel getOrCreateChannel(String nameOrTagOrIdentity, String tag, String name, String description, UploadFile imageFile) {
        return getOrCreateChannel(nameOrTagOrIdentity, tag, name, description, imageFile.getFileUrl());
    }

    /**
     * Get or create a channel with the specified tag, name, description and image url
     * @param tag         The tag for this channel
     * @param name        The name for this channel
     * @param description The description for this channel
     * @param imageUrl    The image url for this channel
     * @return A new {@link OwnChannel} object
     */
    public OwnChannel getOrCreateChannel(String nameOrTagOrIdentity, String tag, String name, String description, String imageUrl) {
        OwnChannel channel = getOwnChannel(nameOrTagOrIdentity);
        if(channel == null) {
            channel = createChannel(tag, name, description, imageUrl);
        }
        return channel;
    }


    /**
     * Create a new chat with the specified email
     * @param email The email of the user you want to chat with
     * @return A new {@link Chat} object
     */
    public Chat createChat(String email) {
        return executeRequest(new CreateChatRequest(email));
    }

    /**
     * Create a new device
     *
     * @param name       The name of the device
     * @param deviceType The type of the device, e.g. "android", "ios", or "stream", but only "stream" will be pushable to
     * @return The newly created device
     */
    public Device createDevice(String name, String deviceType) {
        return executeRequest(new CreateDeviceRequest(name, deviceType));
    }

    /**
     * Delete all pushes, use with caution.
     */
    public void deleteAllPushes() {
        executeRequest(new DeleteAllPushesRequest());
    }

    /**
     * Returns all file pushes
     *
     * @return A list of pushes that included a file
     */
    public List<SentFilePush> getAllFilePushes() {
        return getAllPushes(SentFilePush.class);
    }

    /**
     * Returns all note pushes
     *
     * @return A list of pushes that included a note
     */
    public List<SentNotePush> getAllNotePushes() {
        return getAllPushes(SentNotePush.class);
    }

    /**
     * Returns all link pushes
     *
     * @return A list of pushes that included a link
     */
    public List<SentLinkPush> getAllLinkPushes() {
        return getAllPushes(SentLinkPush.class);
    }

    /**
     * Returns all address pushes
     *
     * @return A list of pushes that included an address
     */
    @Deprecated
    public List<SentAddressPush> getAllAddressPushes() {
        return getAllPushes(SentAddressPush.class);
    }

    /**
     * Returns all list pushes
     *
     * @return A list of pushes that included a list
     */
    @Deprecated
    public List<SentListPush> getAllListPushes() {
        return getAllPushes(SentListPush.class);
    }

    /**
     * @return The list of incoming or outgoing pushes
     */
    public List<SentPush> getAllPushes() {
        return ListUtil.fullList(this, new ListPushesRequest(), ListResponse::getPushes);
    }

    /**
     * Get the push with the specified identity
     * @param pushIdentity The identity of the push
     * @return The push with this identity
     */
    public SentPush getPush(String pushIdentity) {
        return FunctionUtil.getFirstWithCondition(getAllPushes(), pushIdentity, SentPush::getIdentity);
    }

    /**
     * Get pushes by a subclass of {@link SentPush}
     * @param clazz The subclass of {@link SentPush}
     * @param <T>   The type of the class
     * @return A list of pushes
     */
    @SuppressWarnings("unchecked")
    public <T extends SentPush> List<T> getAllPushes(Class<T> clazz) {
        return getAllPushes().stream()
                .filter(push -> clazz.isAssignableFrom(push.getClass()))
                .map(push -> (T) push)
                .collect(Collectors.toList());
    }

    /**
     * Used to get newer pushes since the last call of or {@link #getNewPushes()}
     * @return A list of pushes
     */
    public List<SentPush> getNewPushes() {
        List<SentPush> pushes = ListUtil.fullList(this, new ListPushesRequest().setModifiedAfter(newestModifiedAfter), ListResponse::getPushes);
        handleNewest(pushes);
        return pushes;
    }

    private void handleNewest(List<SentPush> pushes) {
        if(pushes.size() > 0) {
            SentPush push = pushes.get(0);
            double modified = push.getModified().doubleValue();
            if(modified > newestModifiedAfter.doubleValue()) {
                newestModifiedAfter = modified;
            }
        }
    }

    /**
     * Get a registered oauth client with the specified name or identity
     * @param nameOrIdentity The name or identity
     * @return The oauth client with this name or identity
     */
    public OAuthClient getOAuthClient(String nameOrIdentity) {
        return FunctionUtil.getFirstWithCondition(getOAuthClients(), nameOrIdentity, OAuthClient::getName, OAuthClient::getIdentity);
    }

    /**
     * Get all of your registered oauth clients
     *
     * @return A list of oauth clients
     */
    public List<OAuthClient> getOAuthClients() {
        return ListUtil.fullList(this, new ListClientsRequest(), ListResponse::getClients);
    }

    /**
     * Get one of your own channels with the specified name or identity
     * @param nameOrTagOrIdentity The name or identity of the channel
     * @return The channel with this name or identity
     */
    public OwnChannel getOwnChannel(String nameOrTagOrIdentity) {
        return FunctionUtil.getFirstWithCondition(getOwnChannels(), nameOrTagOrIdentity, OwnChannel::getName, OwnChannel::getTag, OwnChannel::getIdentity);
    }

    /**
     * Get all of your own channels
     *
     * @return A list of your own channels
     */
    public List<OwnChannel> getOwnChannels() {
        return ListUtil.fullList(this, new ListChannelsRequest(), ListResponse::getChannels);
    }

    /**
     * Get a channel with the specified chat identity or username / email / identity
     *
     * @param chatIdentityUserNameEmailOrIdentity The chat identity or username / email / identity
     * @return The corresponding chat
     */
    public Chat getChat(String chatIdentityUserNameEmailOrIdentity) {
        List<Chat> chats = getChats();
        Chat chat = FunctionUtil.getFirstWithCondition(chats, chatIdentityUserNameEmailOrIdentity, Chat::getIdentity);
        if(chat == null) {
            return FunctionUtil.getFirstWithFunctionWithCondition(chats, Chat::getWith, chatIdentityUserNameEmailOrIdentity, ChatUser::getName, ChatUser::getIdentity, ChatUser::getEmail);
        }
        return chat;
    }

    /**
     * Get or create a chat with the specified user
     *
     * @param userEmail The email of the user
     * @return A new {@link Chat} object
     */
    public Chat getOrCreateChat(String userEmail) {
        Chat chat = getChat(userEmail);
        if(chat == null) {
            chat = createChat(userEmail);
        }
        return chat;
    }

    /**
     * Returns all chats that are currently held by the user
     *
     * @return A list of chats held by the user
     */
    public List<Chat> getChats() {
        return ListUtil.fullList(this, new ListChatsRequest(), ListResponse::getChats);
    }

    /**
     * Get a contact with the specified name, identity or email
     *
     * @param nameIdentityOrEmail Name, identity or email of the contact
     * @return The contact with this name, identity or email
     */
    @Deprecated
    public Contact getContact(String nameIdentityOrEmail) {
        return FunctionUtil.getFirstWithCondition(getContacts(), nameIdentityOrEmail, Contact::getName, Contact::getEmail, Contact::getIdentity);
    }

    /**
     * Returns all contacts
     *
     * @return A list of contacts
     */
    @Deprecated
    public List<Contact> getContacts() {
        return ListUtil.fullList(this, new ListContactsRequest(), ListResponse::getContacts);
    }

    /**
     * Returns all devices that are registered for this user
     *
     * @return The list of registered devices
     */
    public List<Device> getDevices() {
        return ListUtil.fullList(this, new ListDevicesRequest(), ListResponse::getDevices);
    }

    /**
     * Get a device with the specified name or identity
     * @param nameOrIdentity Name or identity of the device
     * @return The device with this name or identity
     */
    public Device getDevice(String nameOrIdentity) {
        return FunctionUtil.getFirstWithCondition(getDevices(), nameOrIdentity, Device::getNickname, Device::getIdentity);
    }

    /**
     * @return All list objects
     */
    public ListResponse getCompleteListResponse() {
        return ListUtil.completeListResponse(this);
    }

    public Grant getGrant(String grantIdentityUserIdentityOrName) {
        List<Grant> grants = getGrants();
        Grant grant = FunctionUtil.getFirstWithCondition(grants, grantIdentityUserIdentityOrName, Grant::getIdentity);
        if(grant == null) {
            return FunctionUtil.getFirstWithFunctionWithCondition(grants, Grant::getClient, grantIdentityUserIdentityOrName, GrantClient::getName, GrantClient::getIdentity);
        }
        return grant;
    }

    /**
     * Returns all granted clients
     * @return A list of granted clients
     */
    public List<Grant> getGrants() {
        return ListUtil.fullList(this, new ListGrantsRequest(), ListResponse::getGrants);
    }

    /**
     * Get all subscriptions
     * @return A list of subscriptions
     */
    public List<Subscription> getSubscriptions() {
        return ListUtil.fullList(this, new ListSubscriptionsRequest(), ListResponse::getSubscriptions);
    }

    /**
     * Push something to all devices that are registered on this for this user
     * @param push The push to send
     * @return A push object that represents the sent push
     */
    public SentPush pushToAllDevices(SendablePush push) {
        push = push.clone();
        push.clearReceivers();
        return executeRequest(new SendPushRequest(push));
    }

    /**
     * Method needed for {@link Pushable} interface, wraps {@link #pushToAllDevices(SendablePush)}
     * @param push The push to send to all devices
     * @return A {@link SentPush} object
     */
    @Override
    public SentPush push(SendablePush push) {
        return pushToAllDevices(push);
    }

    /**
     * Broadcast an ephemeral
     * @param ephemeral The ephemeral to push
     */
    public void pushEphemeral(Ephemeral ephemeral) {
        pushEphemeral(ephemeral, new String[0]);
    }

    /**
     * Broadcast an ephemeral
     * @param ephemeral The ephemeral to push
     * @param targets   The target devices to push to, e.g. "android", "ios" or "stream"
     */
    public void pushEphemeral(Ephemeral ephemeral, String... targets) {
        executeRequest(new SendEphemeralRequest(ephemeral, targets));
    }

    /**
     * Upload a file to the pushbullet server
     * @param file The file to upload
     * @return An {@link UploadFile} object containing the file url
     */
    public UploadFile uploadFile(File file) {
        if(!file.exists()) throw new IllegalArgumentException("File not found.");
        RequestFileUploadRequest fileUploadRequest = new RequestFileUploadRequest(file);
        UploadFile result = executeRequest(fileUploadRequest);
        AwsAuthData data = result.getData();
        HttpPost post = new HttpPost(result.getUploadUrl());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .addTextBody("awsaccesskeyid", data.getAwsAccessKeyId())
                .addTextBody("acl", data.getAccessControlList())
                .addTextBody("key", data.getKey())
                .addTextBody("signature", data.getSignature())
                .addTextBody("policy", data.getPolicy())
                .addTextBody("content-type", result.getFileType())
                .addBinaryBody("file", file);
        post.setEntity(builder.build());
        try (CloseableHttpClient client = HttpClients.createDefault(); CloseableHttpResponse response = client.execute(post)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                return result;
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Executes an api request an returns the result
     * @param apiRequest The request to execute
     * @param <TResult>  The result type
     * @param <TMessage> The message type of the http request
     * @return A new object with the type of {@link TResult}
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <TResult, TMessage extends HttpUriRequest> TResult executeRequest(Request<TResult, TMessage> apiRequest) {
        Type genericSuperclass = apiRequest.getClass().getGenericSuperclass();
        if(!(genericSuperclass instanceof ParameterizedType)) {
            genericSuperclass = ClassUtil.searchForSuperclassWithResponseType(apiRequest.getClass());
            if(genericSuperclass == null) return null;
        }
        ParameterizedType superType = (ParameterizedType) genericSuperclass;
        ParameterizedType parameterizedType = ClassUtil.searchForSuperclassWithHttpUriRequestType(apiRequest.getClass());
        Type responseType = superType.getActualTypeArguments()[0];
        Type messageType = parameterizedType.getActualTypeArguments()[1];
        Class<? extends HttpUriRequest> httpUriRequestClass = (Class<? extends HttpUriRequest>) messageType;
        URIBuilder builder = new URIBuilder(API_BASE_URL + apiRequest.getRelativePath());
        apiRequest.applyParameters(builder);
        Constructor<? extends HttpUriRequest> httpMessageConstructor = httpUriRequestClass.getConstructor(URI.class);
        URI uri = builder.build();
        HttpUriRequest httpMessage = httpMessageConstructor.newInstance(uri);
        httpMessage.addHeader("Access-Token", accessToken);
        //Add body
        if(apiRequest instanceof EntityEnclosingRequest) {
            httpMessage.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            HttpEntityEnclosingRequestBase enclosingRequest = ((HttpEntityEnclosingRequestBase) httpMessage);
            EntityEnclosingRequest ownRequest = ((EntityEnclosingRequest) apiRequest);
            ownRequest.applyBody(gson, enclosingRequest);
        }
        //Execute request
        try(CloseableHttpClient client = HttpClients.createDefault(); CloseableHttpResponse response = client.execute(httpMessage)) {
            String responseString = EntityUtils.toString(response.getEntity());
            this.rateLimit = Integer.valueOf(HttpUtil.getHeaderValue(response, "X-Ratelimit-Limit", "0"));
            this.rateRemaining = Integer.valueOf(HttpUtil.getHeaderValue(response, "X-Ratelimit-Remaining", "0"));
            this.resetTimestamp = Long.valueOf(HttpUtil.getHeaderValue(response, "X-Ratelimit-Reset", "0"));
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new PushbulletApiException(gson.fromJson(extractError(responseString), PushbulletApiError.class));
            }
            return gson.fromJson(responseString, responseType);
        }
    }

    /**
     * Executes a function asynchronously and returns a future with the response
     * @param function The function to execute
     * @param <T>      The response type
     * @return A future with the response type
     */
    public <T> Future<T> executeAsyncWithFuture(Function<Pushbullet, T> function) {
        return executorService.submit(() -> function.apply(this));
    }

    /**
     * Executes a function asynchronously and triggers the callback on success
     * @param function The function to execute
     * @param callback The callback to trigger
     * @param <T>      The response type
     */
    public <T> void executeAsync(Function<Pushbullet, T> function, Consumer<T> callback) {
        executorService.execute(() -> callback.accept(function.apply(this)));
    }

    /**
     * Create a new websocket client that listens to the websocket stream
     * @return The newly created {@link PushbulletWebsocketClient client}
     */
    public PushbulletWebsocketClient createWebsocketClient() {
        return new PushbulletWebsocketClient(this);
    }

    @SneakyThrows
    private static JsonObject extractError(String jsonString) {
        try (JsonReader reader = new JsonReader(new StringReader(jsonString))) {
            reader.beginObject();
            reader.nextName();
            reader.beginObject();
            JsonObject error = new JsonObject();
            while (reader.hasNext()) {
                error.addProperty(reader.nextName(), reader.nextString());
            }
            return error;
        }
    }
}

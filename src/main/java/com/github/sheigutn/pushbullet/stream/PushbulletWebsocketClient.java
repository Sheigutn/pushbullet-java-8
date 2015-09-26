/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.github.sheigutn.pushbullet.stream;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.stream.message.StreamMessage;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@Getter
public final class PushbulletWebsocketClient {

    private final static String BASE_URL = "wss://stream.pushbullet.com:443/websocket/";

    private final Pushbullet pushbullet;

    protected final Collection<PushbulletWebsocketListener> websocketListeners = new ArrayList<>();

    private ClientEndpointConfig config = ClientEndpointConfig.Builder.create().build();

    private WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();

    private Session websocketSession;

    public PushbulletWebsocketClient(Pushbullet pushbullet) {
        this.pushbullet = pushbullet;
    }

    /**
     * Unregister a listener that listens to this websocket stream
     * @param listener The listener to unregister
     */
    public void registerListener(PushbulletWebsocketListener listener) {
        if(!websocketListeners.contains(listener)) {
            websocketListeners.add(listener);
        }
    }

    /**
     * Register a new listener that listens to this websocket stream
     * @param listener The listener to register
     */
    public void unregisterListener(PushbulletWebsocketListener listener) {
        if(websocketListeners.contains(listener)) {
            websocketListeners.remove(listener);
        }
    }

    /**
     * @return Whether the client is connected to the server
     */
    public synchronized boolean isConnected() {
        return websocketSession != null && websocketSession.isOpen();
    }

    /**
     * Connects the client to the server
     */
    @SneakyThrows
    public synchronized void connect() {
        if(websocketSession != null) return;
        websocketSession = webSocketContainer.connectToServer(new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig config) {
                //noinspection Convert2Lambda
                session.addMessageHandler(new MessageHandler.Whole<String>() {
                    @Override
                    public void onMessage(String message) {
                        websocketListeners.forEach(websocketListener -> websocketListener.handle(pushbullet, pushbullet.getGson().fromJson(message, StreamMessage.class)));
                    }
                });
            }
        }, config, new URI(BASE_URL + pushbullet.getAccessToken()));
    }

    /**
     * Disconnects the client from the server
     */
    public synchronized void disconnect() {
        if(websocketSession == null) return;
        try {
            websocketSession.close();
        } catch (IOException e) {

        }
        finally {
            websocketSession = null;
        }
    }
}
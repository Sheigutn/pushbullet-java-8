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
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@Getter
public final class PushbulletWebsocketClient {

    private final static String BASE_URL = "wss://stream.pushbullet.com:443/websocket/";

    private final Pushbullet pushbullet;

    protected final Collection<PushbulletWebsocketListener> websocketListeners = new ArrayList<>();

    private Channel channel;

    private EventLoopGroup group;

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
    public boolean isConnected() {
        return channel != null && channel.isOpen() && channel.isActive();
    }

    /**
     * Connects the client to the server
     */
    @SneakyThrows
    public void connect() {
        URI uri = new URI(BASE_URL + pushbullet.getAccessToken());
        String host = uri.getHost();
        int port = uri.getPort();
        final SslContext sslCtx = SslContext.newClientContext();
        group = new NioEventLoopGroup();
        final PushbulletWebsocketClientHandler handler =
                new PushbulletWebsocketClientHandler(this,
                        WebSocketClientHandshakerFactory.newHandshaker(
                                uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(sslCtx.newHandler(ch.alloc(), host, port))
                                .addLast(
                                        new HttpClientCodec(),
                                        new HttpObjectAggregator(8192),
                                        new WebSocketClientCompressionHandler(),
                                        handler);
                    }
                });
        channel = b.connect(uri.getHost(), port).sync().channel();
        handler.handshakeFuture().sync();
    }

    /**
     * Disconnects the client from the server
     */
    public void disconnect() {
        channel.writeAndFlush(new CloseWebSocketFrame());
        channel.close().syncUninterruptibly();
        channel = null;
        group.shutdownGracefully();
    }
}
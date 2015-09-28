# Pushbullet library for Java 8

This is an inofficial pushbullet library for Java 8.

# Javadoc

Javadoc is located [here](http://sheigutn.github.io/pushbullet-java-8).

# Maven

This library can be used with Maven, it's located in the central repository:

```xml
<dependency>
    <groupId>com.github.sheigutn</groupId>
    <artifactId>pushbullet-java-8</artifactId>
    <version>1.0</version>
</dependency>
```

To use SNAPSHOT versions, add this to your pom.xml:

```xml
<repository>
    <id>sonatype-nexus-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
```

# Usage (WIP!)

```java
String apiToken = ...;
Pushbullet pushbullet = new Pushbullet(apiToken);
```

If you need end-to-end encryption, apply the password as the second parameter:

```java
String apiToken = ...;
String password = ...;
Pushbullet pushbullet = new Pushbullet(apiToken, password);
```

## /v2/devices

To get all devices, use:

```java
List<Device> devices = pushbullet.getDevices();
```

To get a specific device, you can use one of these properties:
- The name of the device
- The identity of the device

```java
String property = ...;
Device device = pushbullet.getDevice(property);
```

## /v2/pushes

To get all pushes, use:

```java
List<SentPush> pushes = pushbullet.getAllPushes();
```

There are also variants of this method allowing to get pushes by push type:

```java
List<SentFilePush> filePushes = pushbullet.getAllFilePushes();
List<SentLinkPush> linkPushes = pushbullet.getAllLinkPushes();
List<SentNotePush> notePushes = pushbullet.getAllNotePushes();
List<SentAddressPush> addressPushes = pushbullet.getAllAddressPushes();
List<SentListPush> listPushes = pushbullet.getAllListPushes();
```

It's also possible to use a subclass of SentPush as a parameter to get all pushes of that type, e.g.:

```java
List<SentFilePush> filePushes = pushbullet.getAllPushes(SentFilePush.class);
```

## /v2/chats

To get all currently held chats, use:

```java
List<Chat> chats = pushbullet.getChats();
```

To get a specific chat, you can use one of these properties:
- The identity of the chat
- The name of the user this chat is held with
- The email of the user
- The identity of the user

```java
String property = ...;
Chat chat = pushbullet.getChat(property);
```
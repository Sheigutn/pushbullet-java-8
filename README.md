# Pushbullet library for Java 8

This is an inofficial pushbullet library for Java 8.

# Javadoc

Javadoc is located [here](http://sheigutn.github.io/pushbullet-java-8).

# Maven

[![Dependency Status](https://www.versioneye.com/user/projects/560c145b5a262f001e00081d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/560c145b5a262f001e00081d) [![Build Status](https://travis-ci.org/Sheigutn/pushbullet-java-8.svg?branch=master)](https://travis-ci.org/Sheigutn/pushbullet-java-8)

This library can be used with Maven, it's located in the central repository:

```xml
<dependency>
    <groupId>com.github.sheigutn</groupId>
    <artifactId>pushbullet-java-8</artifactId>
    <version>1.3.4</version>
</dependency>
```

To use SNAPSHOT versions, add this to your pom.xml:

```xml
<repository>
    <id>sonatype-nexus-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
```

# Usage

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

# Donate

If you want to donate to me, just use my bitcoin address: 1K3yoaHnbrYSNwK1d8suDxcEgwQD4Y9Fso
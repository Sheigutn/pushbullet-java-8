package com.github.sheigutn.pushbullet.cryptography;

import com.github.sheigutn.pushbullet.Pushbullet;
import lombok.NonNull;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

/**
 * @author Flo
 */
public class Encryption {

    private final static int ITERATIONS = 30000;

    private final static int DERIVED_KEY_LENGTH_BITS = 256;

    private final static int GCM_INIT_VECTOR_LENGTH_BITS = 96;

    private final static int GCM_TAG_LENGTH_BITS = 128;

    private final static String CHARSET = "UTF-8";

    private Pushbullet pushbullet;

    private SecretKey secretKey;

    private SecureRandom secureRandom;

    @SneakyThrows
    public Encryption(@NonNull Pushbullet pushbullet, @NonNull String password) {
        this.pushbullet = pushbullet;
        this.secretKey = generateKey(password);
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        this.secureRandom = SecureRandom.getInstanceStrong();
        System.out.println(decrypt("Mf+MkpkbhAO4e3heYnx3B/ZOOHTSUHsyl5KfGgfuX6F4"));
    }

    @SneakyThrows
    private SecretKey generateKey(String password) {
        String salt = pushbullet.getCurrentUser().getIdentity();
        SecretKeyFactory pbkdf2WithHmacSHA256 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(pbkdf2WithHmacSHA256.generateSecret(new PBEKeySpec(password.toCharArray(), salt.getBytes(CHARSET), ITERATIONS, DERIVED_KEY_LENGTH_BITS)).getEncoded(), "AES");
    }

    @SneakyThrows
    public String encrypt(String message) {
        byte[] initVector = new byte[GCM_INIT_VECTOR_LENGTH_BITS / 8];
        secureRandom.nextBytes(initVector);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, initVector);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        byte[] encrypted = cipher.doFinal(message.getBytes(CHARSET));
        byte[] tag = Arrays.copyOfRange(encrypted, encrypted.length - (GCM_TAG_LENGTH_BITS / Byte.SIZE), encrypted.length);
        encrypted = Arrays.copyOfRange(encrypted, 0, encrypted.length - (GCM_TAG_LENGTH_BITS / Byte.SIZE));
        return Base64.getEncoder().encodeToString(concatByteArrays("1".getBytes(CHARSET), tag, initVector, encrypted));
    }

    @SneakyThrows
    public String decrypt(String encodedMessage) {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        String decoded = new String(Base64.getDecoder().decode(encodedMessage.getBytes(CHARSET)), CHARSET);
        byte[] messageBytes = decoded.getBytes(CHARSET);
        System.out.println(new String(messageBytes, CHARSET));
        byte[] version = Arrays.copyOfRange(messageBytes, 0, 1);
        System.out.println(new String(version, CHARSET));
        int tagLastIndex = 1 + GCM_TAG_LENGTH_BITS / 8;
        System.out.println(tagLastIndex);
        byte[] tag = Arrays.copyOfRange(messageBytes, 1, tagLastIndex);
        int initVectorLastIndex = tagLastIndex + GCM_INIT_VECTOR_LENGTH_BITS / 8;
        byte[] initVector = Arrays.copyOfRange(messageBytes, tagLastIndex, initVectorLastIndex);
        System.out.println(initVectorLastIndex);
        byte[] realMessage = Arrays.copyOfRange(messageBytes, initVectorLastIndex, messageBytes.length);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, initVector));
        cipher.updateAAD(tag);
        byte[] decrypted = cipher.doFinal(realMessage);
        return new String(decrypted, CHARSET);
    }

    private byte[] concatByteArrays(byte[]... arrays) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Stream.of(arrays).forEach(array -> {
            try {
                byteArrayOutputStream.write(array);
            }
            catch (IOException ex) {

            }
        });
        return byteArrayOutputStream.toByteArray();
    }
}

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

public class Encryption {

    /**
     * Iterations
     */
    private final static int ITERATIONS = 30000;

    /**
     * Derived key length
     */
    private final static int DERIVED_KEY_LENGTH_BITS = 256;

    /**
     * GCM init vector length in bits
     */
    private final static int GCM_INIT_VECTOR_LENGTH_BITS = 96;

    /**
     * GCM tag length in bits
     */
    private final static int GCM_TAG_LENGTH_BITS = 128;

    /**
     * Current version of the encryption
     */
    private final static String VERSION = "1";

    /**
     * Charset of the messages
     */
    private final static String CHARSET = "UTF-8";

    /**
     * The {@link Pushbullet} instance
     */
    private Pushbullet pushbullet;

    /**
     * The {@link SecretKey} for the algorithm
     */
    private SecretKey secretKey;

    /**
     * The {@link SecureRandom} generating the bytes for the init vector
     */
    private SecureRandom secureRandom;

    /**
     * Creates a new {@link Encryption} instance
     * @param pushbullet The {@link Pushbullet} instance
     * @param password   The password to use for the encryption
     */
    @SneakyThrows
    public Encryption(@NonNull Pushbullet pushbullet, @NonNull String password) {
        this.pushbullet = pushbullet;
        this.secretKey = generateKey(password);
        this.secureRandom = SecureRandom.getInstanceStrong();
    }

    /**
     * Generates the key from the password, algorithm: PBKDF2 with Hmac SHA256
     * @param password The password given for the key
     * @return The {@link SecretKey} made of the password
     */
    @SneakyThrows
    private SecretKey generateKey(String password) {
        String salt = pushbullet.getCurrentUser().getIdentity();
        SecretKeyFactory pbkdf2WithHmacSHA256 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(pbkdf2WithHmacSHA256.generateSecret(new PBEKeySpec(password.toCharArray(), salt.getBytes(CHARSET), ITERATIONS, DERIVED_KEY_LENGTH_BITS)).getEncoded(), "AES");
    }

    /**
     * Encrypts a message
     * @param message The message to encrypt
     * @return The encrypted message in the format shown on the pushbullet api docs
     */
    @SneakyThrows
    public String encrypt(String message) {
        byte[] initVector = new byte[GCM_INIT_VECTOR_LENGTH_BITS / Byte.SIZE];
        secureRandom.nextBytes(initVector);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, initVector);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        byte[] encrypted = cipher.doFinal(message.getBytes(CHARSET));
        byte[] tag = Arrays.copyOfRange(encrypted, encrypted.length - (GCM_TAG_LENGTH_BITS / Byte.SIZE), encrypted.length);
        encrypted = Arrays.copyOfRange(encrypted, 0, encrypted.length - (GCM_TAG_LENGTH_BITS / Byte.SIZE));
        return Base64.getEncoder().encodeToString(concatByteArrays(VERSION.getBytes(CHARSET), tag, initVector, encrypted));
    }

    /**
     * Decrypts an encoded encrypted message
     * @param encodedMessage The base64 encoded, encrypted message
     * @return The plaintext of the message
     */
    @SneakyThrows
    public String decrypt(String encodedMessage) {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] messageBytes = Base64.getDecoder().decode(encodedMessage.getBytes(CHARSET));
        byte[] version = Arrays.copyOfRange(messageBytes, 0, 1);
        if(!new String(version, CHARSET).equals(VERSION)) {
            throw new IllegalArgumentException("Version is wrong!");
        }
        int tagLastIndex = 1 + GCM_TAG_LENGTH_BITS / Byte.SIZE;
        byte[] tag = Arrays.copyOfRange(messageBytes, 1, tagLastIndex);
        int initVectorLastIndex = tagLastIndex + GCM_INIT_VECTOR_LENGTH_BITS / Byte.SIZE;
        byte[] initVector = Arrays.copyOfRange(messageBytes, tagLastIndex, initVectorLastIndex);
        byte[] realMessage = concatByteArrays(Arrays.copyOfRange(messageBytes, initVectorLastIndex, messageBytes.length), tag);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, initVector));
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

package com.github.sheigutn.pushbullet.cryptography;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.ephemeral.Ephemeral;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * @author Flo
 */
@RequiredArgsConstructor
public class EphemeralEncryptionHandler implements TypeAdapterFactory {

    private final Pushbullet pushbullet;

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if(Ephemeral.class.equals(type.getRawType())) {
            TypeAdapter<T> originalTypeAdapter = gson.getDelegateAdapter(this, type);
            return new EncryptionEphemeralTypeAdapter<>((Class<T>) type.getRawType(), originalTypeAdapter, gson);
        }
        return null;
    }

    @RequiredArgsConstructor
    private class EncryptionEphemeralTypeAdapter<T> extends TypeAdapter<T> {

        private final Class<T> rawType;

        private final TypeAdapter<T> originalAdapter;

        private final Gson gson;

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            Encryption encryption = EphemeralEncryptionHandler.this.pushbullet.getEncryption();
            JsonElement tree = originalAdapter.toJsonTree(value);
            if (encryption != null) {
                String encrypted = encryption.encrypt(tree.toString());
                JsonObject object = new JsonObject();
                object.addProperty("ciphertext", encrypted);
                object.addProperty("encrypted", true);
                tree = object;
            }
            gson.toJson(tree, out);
        }

        @Override
        public T read(JsonReader in) throws IOException {
            Encryption encryption = EphemeralEncryptionHandler.this.pushbullet.getEncryption();
            if(encryption != null) {
                in.beginObject();
                in.nextName();
                boolean encrypted = in.nextBoolean();
                in.nextName();
                String cipherText = in.nextString();
                cipherText = cipherText.replaceAll("\\n", "");
                in.endObject();
                if(encrypted) {
                    String decrypted = encryption.decrypt(cipherText);
                    return originalAdapter.fromJson(decrypted);
                }
            }
            return originalAdapter.read(in);
        }
    }
}

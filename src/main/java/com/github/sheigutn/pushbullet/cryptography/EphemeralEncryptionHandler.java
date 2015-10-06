package com.github.sheigutn.pushbullet.cryptography;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.ephemeral.Ephemeral;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

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
                String cipherText = null;
                boolean encrypted = false;
                while(in.hasNext()) {
                    switch (in.nextName()) {
                        case "ciphertext":
                            cipherText = in.nextString().replaceAll("\\n", "");
                            break;
                        case "encrypted":
                            encrypted = in.nextBoolean();
                            break;
                        default:
                            in.skipValue();
                    }
                }
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

package com.a525team.weekbrowser.navi.core;

import com.a525team.weekbrowser.navi.model.NaviConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** Resolves the encrypted nPoint wrapper used by the iOS NAVI panel. */
public final class NpointConfigLoader {
    private static final String API_ROOT = "https://api.npoint.io/";
    private static final int CONNECT_TIMEOUT_MS = 15000;
    private static final int READ_TIMEOUT_MS = 15000;
    private static final int MAX_RESPONSE_BYTES = 1024 * 1024;
    private static final int GCM_NONCE_BYTES = 12;
    private static final Pattern VALID_NPOINT_ID = Pattern.compile("[A-Za-z0-9_-]{1,128}");

    private NpointConfigLoader() {}

    public static boolean isNpointWrapper(JSONObject script) {
        return script != null && script.optBoolean("newVersion", false);
    }

    public static String resolve(JSONObject wrapper) throws IOException, JSONException {
        if (!isNpointWrapper(wrapper)) {
            throw new JSONException("O script não contém uma configuração nPoint newVersion.");
        }

        String npointId = wrapper.optString("npoint", "").trim();
        String secret = wrapper.optString("secret", "");
        if (!VALID_NPOINT_ID.matcher(npointId).matches()) {
            throw new JSONException("Identificador nPoint ausente ou inválido.");
        }
        if (secret.isEmpty()) {
            throw new JSONException("Chave de descriptografia ausente.");
        }

        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        if (key.length != 16 && key.length != 24 && key.length != 32) {
            throw new JSONException("A chave AES deve ter 16, 24 ou 32 bytes UTF-8.");
        }

        byte[] encrypted = fetchEncryptedValue(npointId);
        return decrypt(encrypted, key);
    }

    private static byte[] fetchEncryptedValue(String npointId) throws IOException, JSONException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(API_ROOT + npointId + "/hash");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(READ_TIMEOUT_MS);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(false);

            int status = connection.getResponseCode();
            if (status < 200 || status >= 300) {
                throw new IOException("nPoint respondeu HTTP " + status + ".");
            }

            byte[] body;
            try (InputStream input = connection.getInputStream();
                 ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int total = 0;
                int count;
                while ((count = input.read(buffer)) != -1) {
                    total += count;
                    if (total > MAX_RESPONSE_BYTES) {
                        throw new IOException("Resposta nPoint excede o limite permitido.");
                    }
                    output.write(buffer, 0, count);
                }
                body = output.toByteArray();
            }

            Object value = new JSONTokener(new String(body, StandardCharsets.UTF_8)).nextValue();
            if (!(value instanceof String)) {
                throw new JSONException("Resposta nPoint não contém uma string Base64.");
            }
            byte[] encrypted = android.util.Base64.decode((String) value, android.util.Base64.DEFAULT);
            if (encrypted.length <= GCM_NONCE_BYTES + 16) {
                throw new JSONException("Dados criptografados nPoint incompletos.");
            }
            return encrypted;
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private static String decrypt(byte[] combined, byte[] key) throws JSONException {
        try {
            byte[] nonce = new byte[GCM_NONCE_BYTES];
            System.arraycopy(combined, 0, nonce, 0, nonce.length);
            int ciphertextLength = combined.length - GCM_NONCE_BYTES;
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, nonce));
            byte[] plaintext = cipher.doFinal(combined, GCM_NONCE_BYTES, ciphertextLength);
            String configJson = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(plaintext))
                    .toString();
            new JSONObject(configJson);
            return configJson;
        } catch (CharacterCodingException e) {
            throw new JSONException("A configuração descriptografada não é UTF-8 válido.");
        } catch (Exception e) {
            throw new JSONException("Não foi possível descriptografar a configuração nPoint: "
                    + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
        }
    }
}

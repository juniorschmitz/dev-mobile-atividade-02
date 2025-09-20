package com.example.servicos;

import android.content.Context;
import android.util.Log;
import java.io.*;

public class EnvHelper {
    private static final String TAG = "EnvHelper";

    public static String getValue(Context context, String key) {
        return getValueFromAsset(context, "env.properties", key); // <- novo nome
    }

    private static String getValueFromAsset(Context context, String assetName, String key) {
        try (InputStream is = context.getAssets().open(assetName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int idx = line.indexOf('=');
                if (idx > 0) {
                    String k = line.substring(0, idx).trim();
                    String v = line.substring(idx + 1).trim();
                    if (k.equals(key)) return v;
                }
            }
            Log.w(TAG, "Chave não encontrada em " + assetName + ": " + key);
        } catch (IOException e) {
            try {
                String[] list = context.getAssets().list("");
                Log.e(TAG, "Não foi possível abrir " + assetName + ". Assets na raiz: " + java.util.Arrays.toString(list), e);
            } catch (IOException ex) {
                Log.e(TAG, "Falha ao listar assets", ex);
            }
        }
        return null;
    }
}
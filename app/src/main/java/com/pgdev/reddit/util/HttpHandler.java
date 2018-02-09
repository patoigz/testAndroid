package com.pgdev.reddit.util;

/**
 * Created by patoi on 08-Feb-18.
 */

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public String makeServiceCall(String requestUrl) {
        String response = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (Exception ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Log.e(TAG, "Exception: " + ex.getMessage());
            }
        }
        return stringBuilder.toString();
    }
}

package com.galalem.async;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class StringDownloadTask extends AsyncTask<String, Integer, String> {

    private final StringDownloadWatcher listener;

    StringDownloadTask(StringDownloadWatcher listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    protected String doInBackground(String... urls) {

        InputStream input = null;
        try {
            input = new URL(urls[0]).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (input == null)
            return "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            int temp;
            while ((temp = reader.read()) != -1)
                builder.append((char) temp);
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null) listener.onFinish(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (listener != null) listener.onCancelled();
    }
}

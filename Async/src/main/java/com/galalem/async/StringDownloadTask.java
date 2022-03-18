package com.galalem.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class StringDownloadTask {

    private final StringDownloadWatcher listener;

    public StringDownloadTask(StringDownloadWatcher listener) {
        this.listener = listener;
    }

    public void execute(String url) {
        InputStream input = null;
        try {
            input = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (input == null)
            return;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            int temp;
            while ((temp = reader.read()) != -1)
                builder.append((char) temp);

            listener.onFinish(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

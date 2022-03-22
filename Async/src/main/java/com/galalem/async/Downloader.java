package com.galalem.async;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

@SuppressWarnings("unused")
public abstract class Downloader {

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void downloadJSON(String from, JSONDownloadWatcher listener) {
        downloadString(from, new StringDownloadWatcher() {
            @Override
            public void onFinish(String result) {
                JSONObject object = null;
                JSONArray array = null;
                if (listener != null) listener.onLoaded(result);
                try {
                    object = new JSONObject(result);
                } catch (JSONException ignored) {
                }
                if (listener != null) listener.onFinish(object);
                try {
                    array = new JSONArray(result);
                } catch (JSONException ignored) {
                }
                if (listener != null) listener.onFinish(array);
            }

            @Override
            public void onCancelled() {
                if (listener != null) listener.onCancelled();
            }
        });
    }

    public static void downloadString(String from, StringDownloadWatcher listener) {
        new StringDownloadTask(listener).execute(from);
    }

    public static void downloadFile(Context context, String from, String dest, FileDownloadWatcher listener) {
        if (fileExists(dest))
            listener.onFinish(true);
        else
            new FileDownloadTask(context, listener).execute(from, dest);
    }

}

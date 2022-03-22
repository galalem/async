package com.galalem.async;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JSONDownloadWatcher {

    /**
     * called when the JSON Text download is completed
     * @param json the JSON Text result
     */
    default void onLoaded(@NonNull String json) { }
    /**
     * called when the JSON download is successfully completed and the result is valid JSON object
     *
     * @param json the JSON result as an object
     */
    default void onFinish(@Nullable JSONObject json) { }

    /**
     * called when the JSON download is successfully completed and the result is valid JSON array
     *
     * @param json the JSON result as an array
     */
    default void onFinish(@Nullable JSONArray json) { }

    /**
     * called when the JSON download is cancelled or failed
     */
    default void onCancelled() { }
}

package com.galalem.async;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JSONDownloadWatcher {

    /**
     * called when the JSON Text download is completed
     * @param jsonTxt the JSON Text result
     */
    void onLoaded(@NonNull String jsonTxt);
    /**
     * called when the JSON download is successfully completed and the result is valid JSON object
     *
     * @param json the JSON result as an object
     */
    void onFinish(@Nullable JSONObject json);

    /**
     * called when the JSON download is successfully completed and the result is valid JSON array
     *
     * @param json the JSON result as an array
     */
    void onFinish(@Nullable JSONArray json);
}

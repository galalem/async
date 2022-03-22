package com.galalem.async;

public interface StringDownloadWatcher {
    void onFinish(String result);
    default void onCancelled() { }
}

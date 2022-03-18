package com.galalem.async;

public interface FileDownloadWatcher {
    default void onStart(){ }
    default void onProgress(int percent){ }
    void onFinish(boolean isResultSuccessful);
    default void onCancel(){ }
}

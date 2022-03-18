package com.galalem.async;

interface IDownload {
    default void onStart(){ }
    default void onProgress(int percent){ }
    default void onDataLoaded(byte[] bytes, int count) { }
    void onFinish(boolean result);
    default void onCancel(){ }
}

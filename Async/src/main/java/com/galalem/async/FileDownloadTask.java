package com.galalem.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

class FileDownloadTask extends AsyncTask<String, Integer, Boolean> {

    static final int SIZE_4K = 4096;
    static final int DEFAULT_TIME_OUT = 3_600_000;

    private final WeakReference<Context> context;
    private final FileDownloadWatcher listener;

    private final int dataBlockSize;
    private final int millisToTimeout;

    FileDownloadTask(Context context, FileDownloadWatcher listener) {
        this(context, listener, SIZE_4K);
    }

    FileDownloadTask(Context context, FileDownloadWatcher listener, int dataBlockSize) {
        this(context, listener, dataBlockSize, DEFAULT_TIME_OUT);
    }

    FileDownloadTask(Context context, FileDownloadWatcher listener, int dataBlockSize, int millisToTimeout) {
        super();
        this.context = new WeakReference<>(context);
        this.listener = listener;
        this.dataBlockSize = dataBlockSize;
        this.millisToTimeout = millisToTimeout;
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        PowerManager.WakeLock wakeLock = ((PowerManager) context.get().getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());

        wakeLock.acquire(millisToTimeout);

        boolean result_ok = false;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                wakeLock.release();
                return false;
            }

            result_ok = true;
            int fileSize = connection.getContentLength();
            InputStream input = connection.getInputStream();
            OutputStream output = new FileOutputStream(urls[1]);

            byte[] data = new byte[dataBlockSize];
            long size = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                if (isCancelled()){
                    wakeLock.release();
                    return false;
                }

                size += count;

                if (fileSize > 0) publishProgress((int) (size * 100 / fileSize));

                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wakeLock.release();
        }
        return result_ok;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) listener.onStart();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (listener != null) listener.onProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (listener != null) listener.onFinish(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (listener != null) listener.onCancel();
    }
}

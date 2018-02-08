package com.lzx2005.eurekamonitor.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by john on 2017/4/23.
 */

public class GetTask implements Runnable {
    private Handler handler;
    private String url;

    public GetTask(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Message msg = new Message();
        Bundle data = new Bundle();
        Request request = new Request.Builder()
                .get()
                .addHeader("Authorization","Basic ZmR0ZXN0OmZkdGVzdA==")
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("lzx", response.code()+"");
            data.putString("value", response.body().string());
            msg.setData(data);
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

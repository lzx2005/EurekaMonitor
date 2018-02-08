package com.lzx2005.eurekamonitor.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hzlizx on 2017/12/28 0028.
 */

public class GetAppsTask implements Runnable{
    private Handler handler;
    private String url;

    public GetAppsTask(String url, Handler handler) {
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
            String xml = response.body().string();
            JSONObject jsonObj = null;
            try {
                jsonObj = XML.toJSONObject(xml);
            } catch (JSONException e) {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }
            if(jsonObj!=null){
                data.putString("value",  jsonObj.toString());
            }
            msg.setData(data);
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

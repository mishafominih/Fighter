package com.example.fighter;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyRequest {

    JSONObject params;
    public MyRequest(){
        params = new JSONObject();
    }

    public void put(String key, Object value){
        try {
            params.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Call(String path, Responsble<JSONObject> resp, Failurable<IOException> fail){
        call(path, resp, fail);
    }

    public void Call(String path, Responsble<JSONObject> resp){
        call(path, resp, this::baseFail);
    }

    private void baseFail(IOException ex){
        Log.i("connect_fail", ex.getMessage());
    }

    private RequestBody build_params(){
        FormBody.Builder formbody = new FormBody.Builder();
        for (Iterator<String> it = params.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                formbody.add(key, (String) params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return formbody.build();
    }

    private void call(String path, Responsble<JSONObject> resp, Failurable<IOException> fail){
        String url = "http://10.0.2.2:5000/";

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url + path)
                .post(build_params())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                fail.callback(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    resp.callback(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}

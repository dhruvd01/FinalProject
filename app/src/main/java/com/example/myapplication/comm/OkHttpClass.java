package com.example.myapplication.comm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClass {
    Context context;

    public OkHttpClass(Context context) {
        this.context = context;
    }

    public String myOkRespose(String urls, ArrayList<String> keys, ArrayList<String> values) {
        try {
            FormBody.Builder formBody = new FormBody.Builder();
            for (int i = 0; i < keys.size(); i++) {
                formBody.add(keys.get(i), values.get(i));
            }

            urls = urls.replace(" ", "20%");

            RequestBody body = formBody.build();
            Request.Builder builder = new Request.Builder();
            builder.url(urls);
            builder.post(body);
            Request request = builder.build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                    .readTimeout(1, TimeUnit.MINUTES)     // socket timeout
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            okhttp3.Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String myokdatanew(String url, String mykey) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                    .readTimeout(1, TimeUnit.MINUTES)     // socket timeout
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            Request request = new Request.Builder()
                    .header("Authorization", mykey)
                    .url(url)
                    .build();
            okhttp3.Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return null;
            }

        } catch (Exception E) {
            return null;
        }
    }
}



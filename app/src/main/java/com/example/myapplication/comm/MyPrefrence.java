package com.example.myapplication.comm;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefrence {
    public Context context;
    SharedPreferences sharedPreferences;
    String pixelname = "pixelname";



    public MyPrefrence(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setPixelname(String value) {
        sharedPreferences.edit().putString(pixelname, value).commit();
    }

    public String getPixelname() {
        return sharedPreferences.getString(pixelname, "2477");
    }

}

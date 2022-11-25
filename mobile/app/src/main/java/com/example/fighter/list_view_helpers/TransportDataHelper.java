package com.example.fighter.list_view_helpers;

import android.content.Intent;
import android.os.Bundle;

public class TransportDataHelper {
    public Intent intent;
    public Bundle data;

    public TransportDataHelper(Intent intent){
        this.intent = intent;
        data = intent.getExtras();
    }

    public String get(String key){
        if (data != null && data.containsKey(key)) {
            return data.get(key).toString();
        }
        return null;
    }

    public void add(String key, String value){
        intent.putExtra(key, value);
    }
}

package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    String server_name = "http://l29340eb.bget.ru";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("chat", "+ MainActivity - запуск приложения");

        findViewById(R.id.button_ping_server).setOnClickListener(view -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(server_name + "/chat.php?action=delete");
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000); // ждем 10сек
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.connect();
                int res = conn.getResponseCode();
                Log.i("chat", "+ MainActivity - ответ сервера (200 = ОК): "
                        + Integer.toString(res));

            } catch (Exception e) {
                Log.i("chat",
                        "+ MainActivity - ответ сервера ОШИБКА: "
                                + e.getMessage());
            } finally {
                conn.disconnect();
            }

        });
    }
}
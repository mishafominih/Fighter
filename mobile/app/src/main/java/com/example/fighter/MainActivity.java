package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    String adress = "http://127.0.0.1:5000/time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "start", Toast.LENGTH_LONG).show();

        findViewById(R.id.button_ping_server).setOnClickListener(view -> {
            new MyRequest().Call("time", (res) -> {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, res, Toast.LENGTH_LONG).show();
                    }
                });
            }, (fail) ->{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, fail.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            });
        });
    }
}
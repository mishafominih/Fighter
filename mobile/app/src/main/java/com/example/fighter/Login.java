package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //  Словарь для получения заполненных данных
        HashMap<String, Integer> sources = new HashMap<>();
        sources.put("login", R.id.edit_text_login_email);
        sources.put("pass", R.id.edit_text_login_password);

        findViewById(R.id.text_view_login_registration).setOnClickListener(view -> {
            Intent reg = new Intent(this, Registration.class);
            startActivity(reg);
            this.finish();
        });

        findViewById(R.id.button_login).setOnClickListener(view -> {
            MyRequest request = new MyRequest();
            // Набираем данные для запроса
            for (String key: sources.keySet()) {
                EditText edit =  (EditText) findViewById(sources.get(key));
                request.put(key, edit.getText().toString());
            }
            // Сам запрос
            request.Call("login", (res) -> {
                runOnUiThread(() -> {
                    try {
                        if(res.getBoolean("result")) {
                            Intent tournaments = new Intent(this, TournametnsList.class);
                            startActivity(tournaments);
                            this.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }, (fail) ->{
                runOnUiThread(() -> Toast.makeText(Login.this, fail.getMessage(), Toast.LENGTH_LONG).show());
            });
        });
    }
}
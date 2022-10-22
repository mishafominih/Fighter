package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //  Словарь для получения заполненных данных
        HashMap<String, Integer> sources = new HashMap<>();
        sources.put("surname", R.id.edit_text_registration_surname);
        sources.put("firstname", R.id.edit_text_registration_firstname);
        sources.put("login", R.id.edit_text_registration_email);
        sources.put("pass", R.id.edit_text_registration_password);

        findViewById(R.id.text_view_registration_login).setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            this.finish();
            return;
        });

        findViewById(R.id.button_registration).setOnClickListener(view -> {
            JSONObject params = new JSONObject();
            try {
                // Набираем данные для запроса
                for (String key: sources.keySet()) {
                    EditText edit =  (EditText) findViewById(sources.get(key));
                    params.put(key, edit.getText().toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Сам запрос
            new MyRequest().Call("registration", params, (res) -> {
                runOnUiThread(() -> {
                    try {
                        Toast.makeText(Registration.this, res.getString("result"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }, (fail) ->{
                runOnUiThread(() -> Toast.makeText(Registration.this, fail.getMessage(), Toast.LENGTH_LONG).show());
            });
        });
    }
}
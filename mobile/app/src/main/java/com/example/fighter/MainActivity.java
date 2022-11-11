package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_main_login).setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });

        findViewById(R.id.image_join_to_tournament).setOnClickListener(view -> {
            MyRequest request = new MyRequest();
            EditText edit =  (EditText) findViewById(R.id.edit_text_main_code_to_join);
            request.put("key", edit.getText().toString());
            request.Call("join_to_tournament", (res) -> {
                runOnUiThread(() -> {
                    try {
                        if(res.getBoolean("result")){
                            Intent intent = new Intent(this, FightList.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }, (fail) ->{
                return;
            });
        });
    }
}
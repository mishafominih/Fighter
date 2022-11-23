package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddPlayer extends AppCompatActivity {

    protected String user_id;
    protected String tournament_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null && arguments.containsKey("user_id")){
            user_id = arguments.get("user_id").toString();
            tournament_id = arguments.get("tournament_id").toString();
        }


        findViewById(R.id.button_add_player).setOnClickListener((view) -> {
            String name = ((EditText)findViewById(R.id.name)).getText().toString();
            String surname = ((EditText)findViewById(R.id.surname)).getText().toString();
            String lastname = ((EditText)findViewById(R.id.lastname)).getText().toString();
            String link = ((EditText)findViewById(R.id.link)).getText().toString();

            JSONArray categories = new JSONArray();
            JSONObject cat = new JSONObject();
            try {
                cat.put("name", "123");
                cat.put("value", "123");
                categories.put(cat);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MyRequest request = new MyRequest();
            request.put("tournament_id", "13");
            request.put("user_id", "3");
            request.put("name", name);
            request.put("surname", surname);
            request.put("patronymic", lastname);
            request.put("categories", categories);
            request.put("link", link);
            request.put("description", "");
            request.Call("add_player", (res) -> {
                finish();
            });
        });
    }
}
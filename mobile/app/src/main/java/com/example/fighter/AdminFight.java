package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fighter.list_view_helpers.Player;
import com.example.fighter.utils.TransportDataHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminFight extends AppCompatActivity {
    String tournament_id;
    String fight_id;
    String user_id;
    Player fighter_one;
    Player fighter_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fight);

        findViewById(R.id.head_button).setOnClickListener(view -> {
            Intent start = new Intent(this, MainActivity.class);
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start);
            this.finish();
        });

        TransportDataHelper helper = new TransportDataHelper(getIntent());
        user_id = helper.get("user_id");
        fight_id = helper.get("fight_id");
        tournament_id = helper.get("tournament_id");
        String f_one = helper.get("fighter_one");
        String f_two = helper.get("fighter_two");
        try {
            fighter_one = new Player(new JSONObject(f_one));
            fighter_two = new Player(new JSONObject(f_two));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] arr = new String[3];
        arr[0] = "";
        arr[1] = fighter_one.GetFIO();
        arr[2] = fighter_two.GetFIO();
        Spinner spinner = findViewById(R.id.spinner_fighters);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);
        spinner.setAdapter(adapter);

        findViewById(R.id.button_save).setOnClickListener((v) -> {
            String fio = spinner.getSelectedItem().toString();
            if(!fio.equals("")){
                MyRequest request = new MyRequest();
                request.put("user_id", user_id);
                request.put("tournament_id", tournament_id);
                request.put("fight_id", fight_id);
                String winner_id;
                if(fighter_one.GetFIO().equals(fio))
                    winner_id = fighter_one.Id;
                else
                    winner_id = fighter_two.Id;
                request.put("winner_id", winner_id);
                request.Call("/set_winner", (res) -> {
                    runOnUiThread(() -> {
                        try {
                            if(res.getBoolean("result")) {
                                this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }, (fail) ->{
                    runOnUiThread(() -> Toast.makeText(AdminFight.this, fail.getMessage(), Toast.LENGTH_LONG).show());
                });
            }
        });
    }
}
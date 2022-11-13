package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FightList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_list);

        MyRequest request = new MyRequest();
        // Набираем данные для запроса
        request.put("key", "1234");
        request.CallArray("tournament_list", (res) -> {
            runOnUiThread(() -> {
                ArrayList<Fight> fights = new ArrayList<Fight>();
                // fights.add(new Fight("Время/место", "Участники", "Счет"));
                for(int i = 0; i < res.length(); i++){
                    try {
                        JSONObject json = res.getJSONObject(i);
                        fights.add(new Fight(
                            json.getString("time"),
                            json.getString("place"),
                            json.getString("fighter_one"),
                            json.getString("fighter_two"),
                            json.getString("score")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView fights_list = findViewById(R.id.list_view_tournament_list);
                FightAdapter adapter = new FightAdapter(this, R.layout.fight_list_item, fights);
                fights_list.setAdapter(adapter);
            });
        }, (fail) ->{
            runOnUiThread(() -> Toast.makeText(this, fail.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
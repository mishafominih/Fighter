package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TournametnsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournametns_list);

        MyRequest request = new MyRequest();
        // Набираем данные для запроса
        request.put("key", "1234");
        // Сам запрос
        request.CallArray("get_tournaments", (res) -> {
            runOnUiThread(() -> {
                ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
                // fights.add(new Fight("Время/место", "Участники", "Счет"));
                for(int i = 0; i < res.length(); i++){
                    try {
                        JSONObject json = res.getJSONObject(i);
                        tournaments.add(new Tournament(
                                json.getString("name"),
                                json.getString("description"),
                                json.getString("status")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView fights_list = findViewById(R.id.list_view_tournaments_list);
                TournamentAdapter adapter = new TournamentAdapter(this, R.layout.tournament_list_item, tournaments);
                fights_list.setAdapter(adapter);
            });
        }, (fail) ->{
            runOnUiThread(() -> Toast.makeText(this, fail.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fighter.list_view_helpers.Tournament;
import com.example.fighter.list_view_helpers.TournamentAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TournamentsList extends AppCompatActivity {

    protected String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournametns_list);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null && arguments.containsKey("user_id")){
            user_id = arguments.get("user_id").toString();
        }

        findViewById(R.id.button_add_tournament).setOnClickListener((click) -> {
            Intent intent = new Intent(this, CreateTournament.class);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        });

    }

    private void update_tournaments() {
        MyRequest request = new MyRequest();
        // Набираем данные для запроса
        request.put("user", user_id);
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

    @Override
    protected void onResume() {
        super.onResume();
        update_tournaments();
    }
}
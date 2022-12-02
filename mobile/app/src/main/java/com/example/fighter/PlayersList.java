package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fighter.list_view_helpers.Player;
import com.example.fighter.list_view_helpers.PlayerAdapter;
import com.example.fighter.utils.TransportDataHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlayersList extends AppCompatActivity {

    protected String user_id;
    protected String tournament_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        TransportDataHelper helper = new TransportDataHelper(getIntent());
        user_id = helper.get("user_id");
        tournament_id = helper.get("tournament_id");

        findViewById(R.id.button_add_player).setOnClickListener((click) -> {
            Intent intent = new Intent(this, AddPlayer.class);
            intent.putExtra("user_id", user_id);
            intent.putExtra("tournament_id", tournament_id);
            String test = helper.get("categories");
            if(test != null)
                intent.putExtra("categories", test);
            startActivity(intent);
        });

    }

    private void update_players() {
        MyRequest request = new MyRequest();
        // Набираем данные для запроса
        request.put("user", user_id);
        request.put("tournament_id", tournament_id);
        // Сам запрос
        request.CallArray("get_players", (res) -> {
            runOnUiThread(() -> {
                ArrayList<Player> players = new ArrayList<Player>();
                // fights.add(new Fight("Время/место", "Участники", "Счет"));
                for(int i = 0; i < res.length(); i++){
                    try {
                        JSONObject json = res.getJSONObject(i);
                        players.add(new Player(json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView players_list = findViewById(R.id.list_view_players_list);
                PlayerAdapter adapter = new PlayerAdapter(this, R.layout.item_player, players);
                players_list.setAdapter(adapter);
            });
        }, (fail) ->{
            runOnUiThread(() -> Toast.makeText(this, fail.getMessage(), Toast.LENGTH_LONG).show());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_players();
    }
}
package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fighter.list_view_helpers.Fight;
import com.example.fighter.list_view_helpers.FightAdapter;
import com.example.fighter.utils.TransportDataHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FightList extends AppCompatActivity {
    String tournament_id;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_list);

        TransportDataHelper helper = new TransportDataHelper(getIntent());
        user_id = helper.get("user_id");
        tournament_id = helper.get("tournament_id");

        if(user_id == null) {
            findViewById(R.id.head_button).setOnClickListener(view -> {
                Intent login = new Intent(this, Login.class);
                startActivity(login);
            });
        }
        else{
            Button btn = findViewById(R.id.head_button);
            btn.setText("Выйти");
            btn.setOnClickListener(view -> {
                Intent start = new Intent(this, MainActivity.class);
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(start);
                this.finish();
            });
        }

        MyRequest request = new MyRequest();
        // Набираем данные для запроса
//        if(user_id != null)
//            request.put("user_id", user_id);
        request.put("tournament_id", tournament_id);
        request.CallArray("tournament_list", (res) -> {
            runOnUiThread(() -> {
                ArrayList<Fight> fights = new ArrayList<Fight>();
                for(int i = 0; i < res.length(); i++){
                    try {
                        JSONObject json = res.getJSONObject(i);
                        fights.add(new Fight(json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView fights_list = findViewById(R.id.list_view_tournament_list);
                FightAdapter adapter = new FightAdapter(this, R.layout.item_fight, fights);
                fights_list.setAdapter(adapter);

                fights_list.setOnItemClickListener((parent, view, position, id) -> {
                    if(user_id != null) {
                        Fight f = (Fight) fights_list.getItemAtPosition(position);
                        Intent intent = new Intent(parent.getContext(), AdminFight.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("tournament_id", tournament_id);
                        intent.putExtra("fight_id", f.Id);
                        intent.putExtra("fighter_one", f.Fighter_one.Base);
                        intent.putExtra("fighter_two", f.Fighter_two.Base);
                        intent.putExtra("place", f.Place);
                        startActivity(intent);
                    }
                });
            });
        }, (fail) ->{
            runOnUiThread(() -> Toast.makeText(this, fail.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
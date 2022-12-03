package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        MyRequest request = new MyRequest();
        // Набираем данные для запроса
        if(user_id != null)
            request.put("user_id", user_id);
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
            });
        }, (fail) ->{
            runOnUiThread(() -> Toast.makeText(this, fail.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
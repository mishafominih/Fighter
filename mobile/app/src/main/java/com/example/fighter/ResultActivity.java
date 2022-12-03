package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fighter.list_view_helpers.Player;
import com.example.fighter.list_view_helpers.PlayerAdapter;
import com.example.fighter.list_view_helpers.Result;
import com.example.fighter.list_view_helpers.ResultAdapter;
import com.example.fighter.utils.TransportDataHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    protected String user_id;
    protected String tournament_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViewById(R.id.head_button).setOnClickListener(view -> {
            Intent start = new Intent(this, MainActivity.class);
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start);
            this.finish();
        });

        TransportDataHelper helper = new TransportDataHelper(getIntent());
        user_id = helper.get("user_id");
        tournament_id = helper.get("tournament_id");

        MyRequest request = new MyRequest();
        // Набираем данные для запроса
        request.put("user", user_id);
        request.put("tournament_id", tournament_id);
        // Сам запрос
        request.CallArray("get_result", (res) -> {
            runOnUiThread(() -> {
                ArrayList<Result> results = new ArrayList<Result>();
                for(int i = 0; i < res.length(); i++){
                    try {
                        JSONObject json = res.getJSONObject(i);
                        results.add(new Result(json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ListView players_list = findViewById(R.id.list_view_result_list);
                ResultAdapter adapter = new ResultAdapter(this, R.layout.item_result, results);
                players_list.setAdapter(adapter);
            });
        }, (fail) ->{
            runOnUiThread(() -> Toast.makeText(this, fail.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
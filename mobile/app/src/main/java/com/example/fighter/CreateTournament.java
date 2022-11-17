package com.example.fighter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.example.fighter.list_view_helpers.Distribution;
import com.example.fighter.list_view_helpers.DistributionAdapter;
import com.example.fighter.list_view_helpers.Range;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateTournament extends AppCompatActivity {

    DistributionAdapter adapter;
    private int last_pos;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if(data != null){
                    ArrayList<String> min = data.getStringArrayListExtra("min");
                    ArrayList<String> max = data.getStringArrayListExtra("max");
                    adapter.update_item(min, max, last_pos);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);


        ArrayList<Distribution> distributions = new ArrayList<Distribution>();

        distributions.add(new Distribution("", ""));

        ListView fights_list = findViewById(R.id.list_view_distributions_list);
        adapter = new DistributionAdapter(this, R.layout.distribution_list_item, distributions, this);
        fights_list.setAdapter(adapter);

        findViewById(R.id.add_new_distribution).setOnClickListener((view) -> {
            adapter.add(new Distribution("", ""));
        });

        findViewById(R.id.button_create_tournament).setOnClickListener((view) -> {
            MyRequest request = new MyRequest();
            request.put("name", findViewById(R.id.name));
            request.put("description", findViewById(R.id.description));
            request.put("categories", create_categories());
//            request.Call("create_tournament", (res) -> {
//
//            });
        });
    }

    @NonNull
    private JSONArray create_categories() {
        JSONArray categories = new JSONArray();
        for(int i = 0; i < adapter.getCount(); i++){
            Distribution distribution = adapter.getItem(i);
            JSONObject item = new JSONObject();
            try {
                item.put("name", distribution.Name);
                JSONArray ranges = new JSONArray();
                for(Range r : distribution.Ranges){
                    JSONObject range = new JSONObject();
                    range.put("start", r.Min);
                    range.put("end", r.Max);
                    ranges.put(range);
                }
                item.put("range", ranges);
                categories.put(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public void change_distribution(int position, Intent intent){
        last_pos = position;
        mStartForResult.launch(intent);
    }
}
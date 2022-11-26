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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fighter.list_view_helpers.Distribution;
import com.example.fighter.list_view_helpers.DistributionAdapter;
import com.example.fighter.list_view_helpers.Range;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateTournament extends AppCompatActivity {

    protected String user_id;
    DistributionAdapter adapter;
    private int last_pos;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if(data != null){
                    ArrayList<String> values = data.getStringArrayListExtra("values");
                    adapter.update_item(values, last_pos);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null && arguments.containsKey("user_id")){
            user_id = arguments.get("user_id").toString();
        }

        ArrayList<Distribution> distributions = new ArrayList<Distribution>();

        ListView fights_list = findViewById(R.id.list_view_distributions_list);
        adapter = new DistributionAdapter(this, R.layout.distribution_list_item, distributions, this);
        fights_list.setAdapter(adapter);

        findViewById(R.id.add_new_distribution).setOnClickListener((view) -> {
            adapter.add(new Distribution("", ""));
        });

        findViewById(R.id.button_create_tournament).setOnClickListener((view) -> {
            String name = ((EditText)findViewById(R.id.name)).getText().toString();
            String description = ((EditText)findViewById(R.id.description)).getText().toString();
            JSONArray categories = create_categories();
            if(check_fields(name, description, categories)){
                MyRequest request = new MyRequest();
                request.put("name", name);
                request.put("user_id", user_id);
                request.put("description", description);
                if(categories.length() > 0)
                    request.put("categories", categories);
                request.Call("create_tournament", (res) -> {
                    finish();
                });
            }
        });
    }

    private boolean check_fields(String name, String description, JSONArray categories){
        if(name.length() < 3){
            return show_error("Слишком короткое название соревнования");
        }
        if(description.length() == 0){
            return show_error("Введите описание соревнования");
        }
        return true;
    }

    private boolean show_error(String error) {
        Toast.makeText(CreateTournament.this, error, Toast.LENGTH_LONG).show();
        return false;
    }

    @NonNull
    private JSONArray create_categories() {
        JSONArray categories = new JSONArray();
        for(int i = 0; i < adapter.getCount(); i++){
            Distribution distribution = adapter.getItem(i);
            JSONObject item = new JSONObject();
            try {
                item.put("name", distribution.Name);
                JSONArray values = new JSONArray();
                for(Range r : distribution.Ranges){
                    values.put(r.Value);
                }
                item.put("values", values);
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
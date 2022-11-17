package com.example.fighter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fighter.list_view_helpers.Distribution;
import com.example.fighter.list_view_helpers.DistributionAdapter;
import com.example.fighter.list_view_helpers.Range;
import com.example.fighter.list_view_helpers.RangeAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class ChangeDistribution extends AppCompatActivity {

    RangeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_distribution);

        Bundle arguments = getIntent().getExtras();
        TextView textView = findViewById(R.id.distribution_text_name);
        textView.append(arguments.get("distribution_name").toString());

        ArrayList<Range> ranges = new ArrayList<Range>();
        ArrayList<String> min = (ArrayList<String>) arguments.get("min");
        ArrayList<String> max = (ArrayList<String>) arguments.get("max");
        if(min != null){
            for(int i = 0; i < min.size(); i++){
                ranges.add(new Range(min.get(i), max.get(i)));
            }
        }

        ListView fights_list = findViewById(R.id.list_view_range_list);
        adapter = new RangeAdapter(this, R.layout.range_list_item, ranges);
        fights_list.setAdapter(adapter);

        findViewById(R.id.add_new_range).setOnClickListener((view) -> {
            adapter.add(new Range("", ""));
            adapter.notifyDataSetChanged();
        });

        findViewById(R.id.button_save_distribution).setOnClickListener((view) -> {
            save_ranges();
            finish();
        });
    }

    private void save_ranges() {
        ArrayList<String> min_set = new ArrayList<>();
        ArrayList<String> max_set = new ArrayList<>();
        for(int i = 0; i < adapter.getCount(); i++){
            Range range = adapter.getItem(i);
            min_set.add(range.Min);
            max_set.add(range.Max);
        }
        Intent data = new Intent();
        data.putExtra("min", min_set);
        data.putExtra("max", max_set);
        setResult(RESULT_OK, data);
        finish();
    }
}
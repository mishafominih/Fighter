package com.example.fighter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.example.fighter.list_view_helpers.Distribution;
import com.example.fighter.list_view_helpers.DistributionAdapter;
import com.example.fighter.list_view_helpers.Range;

import java.util.ArrayList;

public class CreateTournament extends AppCompatActivity {

    DistributionAdapter adapter;
    private int last_pos;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                ArrayList<String> min = data.getStringArrayListExtra("min");
                ArrayList<String> max = data.getStringArrayListExtra("max");
                adapter.update_item(min, max, last_pos);
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


    }

    public void change_distribution(int position, String name){
        last_pos = position;
        Intent intent = new Intent(this, ChangeDistribution.class);
        intent.putExtra("distribution_name", name);
        mStartForResult.launch(intent);
    }
}
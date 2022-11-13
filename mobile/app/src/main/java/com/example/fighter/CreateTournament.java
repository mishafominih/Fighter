package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateTournament extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);


        ArrayList<Distribution> distributions = new ArrayList<Distribution>();

        distributions.add(new Distribution(""));

        ListView fights_list = findViewById(R.id.list_view_distributions_list);
        DistributionAdapter adapter = new DistributionAdapter(this, R.layout.distribution_list_item, distributions);
        fights_list.setAdapter(adapter);
    }
}
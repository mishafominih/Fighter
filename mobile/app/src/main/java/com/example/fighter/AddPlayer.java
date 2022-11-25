package com.example.fighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fighter.list_view_helpers.TransportDataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddPlayer extends AppCompatActivity {

    protected String user_id;
    protected String tournament_id;
    protected JSONArray categories;
    protected View[] v_categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        TransportDataHelper helper = new TransportDataHelper(getIntent());
        user_id = helper.get("user_id");
        tournament_id = helper.get("tournament_id");
        String test = helper.get("categories");
        if(test != null){
            try {
                categories = new JSONArray(test);

                LinearLayout ll = findViewById(R.id.add_place);
                v_categories = new View[categories.length()];

                for(int i = 0; i < categories.length(); i++){
                    JSONObject cat = categories.getJSONObject(i);

                    LayoutInflater l = getLayoutInflater();
                    View v = l.inflate(R.layout.category_choose, null);
                    v_categories[i] = v;

                    JSONArray values = new JSONArray(cat.getString("values"));
                    String[] arr = new String[values.length()];
                    for(int j = 0; j < values.length(); j++){
                        arr[j] = values.getString(j);
                    }

                    Spinner spinner = v.findViewById(R.id.spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, arr);
                    spinner.setAdapter(adapter);

                    TextView textView = v.findViewById(R.id.cat_name);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setText(cat.getString("name"));

                    ll.addView(v);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        findViewById(R.id.button_add_player).setOnClickListener((view) -> {
            String name = ((EditText)findViewById(R.id.name)).getText().toString();
            String surname = ((EditText)findViewById(R.id.surname)).getText().toString();
            String lastname = ((EditText)findViewById(R.id.lastname)).getText().toString();
            String link = ((EditText)findViewById(R.id.link)).getText().toString();

            JSONArray categories = new JSONArray();
            for(int i = 0; i < this.categories.length(); i++){
                try {
                    JSONObject cat = new JSONObject();
                    View v = v_categories[i];

                    cat.put("name", ((TextView)v.findViewById(R.id.cat_name)).getText());
                    cat.put("value", ((Spinner)v.findViewById(R.id.spinner)).getSelectedItem().toString());
                    categories.put(cat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            MyRequest request = new MyRequest();
            request.put("tournament_id", tournament_id);
            request.put("user_id", user_id);
            request.put("name", name);
            request.put("surname", surname);
            request.put("patronymic", lastname);
            request.put("categories", categories);
            request.put("link", link);
            request.put("description", "");
            request.Call("add_player", (res) -> {
                finish();
            });
        });
    }
}
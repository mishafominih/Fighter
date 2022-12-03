package com.example.fighter.list_view_helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Result {
    public String Category;
    public String Data;
    public Result(JSONObject data) throws JSONException {
        parse_category(new JSONArray(data.getString("category")));
        parse_data(new JSONArray(data.getString("data")));
    }

    private void parse_category(JSONArray data) throws JSONException {
        String res = "";
        for(int i = 0; i < data.length(); i++){
            JSONObject cat = data.getJSONObject(i);
            res += cat.getString("name") + ": "  + cat.getString("value") + "; ";
        }
        Category = res;
    }

    private void parse_data(JSONArray data) throws JSONException {
        String res = "";
        for(int i = 0; i < data.length(); i++){
            Player player = new Player(data.getJSONObject(i));
            res += + (i + 1) + " место - " + player.Name + " " + player.Surname + " " + player.Lastname + "\n";
        }
        Data = res;
    }
}

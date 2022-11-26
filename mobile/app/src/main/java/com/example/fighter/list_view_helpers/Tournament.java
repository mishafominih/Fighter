package com.example.fighter.list_view_helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tournament {
    public String Id;
    public String Name;
    public String Description;
    public String Status;
    public JSONArray Categories;
    public Tournament(JSONObject data) throws JSONException {
        Id = data.getString("key");
        Name = data.getString("name");
        Description = data.getString("description");
        if(!data.isNull("categories"))
            Categories = new JSONArray(data.getString("categories"));
        switch (data.getString("status")) {
            case "0":
                Status = "Проведено";
                break;
            case "1":
                Status = "Идет сейчас";
                break;
            case "2":
                Status = "Запланировано";
                break;
        }
    }
}
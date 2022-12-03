package com.example.fighter.list_view_helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tournament {
    public String Id;
    public String Name;
    public String Description;
    public int Status;
    public JSONArray Categories;
    public Tournament(JSONObject data) throws JSONException {
        Id = data.getString("key");
        Name = data.getString("name");
        Description = data.getString("description");
        if(!data.isNull("categories"))
            Categories = new JSONArray(data.getString("categories"));
        Status = data.getInt("status");
    }

    public String GetStringStatus(){
        switch (Status) {
            case 0:
                return "Проведено";
            case 1:
                return "Идет сейчас";
            case 2:
                return "Запланировано";
        }
        return "";
    }
}
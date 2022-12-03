package com.example.fighter.list_view_helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Player {
    public String Name;
    public String Surname;
    public String Lastname;
    public JSONArray Categories;
    public String Link;
    public String Description;

    public Player(JSONObject data){
        try {
            Name = data.getString("name");
            Surname = data.getString("surname");
            Lastname = data.getString("patronymic");
            Link = data.getString("link");
            Description = data.getString("description");
            if(!data.isNull("categories"))
                Categories = new JSONArray(data.getString("categories"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String GetFIO(){
        String res = "";
        if(Name != null)
            res += Name;
        if(Surname != null)
            res += " " + Surname.charAt(0) + ".";
        if(Lastname != null)
            res += " " + Lastname.charAt(0) + ".";
        return res;
    }

    public String GetCategoriesStr(){
        String res = "";
        if(Categories != null)
            for(int i = 0; i < Categories.length(); i++){
                JSONObject cat = null;
                try {
                    cat = Categories.getJSONObject(i);
                    res += cat.getString("name") + ": " + cat.getString("value") + "; ";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        return res;
    }
}

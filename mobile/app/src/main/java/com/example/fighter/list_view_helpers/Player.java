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
            Categories = data.getJSONArray("categories");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String GetFIO(){
        return Name + " " + Surname.charAt(0) + "." + Lastname.charAt(0) + ".";
    }

    public String GetCategoriesStr(){
        String res = "";
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

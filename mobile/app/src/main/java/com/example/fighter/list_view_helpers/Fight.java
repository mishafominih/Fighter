package com.example.fighter.list_view_helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class Fight {
    public String TimePlace;
    public JSONObject Fighter_one;
    public JSONObject Fighter_two;
    public String Score = "";
    public Fight(JSONObject json) throws JSONException {
        TimePlace = json.getString("place");
        if( !json.isNull("fighter_one"))
            Fighter_one = json.getJSONObject("fighter_one");
        if( !json.isNull("fighter_two"))
            Fighter_two = json.getJSONObject("fighter_two");
        if( !json.isNull("score"))
            Score = json.getString("score");
    }

    public String GetTimePlace(){
        return TimePlace;
    }

    public String GetFigters(){
        try {
            if(Fighter_one != null && Fighter_two != null){
                return Fighter_one.getString("name") + " - " + Fighter_two.getString("name") ;
            }
            if(Fighter_one != null){
                return Fighter_one.getString("name") ;
            }
            if(Fighter_two != null) {
                return Fighter_two.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String GetScore(){
        return Score;
    }
}

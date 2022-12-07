package com.example.fighter.list_view_helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class Fight {
    public String Id;
    public String Place;
    public Player Fighter_one;
    public Player Fighter_two;
    public Player Winner;
    public String Score = "";
    public Fight(JSONObject json) throws JSONException {
        Place = json.getString("place");
        Id = json.getString("id");
        if( !json.isNull("fighter_one"))
            Fighter_one = new Player(json.getJSONObject("fighter_one"));
        if( !json.isNull("fighter_two"))
            Fighter_two = new Player(json.getJSONObject("fighter_two"));
        if( !json.isNull("winner"))
            Winner = new Player(json.getJSONObject("winner"));
        if( !json.isNull("score"))
            Score = json.getString("score");
    }

    public String GetTimePlace(){
        return Place;
    }

    public String GetFigters(){
        if(Fighter_one != null && Fighter_two != null){
            return Fighter_one.Name + " - " + Fighter_two.Name ;
        }
        if(Fighter_one != null){
            return Fighter_one.Name ;
        }
        if(Fighter_two != null) {
            return Fighter_two.Name;
        }
        return "";
    }

    public String GetScore(){
        if(Winner != null) {
            if(Winner.Id.equals(Fighter_one.Id)){
                return "1 : 0";
            }
            return "0 : 1";
        }
        return "";
    }
}

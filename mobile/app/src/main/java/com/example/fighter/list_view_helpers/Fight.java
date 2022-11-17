package com.example.fighter.list_view_helpers;

public class Fight {
    public String TimePlace;
    public String Fighters;
    public String Score;
    public Fight(String time, String place, String fighter_one, String fighter_two, String score){
        TimePlace = time.toString() + "*" + place.toString();
        Fighters = fighter_one.toString() + "-" + fighter_two.toString();
        Score = score;
    }

    public Fight(String time_place, String fighters, String score){
        TimePlace = time_place;
        Fighters = fighters;
        Score = score;
    }
    public String GetTimePlace(){
        return TimePlace;
    }

    public String GetFigters(){
        return Fighters;
    }

    public String GetScore(){
        return Score.toString();
    }
}

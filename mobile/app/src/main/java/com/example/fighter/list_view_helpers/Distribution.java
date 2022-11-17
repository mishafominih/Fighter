package com.example.fighter.list_view_helpers;

import java.util.ArrayList;

public class Distribution {
    public String Name;
    public String Description;
    public ArrayList<Range> Ranges;

    public Distribution(String name, String desc){
        Name = name;
        Description = desc;
        Ranges = new ArrayList<>();
    }

    public void UpdateRanges(ArrayList<Range> ranges){
        Ranges = ranges;
        UpdateDescription();
    }

    public void UpdateDescription(){
        StringBuilder res = new StringBuilder();
        for(Range r : Ranges){
            res.append(r.Min);
            res.append("-");
            res.append(r.Max);
            res.append("; ");
        }
        if(res.length() > 0)
            res.delete(res.length() - 2, res.length() - 1);
        Description = res.toString();
    }
}
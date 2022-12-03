package com.example.fighter.list_view_helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fighter.R;

import java.util.ArrayList;

public class FightAdapter extends ArrayAdapter<Fight> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Fight> fightList;

    public FightAdapter(Context context, int resource, ArrayList<Fight> fights) {
        super(context, resource, fights);
        this.fightList = fights;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Fight fight = fightList.get(position);

        viewHolder.time_place.setText(fight.GetTimePlace());
        viewHolder.fighters.setText(fight.GetFigters());
        viewHolder.score.setText(fight.GetScore());

        if(position % 2 == 0){
            viewHolder.base.setBackgroundColor(0xffffff);
        }

        return convertView;
    }
    private class ViewHolder {
        final LinearLayout base;
        final TextView time_place, fighters, score;
        ViewHolder(View view){
            time_place = view.findViewById(R.id.time_place);
            fighters = view.findViewById(R.id.fighters);
            score = view.findViewById(R.id.score);
            base = view.findViewById(R.id.base);
        }
    }
}

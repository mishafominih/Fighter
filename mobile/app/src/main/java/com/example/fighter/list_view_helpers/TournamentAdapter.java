package com.example.fighter.list_view_helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fighter.R;

import java.util.ArrayList;

public class TournamentAdapter extends ArrayAdapter<Tournament> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Tournament> tournamentsList;

    public TournamentAdapter(Context context, int resource, ArrayList<Tournament> tournaments) {
        super(context, resource, tournaments);
        this.tournamentsList = tournaments;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final TournamentAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new TournamentAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (TournamentAdapter.ViewHolder) convertView.getTag();
        }
        final Tournament tournament = tournamentsList.get(position);

        viewHolder.name.setText(tournament.Name);
        viewHolder.desc.setText(tournament.Description);
        viewHolder.status.setText(tournament.Status);

        return convertView;
    }
    private class ViewHolder {
        final TextView name, desc, status;
        ViewHolder(View view){
            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.desc);
            status = view.findViewById(R.id.status);
        }
    }
}

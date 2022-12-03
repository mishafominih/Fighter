package com.example.fighter.list_view_helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fighter.R;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Player> playersList;

    public PlayerAdapter(Context context, int resource, ArrayList<Player> players) {
        super(context, resource, players);
        this.playersList = players;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final PlayerAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new PlayerAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (PlayerAdapter.ViewHolder) convertView.getTag();
        }
        final Player player = playersList.get(position);

        viewHolder.name.setText(player.GetFIO());
        viewHolder.categories.setText(player.GetCategoriesStr());
        viewHolder.delBtn.setOnClickListener((view) -> {
            playersList.remove(position);
            notifyDataSetChanged();
        });


        return convertView;
    }
    private class ViewHolder {
        final ImageView delBtn;
        final TextView name, categories;
        ViewHolder(View view){
            name = view.findViewById(R.id.name);
            categories = view.findViewById(R.id.categories);
            delBtn = view.findViewById(R.id.image_del_range);
        }
    }
}

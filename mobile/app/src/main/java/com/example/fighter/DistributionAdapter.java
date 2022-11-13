package com.example.fighter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DistributionAdapter extends ArrayAdapter<Distribution> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Distribution> distributionsList;

    DistributionAdapter(Context context, int resource, ArrayList<Distribution> tournaments) {
        super(context, resource, tournaments);
        this.distributionsList = tournaments;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final DistributionAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new DistributionAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (DistributionAdapter.ViewHolder) convertView.getTag();
        }
        final Distribution distribution = distributionsList.get(position);

        // `viewHolder.name.setText(distribution.Name);

        return convertView;
    }
    private class ViewHolder {
        final TextView name;
        ViewHolder(View view){
            name = view.findViewById(R.id.name);
        }
    }
}

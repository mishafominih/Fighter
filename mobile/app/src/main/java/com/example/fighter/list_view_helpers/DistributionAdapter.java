package com.example.fighter.list_view_helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;

import com.example.fighter.ChangeDistribution;
import com.example.fighter.CreateTournament;
import com.example.fighter.R;

import java.util.ArrayList;

public class DistributionAdapter extends ArrayAdapter<Distribution> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Distribution> distributionsList;
    private Context context;
    private CreateTournament parent;

    public DistributionAdapter(Context context, int resource, ArrayList<Distribution> tournaments, CreateTournament parent) {
        super(context, resource, tournaments);
        this.distributionsList = tournaments;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.parent = parent;
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

        viewHolder.name.setText(distribution.Name);
        viewHolder.name.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                EditText et =(EditText)v.findViewById(R.id.name);
                distributionsList.get(position).Name = et.getText().toString().trim();
            }
        });

        viewHolder.desc.setText(distribution.Description);
        viewHolder.desc.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                TextView et =(TextView)v.findViewById(R.id.desc);
                distributionsList.get(position).Description = et.getText().toString().trim();
            }
        });

        viewHolder.ChangeDistribution.setOnClickListener((view) -> {
            Intent intent = new Intent(parent.getContext(), ChangeDistribution.class);
            Distribution distr = distributionsList.get(position);
            distr.Name = viewHolder.name.getText().toString();
            intent.putExtra("distribution_name", distr.Name);
            if(distr.Ranges.size() > 0){
                ArrayList<String> values = new ArrayList<>();
                for(Range r : distr.Ranges){
                    values.add(r.Value);
                }
                intent.putExtra("values", values);
            }
            this.parent.change_distribution(position, intent);
        });

        viewHolder.DeleteDistribution.setOnClickListener((view) -> {
            distributionsList.remove(position);
            notifyDataSetChanged();
        });
        return convertView;
    }
    private class ViewHolder {
        final EditText name;
        final TextView desc;
        final Button ChangeDistribution;
        final Button DeleteDistribution;
        ViewHolder(View view){
            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.desc);
            ChangeDistribution = view.findViewById(R.id.change_distribution);
            DeleteDistribution = view.findViewById(R.id.delete_distribution);
        }
    }

    public void update_item(ArrayList<String> values, int position){
        Distribution distribution = getItem(position);
        ArrayList<Range> ranges = new ArrayList<>();
        for(int i = 0; i < values.size(); i++){
            ranges.add(new Range(values.get(i)));
        }
        distribution.UpdateRanges(ranges);
        notifyDataSetChanged();
    }
}

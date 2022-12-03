package com.example.fighter.list_view_helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fighter.R;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<Result> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Result> resultsList;

    public ResultAdapter(Context context, int resource, ArrayList<Result> results) {
        super(context, resource, results);
        this.resultsList = results;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ResultAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ResultAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ResultAdapter.ViewHolder) convertView.getTag();
        }
        final Result res = resultsList.get(position);


        viewHolder.category.setText(res.Category);
        viewHolder.data.setText(res.Data);

        return convertView;
    }

    private class ViewHolder {
        final TextView category, data;
        ViewHolder(View view){
            category = view.findViewById(R.id.category_name);
            data = view.findViewById(R.id.category_data);
        }
    }
}

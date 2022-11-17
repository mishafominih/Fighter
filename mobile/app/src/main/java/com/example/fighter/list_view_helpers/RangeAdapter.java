package com.example.fighter.list_view_helpers;

import android.content.Context;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.fighter.R;

import java.util.ArrayList;

public class RangeAdapter extends ArrayAdapter<Range>{
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Range> rangeList;

    public RangeAdapter(Context context, int resource, ArrayList<Range> tournaments) {
        super(context, resource, tournaments);
        this.rangeList = tournaments;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final RangeAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new RangeAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (RangeAdapter.ViewHolder) convertView.getTag();
        }
        final Range range = rangeList.get(position);


        viewHolder.min.setText(range.Min);
        viewHolder.min.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                EditText et =(EditText)v.findViewById(R.id.min);
                rangeList.get(position).Min = et.getText().toString().trim();
            }
        });
        viewHolder.max.setText(range.Max);
        viewHolder.max.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                EditText et =(EditText)v.findViewById(R.id.max);
                rangeList.get(position).Max = et.getText().toString().trim();
            }
        });

        viewHolder.delBtn.setOnClickListener((view) -> {
            rangeList.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }

    private class ViewHolder {
        final TextView min, max;
        final ImageView delBtn;
        ViewHolder(View view){
            min = view.findViewById(R.id.min);
            max = view.findViewById(R.id.max);
            delBtn = view.findViewById(R.id.image_del_range);
        }
    }
}

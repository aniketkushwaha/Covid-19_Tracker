package com.example.android.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class list_adapter extends ArrayAdapter {
    public list_adapter(@NonNull Context context, ArrayList covid_list) {
        super(context, 0,covid_list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView =convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        covid_cases current_case= (covid_cases) getItem(position);
        TextView State=listItemView.findViewById(R.id.state);
        State.setText(current_case.getmState());

        TextView confirmed=listItemView.findViewById(R.id.confirmed);
        confirmed.setText(Integer.toString(current_case.getmConfirmed()));

        TextView active = listItemView.findViewById(R.id.active);
        active.setText(Integer.toString(current_case.getmActive()));

        TextView Deaths = listItemView.findViewById(R.id.deaths);
        Deaths.setText(Integer.toString(current_case.getmDeaths()));
        return listItemView;
    }
}

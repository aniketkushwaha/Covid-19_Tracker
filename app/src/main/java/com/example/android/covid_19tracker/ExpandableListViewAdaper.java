package com.example.android.covid_19tracker;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewAdaper extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<covid_cases> List;

    public ExpandableListViewAdaper(Context context, ArrayList<covid_cases> list) {
        this.context = context;
        List = list;

    }

    @Override
    public int getGroupCount() {
        return List.size();
    }

    @Override
    public int getChildrenCount(int i) {
        ArrayList<Districts> districts=List.get(i).getmDistricts();
        return districts.size();
    }

    @Override
    public Object getGroup(int i) {
        return List.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        ArrayList<Districts>districts= List.get(i).getmDistricts();
        return  districts.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
        }
         covid_cases current_case = (covid_cases)getGroup(i);

        TextView State= view.findViewById(R.id.state);
        State.setText(current_case.getmState());

        TextView confirmed=view.findViewById(R.id.confirmed);
        confirmed.setText(Integer.toString(current_case.getmConfirmed()));

        TextView active = view.findViewById(R.id.active);
        active.setText(Integer.toString(current_case.getmActive()));

        TextView Deaths = view.findViewById(R.id.deaths);
        Deaths.setText(Integer.toString(current_case.getmDeaths()));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Log.v("child", " in getChildView()");
        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.child_list,viewGroup,false);
        }
        Districts current_case= (Districts)getChild(i,i1);
        TextView State= view.findViewById(R.id.state);
        State.setText(current_case.getMdistrict());
        GradientDrawable rec = (GradientDrawable) State.getBackground();

        String zonecolor=current_case.getMzone();
        if (zonecolor.equals("Red")){
            rec.setColor(context.getResources().getColor(R.color.RedZone));
        }else if(zonecolor.equals("Green")){
             rec.setColor(context.getResources().getColor(R.color.GreenZone));
        }else{
             rec.setColor(context.getResources().getColor(R.color.OrangeZone));
        }

        TextView confirmed=view.findViewById(R.id.confirmed);
        confirmed.setText(Integer.toString(current_case.getMconfirmed()));


        TextView active = view.findViewById(R.id.active);
        active.setText(Integer.toString(current_case.getMactive()));

        TextView Deaths = view.findViewById(R.id.deaths);
        Deaths.setText(Integer.toString(current_case.getMdeaths()));


        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    public void setData(ArrayList<covid_cases> data) {
        List.addAll(data);
        notifyDataSetChanged();
    }
}

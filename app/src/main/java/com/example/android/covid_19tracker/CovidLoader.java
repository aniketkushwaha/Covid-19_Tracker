package com.example.android.covid_19tracker;

import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.net.MalformedURLException;
import java.util.List;

public class CovidLoader extends AsyncTaskLoader {
 String murl;
    public CovidLoader(@NonNull Context context,String url) {
        super(context);
        murl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Object loadInBackground() {
           if(murl==null) {
               return null;
           }
        List<covid_cases> cases= null;
        try {
            cases = QueryUtils.fetchCovidData(murl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return cases;
    }
}

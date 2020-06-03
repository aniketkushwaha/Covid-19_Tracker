package com.example.android.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CovidActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<covid_cases>> {
   private ExpandableListViewAdaper mAdapter;
   Context context=CovidActivity.this;
    ExpandableListView listView;
    public static int LoaderId=1;
    TextView emptyView;
   private String url ="https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";

    @NonNull
    @Override
    public Loader<List<covid_cases>> onCreateLoader(int id, @Nullable Bundle args) {

        return new CovidLoader(this,url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<covid_cases>> loader, List<covid_cases> data) {
              View progress=findViewById(R.id.progress_circular);
              progress.setVisibility(View.GONE);

              emptyView.setText(R.string.empty);
              mAdapter.setData(new ArrayList<covid_cases>());
              if(data!=null && !data.isEmpty()){
                  mAdapter.setData((ArrayList<covid_cases>) data);
              }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<covid_cases>> loader) {
        mAdapter.setData(new ArrayList<covid_cases>());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_list_view);
        emptyView =findViewById(R.id.emptyView);

         listView = findViewById(R.id.list);
         listView.setEmptyView(emptyView);
        mAdapter =new ExpandableListViewAdaper(context,new ArrayList<covid_cases>());
        listView.setAdapter(mAdapter);

        ConnectivityManager manager=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            getLoaderManager().initLoader(LoaderId, null, this).forceLoad();
        }
         else{
             View progress =findViewById(R.id.progress_circular);
             progress.setVisibility(View.GONE);
             emptyView.setText(R.string.empty);
        }
    }

}

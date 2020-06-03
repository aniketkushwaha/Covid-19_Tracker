package com.example.android.covid_19tracker;

import java.util.ArrayList;

public class covid_cases {
    String mState,mDistrict;
    int mConfirmed,mActive,mDeaths;
    ArrayList<Districts> mDistricts;
    covid_cases(String state,int confirmed,int active,int deaths,ArrayList<Districts> districts){
        mState=state;
        mConfirmed=confirmed;
        mActive=active;
        mDeaths=deaths;
        mDistricts =districts;
    }

   public String getmState(){return mState;}
   public int getmConfirmed(){return mConfirmed;}
   public int getmActive(){return mActive;}
   public int getmDeaths(){return mDeaths;}

   public ArrayList<Districts> getmDistricts(){return mDistricts;}
}

package com.example.android.covid_19tracker;

public class Districts {
    String mdistric,mzone;
    int mactive,mconfirmed,mdeaths;


    public Districts(String mdistric,  int mconfirmed,int mactive, int mdeaths,String zone) {
        this.mdistric = mdistric;
        this.mactive = mactive;
        this.mconfirmed = mconfirmed;
        this.mdeaths = mdeaths;
        mzone=zone;
    }

    public String getMdistrict() {
        return mdistric;
    }
 public int getMconfirmed(){
        return mconfirmed;
 }
    public int getMactive() {
        return mactive;
    }

    public int getMdeaths() {
        return mdeaths;
    }
    public String getMzone(){return mzone;}
}

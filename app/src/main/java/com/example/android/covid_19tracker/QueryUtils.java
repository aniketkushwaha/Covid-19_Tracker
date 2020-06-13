package com.example.android.covid_19tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiEnterpriseConfig;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;

public class QueryUtils {
    private  static final  String ZoneUrl="https://api.covid19india.org/zones.json",LOG_TAG=QueryUtils.class.getSimpleName(),districtUrl="https://api.covid19india.org/v2/state_district_wise.json";
    private static String zonesJsonResponse;
    private static JSONObject ZonesJsonObject;
    private static JSONArray ZonesArray;
    public static ArrayList<covid_cases> fetchCovidData(String url) throws MalformedURLException {
        URL request_url= createURL(url);
        String JSONresponse="";
        try {
            JSONresponse=makeHTTPrequest(request_url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Cannot make the http request",e);
        }
        ArrayList<covid_cases> cases=ExtractJSONresponse(JSONresponse);
        return cases;
    }
    private static URL createURL(String stringurl) throws MalformedURLException {
      URL url=null;
      try{
          url=new URL(stringurl);
      }catch (MalformedURLException e){
          Log.e(LOG_TAG,"Error creating URL ",e);
          return null;
      }
      return url;
    }
    private static String makeHTTPrequest(URL url) throws IOException {
         String jsonResponseReceived="";
         if(url==null){
             return null;
         }
        HttpURLConnection httpURLConnection =null;
        InputStream inputStream=null;
         try{
             httpURLConnection=(HttpURLConnection)url.openConnection();
             httpURLConnection.setRequestMethod("GET");
             httpURLConnection.setConnectTimeout(15000);
             httpURLConnection.setReadTimeout(10000);
             httpURLConnection.connect();
             int response_code=httpURLConnection.getResponseCode();
             if(response_code==200){
                 inputStream=httpURLConnection.getInputStream();
                 jsonResponseReceived=readInputStream(inputStream);
             }else {
                 Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
             }
         } catch (IOException e) {
             e.printStackTrace();
         }finally {
             if (httpURLConnection!=null){
                 httpURLConnection.disconnect();
             }
             if(inputStream!=null){
                 inputStream.close();
             }
             return jsonResponseReceived;
         }
    }
    private static String readInputStream(InputStream inputStream) throws IOException {
       StringBuilder output=new StringBuilder();
       if(inputStream!=null){
           InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
           BufferedReader reader=new BufferedReader(inputStreamReader);
           String line=reader.readLine();
       while(line!=null){
           output.append(line);
           line=reader.readLine();
       }}
       return output.toString();

    }
    public static ArrayList<covid_cases> ExtractJSONresponse(String jsonResponse) throws MalformedURLException {
        ArrayList<covid_cases> cases=new ArrayList<covid_cases>();
        String districtsJSON = fetchDistrictsData(districtUrl);
        zonesJsonResponse=fetchDistrictsData(ZoneUrl);


        ArrayList<Districts> districtList;
        try{

            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONObject data=jsonObject.getJSONObject("data");
            JSONArray jsonArray=data.getJSONArray("statewise");
            for(int i=0;i<jsonArray.length();i++){
                districtList=new ArrayList<Districts>();
                JSONObject covidCase=jsonArray.getJSONObject(i);
                districtList=ExtractDistrictInfo(districtsJSON,covidCase.getString("state"));

                cases.add(new covid_cases(covidCase.getString("state"),covidCase.getInt("confirmed"),covidCase.getInt("active"),covidCase.getInt("deaths"),districtList));
            }


        }catch (JSONException e){
            Log.e(LOG_TAG,"Error Parsing The Json Response ",e);
        }

        return cases;
    }
    public static ArrayList<Districts> ExtractDistrictInfo(String json,String stateName){
        ArrayList<Districts> district=new ArrayList<Districts>();

        try{
            ZonesJsonObject = new JSONObject(zonesJsonResponse);
            ZonesArray=ZonesJsonObject.getJSONArray("zones");
            JSONArray data =new JSONArray(json);
            for(int i=0;i<data.length();i++) {
                JSONObject state = data.getJSONObject(i);
                String name=state.getString("state");
                if ( name.equals(stateName)) {

                    JSONArray districtData = state.getJSONArray("districtData");
                    for (int j = 0; j < districtData.length(); j++) {
                        JSONObject particularDistricts = districtData.getJSONObject(j);
                        for (int k=0;k<ZonesArray.length();k++){
                            JSONObject dis= ZonesArray.getJSONObject(k);
                            if(dis.getString("district").equals(particularDistricts.getString("district"))){
                                district.add(new Districts(particularDistricts.getString("district"), particularDistricts.getInt("confirmed"), particularDistricts.getInt("active"), particularDistricts.getInt("deceased"),dis.getString("zone")));

                            }
                        }

                    }
                }
            }
        }catch (JSONException e){
            Log.e(LOG_TAG,"Error Parsing The District Json Response ",e);
        }
        return district;
    }


    private static String fetchDistrictsData(String  url) throws MalformedURLException {
        URL request_url= createURL(url);

        String JSONresponse="";
        try {
            JSONresponse=makeHTTPrequest(request_url);

        }catch (IOException e){
            Log.e(LOG_TAG,"Cannot make the http request",e);
        }
        return JSONresponse;
    }


}

package com.example.souravpk.choriyedao1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by sourav pk on 9/14/2017.
 */

public class API_CALLER extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        String inputLine = "";

        try {
            String authToken = strings[0];
            String lastNotificationId = strings[1];
            //Create a URL object holding our url
            URL myUrl = new URL("http://code--projects.com/api/mobile/"+authToken+"?last="+lastNotificationId);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod("GET");

            //Connect to our url
            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            response = stringBuilder.toString();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    } // doInBackground()

    @Override
    protected void onPostExecute(String jsonStr) {
        //super.onPostExecute(s);
        Log.d("response", jsonStr);
        if(jsonStr != null){
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                int len = jsonArray.length();
                for (int i=0 ; i < len ; i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Log.d("parsed data", jsonObj.getString("academic_session") );
                }
            }catch (Exception e){

            }
        }
    } // onPostExecute()
}

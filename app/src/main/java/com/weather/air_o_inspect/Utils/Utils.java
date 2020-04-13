package com.weather.air_o_inspect.Utils;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.air_o_inspect.Entities.CurrentStatus;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.MyApp;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public String getDataFromUrl(String longLat, String query) {

        Log.i("BackgroundTask","Getting data from url");
        HttpURLConnection connection = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            String key = "c4ad847858ecf2e09f5ea405bea37b12";
            String darkskyUrl = "https://api.darksky.net/forecast/" + key + "/" + longLat + "?" + query;
            URL url = new URL(darkskyUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                // read, split data, and add to weather data
                result.append(line);
            }
        } catch (Exception e) {
            Log.e("getDataFromUrlWrit 1", "Exception in Utils", e);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                Log.d("getDataFromUrlWrit 2", e.toString());
            }
            if (connection != null)
                connection.disconnect();
        }
        return result.toString();
    }

    public List<WeatherUpdate> convertJsonToWeatherUpdateList(String result) {
        List<WeatherUpdate> weatherUpdateList = null;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONArray finalResultJson = resultJson.getJSONObject("hourly").getJSONArray("data");
            weatherUpdateList = new ArrayList<>();
            for (int i = 0; i < finalResultJson.length(); i++) {
                WeatherUpdate weatherUpdate = null;

                JSONObject obj = (JSONObject) finalResultJson.get(i);

                weatherUpdate = new WeatherUpdate(obj.getLong("time"),
                        Float.valueOf("" + obj.get("precipIntensity")), Float.valueOf("" + obj.get("precipProbability")),
                        Float.valueOf("" + obj.get("temperature")), Float.valueOf("" + obj.get("pressure")),
                        Float.valueOf("" + obj.get("windSpeed")),
                        Float.valueOf("" + obj.get("windGust")), Float.valueOf("" + obj.get("cloudCover")),
                        Float.valueOf("" + obj.get("visibility")));
                weatherUpdate.setCurrentTime(obj.getLong("time"));

                weatherUpdateList.add(weatherUpdate);
            }

        } catch (JSONException e) {
            Log.e("convertJsonToCSV", "" , e);
        }

        return weatherUpdateList;
    }
    public CurrentStatus convertJsonToCurrentStatus(String result) {
        CurrentStatus currentStatus = null;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONObject obj = resultJson.getJSONObject("currently");

            currentStatus = new CurrentStatus(obj.getLong("time"),
                    Float.valueOf("" + obj.get("precipIntensity")), Float.valueOf("" + obj.get("precipProbability")),
                    Float.valueOf("" + obj.get("temperature")), Float.valueOf("" + obj.get("pressure")),
                    Float.valueOf("" + obj.get("windSpeed")),
                    Float.valueOf("" + obj.get("windGust")), Float.valueOf("" + obj.get("cloudCover")),
                    Float.valueOf("" + obj.get("visibility")));
            currentStatus.setCurrentTime(obj.getLong("time"));

        } catch (JSONException e) {
            Log.e("convertJsonToCSV", "" , e);
        }

        return currentStatus;
    }

    public Preferences getPreferences(){
        Float[] COLUMN_THRESOLD = MyApp.getColumnsThresold();
        Boolean[] COLUMN_SWITCH = MyApp.getColumnsSwitch();
        return new Preferences(COLUMN_THRESOLD[0], COLUMN_THRESOLD[1], COLUMN_THRESOLD[2], COLUMN_THRESOLD[3], COLUMN_THRESOLD[4],
                COLUMN_THRESOLD[5], COLUMN_THRESOLD[6],COLUMN_THRESOLD[7], COLUMN_SWITCH[0], COLUMN_SWITCH[1], COLUMN_SWITCH[2],
                COLUMN_SWITCH[3], COLUMN_SWITCH[4], COLUMN_SWITCH[5], COLUMN_SWITCH[6], COLUMN_SWITCH[7]);
    }
}

package com.weather.air_o_inspect.service;

import android.content.Context;
import android.util.Log;

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

public class Utils {

    public String getDataFromUrlWriteToCSV(String longLat, String query) {
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
            Log.d("getDataFromUrlWrit 1", e.toString());
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

    public void saveCSVFile(Context context, String result, String[] filename) {
        FileOutputStream[] fos = null;
        try {
            String[] data = convertJsonToCSV(result, filename.length);
            Log.i("indexTime1", "Reached");

            File[] file = new File[data.length];

            fos = new FileOutputStream[data.length];

            for (int i = 0; i < data.length; i++) {
                if (i < filename.length) {
                    file[i] = new File(context.getExternalFilesDir(null), filename[i]);
                    Log.i("saveCSVFile 1", "after file");
                } else {
                    file[i] = new File(context.getExternalFilesDir(null), "Currentforecast.csv");
                    Log.i("saveCSVFile 1", "after file");
                }
            }

            for (int i = 0; i < file.length; i++) {
                fos[i] = new FileOutputStream(file[i]);
                fos[i].write(data[i].getBytes());
                Log.i("saveCSVFile 3", "after file write");
            }

        } catch (Exception e) {
            Log.d("saveCSVFile 1", e.toString());
        } finally {
            try {
                if (fos != null) {
                    for (FileOutputStream f : fos) {
                        f.flush();
                        f.close();
                    }
                }
            } catch (IOException e) {
                Log.d("saveCSVFile 2", e.toString());
            }
        }
    }

    public String[] convertJsonToCSV(String result, Integer filenameLength) {
        String[] resultCSV = null;
        String convertedCSV;
        String currentConvertedCSV;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONObject jsonObject = resultJson.getJSONObject("currently");
            jsonObject.remove("precipType");
            String currentWeather = "[" + jsonObject.toString() + "]";
            JSONArray currentWeatherJson = new JSONArray(currentWeather);
            JSONArray finalResultJson = resultJson.getJSONObject("hourly").getJSONArray("data");
            convertedCSV = CDL.toString(finalResultJson);
            currentConvertedCSV = CDL.toString(currentWeatherJson);

            resultCSV = new String[filenameLength + 1];

            String[] csvRows = convertedCSV.split("\n");
            String[] currentWeatherCSV = currentConvertedCSV.split("\n");

            int halfNoRows = (csvRows.length - 1) / 2;

            int i;

            resultCSV[0] = csvRows[0] + "\n";
            resultCSV[1] = csvRows[0] + "\n";
            resultCSV[2] = currentWeatherCSV[0] + "\n" + currentWeatherCSV[1];

            for (i = 1; i < halfNoRows + 1; i++) {

                resultCSV[0] += csvRows[i];

                if (i != halfNoRows) {

                    resultCSV[0] += "\n";

                }

            }


            for (; i < csvRows.length; i++) {

                resultCSV[1] += csvRows[i];

                if (i != (csvRows.length - 1)) {

                    resultCSV[1] += "\n";

                }

            }

        } catch (JSONException e) {
            Log.e("convertJsonToCSV", "" , e);
        }
        return resultCSV;
    }
}

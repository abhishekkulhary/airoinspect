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
        FileOutputStream fos = null;
        try {
            String[] data = convertJsonToCSV(result);

            File file = new File(context.getExternalFilesDir(null), filename[0]);
            Log.i("saveCSVFile 1", "after file");
            fos = new FileOutputStream(file);
            fos.write(data[0].getBytes());

            File file1 = new File(context.getExternalFilesDir(null), filename[1]);
            Log.i("saveCSVFile 1", "after file");
            fos = new FileOutputStream(file1);
            fos.write(data[1].getBytes());
            Log.i("saveCSVFile 2", "after outputstream");
        } catch (Exception e) {
            Log.d("saveCSVFile 1", e.toString());
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                Log.d("saveCSVFile 2", e.toString());
            }
        }
    }

    public String[] convertJsonToCSV(String result) {
        String[] resultCSV = null;
        String convertedCSV;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONArray finalResultJson = resultJson.getJSONObject("hourly").getJSONArray("data");
            convertedCSV = CDL.toString(finalResultJson);

            resultCSV = new String[]{"", ""};

            String[] csvRows = convertedCSV.split("\n");

            int halfNoRows = (csvRows.length - 1) / 2;

            int i;

            resultCSV[0] += csvRows[0] + "\n";
            resultCSV[1] += csvRows[0] + "\n";

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
            Log.d("convertJsonToCSV", "" + e);
        }
        return resultCSV;
    }
}

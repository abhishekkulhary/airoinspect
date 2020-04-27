package com.weather.air_o_inspect.Database;

import android.util.Log;

import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    public String getDataFromUrl(String longLat, String query) {

        Log.i("BackgroundTask", "Getting data from url");
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
            Log.e("getDataFromUrlWrit 1", "Exception in DatabaseUtils", e);
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

    public List<WeatherForecast> convertJsonToWeatherForecastList(String result) {
        List<WeatherForecast> weatherForecastList = null;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONArray finalResultJson = resultJson.getJSONObject("hourly").getJSONArray("data");
            weatherForecastList = new ArrayList<>();
            for (int i = 0; i < finalResultJson.length(); i++) {
                WeatherForecast weatherForecast;

                JSONObject obj = (JSONObject) finalResultJson.get(i);

                weatherForecast = new WeatherForecast(i, obj.getLong("time"),
                        Float.valueOf("" + obj.get("precipIntensity")), Float.parseFloat("" + obj.get("precipProbability")) * 100.0f,
                        Float.valueOf("" + obj.get("temperature")), Float.valueOf("" + obj.get("pressure")),
                        Float.valueOf("" + obj.get("windSpeed")),
                        Float.valueOf("" + obj.get("windGust")), Float.valueOf("" + obj.get("cloudCover")),
                        Float.valueOf("" + obj.get("visibility")));
                weatherForecastList.add(weatherForecast);
            }

        } catch (JSONException e) {
            Log.e("convertJsonToCSV", "", e);
        }

        return weatherForecastList;
    }

    public WeatherCurrent convertJsonToWeatherCurrent(String result) {
        WeatherCurrent weatherCurrent = null;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONObject obj = resultJson.getJSONObject("currently");

            weatherCurrent = new WeatherCurrent(obj.getLong("time"),
                    Float.valueOf("" + obj.get("precipIntensity")), Float.parseFloat("" + obj.get("precipProbability")) * 100.0f,
                    Float.valueOf("" + obj.get("temperature")), Float.valueOf("" + obj.get("pressure")),
                    Float.valueOf("" + obj.get("windSpeed")),
                    Float.valueOf("" + obj.get("windGust")), Float.valueOf("" + obj.get("cloudCover")),
                    Float.valueOf("" + obj.get("visibility")), Double.valueOf("" + resultJson.get("latitude")),
                    Double.valueOf("" + resultJson.get("longitude")));

        } catch (JSONException e) {
            Log.e("convertJsonToCSV", "", e);
        }

        return weatherCurrent;
    }

    public List<WeatherForecastDaily> convertJsonToWeatherForecastDaily(String result) {
        List<WeatherForecastDaily> weatherForecastDailyList = null;
        try {
            JSONObject resultJson = new JSONObject(result);
            JSONArray finalResultJson = resultJson.getJSONObject("daily").getJSONArray("data");
            weatherForecastDailyList = new ArrayList<>();
            for (int i = 0; i < finalResultJson.length(); i++) {
                WeatherForecastDaily weatherForecastDaily;

                JSONObject obj = (JSONObject) finalResultJson.get(i);

                weatherForecastDaily = new WeatherForecastDaily(i, obj.getLong("time"), obj.getLong("sunriseTime"),
                        obj.getLong("sunsetTime"), Float.valueOf("" + obj.get("moonPhase")),
                        Float.valueOf("" + obj.get("precipIntensity")), Float.valueOf("" + obj.get("precipIntensityMax")),
                        obj.getLong("precipIntensityMaxTime"), Float.parseFloat("" + obj.get("precipProbability")) * 100.0f,
                        obj.getString("precipType"), Float.valueOf("" + obj.get("temperatureHigh")),
                        obj.getLong("temperatureHighTime"), Float.valueOf("" + obj.get("temperatureLow")),
                        obj.getLong("temperatureLowTime"), Float.valueOf("" + obj.get("dewPoint")),
                        Float.valueOf("" + obj.get("humidity")), Float.valueOf("" + obj.get("pressure")),
                        Float.valueOf("" + obj.get("windSpeed")), Float.valueOf("" + obj.get("windGust")),
                        obj.getLong("windGustTime"), Float.valueOf("" + obj.get("windBearing")),
                        Float.valueOf("" + obj.get("cloudCover")), Float.valueOf("" + obj.get("uvIndex")),
                        obj.getLong("uvIndexTime"), Float.valueOf("" + obj.get("visibility")),
                        Float.valueOf("" + obj.get("ozone")));
                weatherForecastDailyList.add(weatherForecastDaily);
            }

        } catch (JSONException e) {
            Log.e("convertJsonToCSV", "", e);
        }

        return weatherForecastDailyList;
    }

}

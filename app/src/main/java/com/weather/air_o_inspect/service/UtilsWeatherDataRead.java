package com.weather.air_o_inspect.service;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.settings.Preferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class UtilsWeatherDataRead {

    private Context context;
    private MyApplication myApplication;
    private ArrayList<Long> xValues;

    public UtilsWeatherDataRead(Context context) {
        super();
        this.context = context;
        this.myApplication = new MyApplication();
    }

    public Map<String, List<String>> getCurrentWeatherConditions(Map<String, Map<String, ArrayList<Float>>> weatherData) {
        Log.i("getCurrentWeatherCond1", "start");
        Map<String, List<String>> currentWeatherCondition = new HashMap<>();

        List<String> titleLine = new ArrayList<>();

        titleLine.add(myApplication.getxColumn());

        if (!weatherData.isEmpty()) {


            titleLine.addAll(Arrays.asList(myApplication.getCOLUMNS()));

            Set<String> keys = weatherData.keySet();

            Map<String, ArrayList<Float>> currentStatus = weatherData.get("" + keys.toArray()[0]);

            List<String> values = new ArrayList<>();

            assert currentStatus != null;
            values.add((String) currentStatus.keySet().toArray()[0]);

            for (Float i : currentStatus.get(currentStatus.keySet().toArray()[0])) {

                values.add("" + i);

            }

            currentWeatherCondition.put("titles", titleLine);
            currentWeatherCondition.put("values", values);
        }
        Log.i("getCurrentWeatherCond2", "end");

        return currentWeatherCondition;
    }

    //TODO: 3. Separate the Data Retrivel from API and Chart creation.
    //TODO: 5. Important UI UPDATE Time and date show on x-axis(UtilsWeatherDataRead, ChartDataAdapter)
    public Map<String, ArrayList<Float>> getChartItems(Map<String, ArrayList<Float>> weatherData) {

        Log.i("getChartItems 1", "start");
        Map<String, ArrayList<Float>> yLabelValues = new HashMap<>();

        List<String> titleLine;

        if (!weatherData.isEmpty()) {

            titleLine = Arrays.asList(myApplication.getCOLUMNS());

            xValues = new ArrayList<>();

            for (String s : weatherData.keySet()) {

                xValues.add(Long.parseLong(s));

            }

            Collections.sort(xValues);

            for (String column : myApplication.getCOLUMNS()) {

                int index = titleLine.indexOf(column);

                ArrayList<Float> colValues = new ArrayList<>();

                for (Long x : xValues) {

                    colValues.add(Objects.requireNonNull(weatherData.get("" + x)).get(index));

                }

                yLabelValues.put(column, colValues);
            }

            System.out.println("getChartItems" + xValues);
        }

        return yLabelValues;
    }

    public Map<String, Map<String, ArrayList<Float>>> readWeatherData(String filename) {
        Map<String, Map<String, ArrayList<Float>>> weatherData = new HashMap<>();
        FileInputStream is = null;
        BufferedReader reader = null;
        try {
            File file = new File(context.getExternalFilesDir(null), filename);
            int i = 0;

            String[] titleLine = null;

            if (file.exists()) {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line;

                Map<String, ArrayList<Float>> dayWiseData = null;
                String tmpDate = null;

                while ((line = reader.readLine()) != null) {
                    // read, split data, and add to weather data
                    if (i == 0) {
                        dayWiseData = new HashMap<>();
                        titleLine = line.split(",");
                        i++;
                    } else {
                        String[] strings = line.split(",");
                        ArrayList<Float> utilModel = new ArrayList<>();

                        for (int j = 0; j < myApplication.getCOLUMNS().length; j++) {
                            for (int k = 0; k < titleLine.length; k++) {
                                if (myApplication.getCOLUMNS()[j].equals(titleLine[k])) {
//                                    System.out.println(myApp.getCOLUMNS()[j]);
                                    // getting maximum value for each column

                                    if (Float.parseFloat(strings[k]) >= myApplication.getCOLUMNS_MAXVALUE()[j]) {
                                        myApplication.getCOLUMNS_MAXVALUE()[j] = Float.parseFloat(strings[k]);
                                    }

                                    utilModel.add(j, Float.parseFloat(strings[k]));
                                    break;
                                }
                            }
                        }

                        for (int j = 0; j < titleLine.length; j++) {
                            if (titleLine[j].equals(myApplication.getxColumn())) {

                                tmpDate = myApplication.getSimpleDateFormat().format(new Timestamp(Long.parseLong(strings[j]) * 1000));

                                if (weatherData.containsKey(tmpDate)) {
                                    dayWiseData = weatherData.get(tmpDate);
                                    assert dayWiseData != null;
                                    dayWiseData.put(strings[j], utilModel);
                                    weatherData.put(tmpDate, dayWiseData);
                                } else {
                                    dayWiseData = new HashMap<>();
                                    dayWiseData.put(strings[j], utilModel);
                                    weatherData.put(tmpDate, dayWiseData);
                                }
                                break;
                            }
                        }
                    }

                }
            }
            Log.i("readWeatherData 3", "DOne");
        } catch (FileNotFoundException e) {
            Log.d("readWeatherData 1", "Error reading data file" + e);
        } catch (IOException e) {
            Log.d("readWeatherData 2", "Error reading data file" + e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                Log.d("readWeatherData 3", e.toString());
            }
        }
        return weatherData;
    }

    public float getThreshold(String label) {

        switch (label) {
            case "windSpeed":
                return Preferences.getPreferences().getWindThresold();
            case "windGust":
                return Preferences.getPreferences().getWindGustThresold();
            case "precipIntensity":
                return Preferences.getPreferences().getPrecipitationThresold();
            case "precipProbability":
                return Preferences.getPreferences().getPrecipitationThresold() / 100;
            case "temperature":
                return Preferences.getPreferences().getPrecipitationThresold();
            case "cloudCover":
                return Preferences.getPreferences().getPrecipitationThresold() / 100;
            case "visibility":
                return Preferences.getPreferences().getPrecipitationThresold();
            default:
                return 2;
        }
    }

    //TODO: 1. Remove the Line chart from the combined chart
    //TODO: 2. Add colors to the bars based on some conditions.
    public ArrayList<BarData> generateBarData(Map<String, Object> values) {
        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);

        ArrayList<BarData> bardata_arraylist = new ArrayList<>();


        if (values != null && !values.isEmpty()) {
            for (String label : myApplication.getCOLUMNS()) {
                ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();
                ArrayList<BarEntry> yValues = ((Map<String, ArrayList<BarEntry>>) Objects.requireNonNull(values.get("bar"))).get(label);
                assert yValues != null;
                for (BarEntry data : yValues) {

                    float threshold = getThreshold(label);
                    Log.i("GenerateBarData", "label : " + label + String.valueOf(threshold));
                    if (data.getY() > threshold) {
                        colors.add(green);
                    } else {
                        colors.add(red);
                    }
                }
//                Log.d("generateBarDaata", label);
                // Here each dataset would be processed, temp, sunshine etc.
                BarDataSet barDataSet = new BarDataSet(yValues, label);
                barDataSet.setColors(colors);
                barDataSet.setDrawValues(false);
                barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                barDataSets.add(barDataSet);
                BarData data = new BarData(barDataSets);
                bardata_arraylist.add(data);
            }
        }
        return bardata_arraylist;
    }

    public Map<String, Object> getMappedArrayListOfEntries(Map<String, ArrayList<Float>> yLabelValues) {
        Map<String, Object> mappedEntries = new HashMap<>();
        if (yLabelValues != null && !yLabelValues.isEmpty()) {
            Map<String, ArrayList<BarEntry>> mappedBarEntries = new HashMap<>();

            for (Map.Entry<String, ArrayList<Float>> entry : yLabelValues.entrySet()) {
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                int i = 0;
                for (Float val : entry.getValue()) {
                    barEntries.add(new BarEntry(i, val));
                    i++;
                }
                mappedBarEntries.put(entry.getKey(), barEntries);
            }
            mappedEntries.put("bar", mappedBarEntries);
        }
        return mappedEntries;
    }

    public ArrayList<Long> getxValues() {
        return xValues;
    }

    public void setxValues(ArrayList<Long> xValues) {
        this.xValues = xValues;
    }
}
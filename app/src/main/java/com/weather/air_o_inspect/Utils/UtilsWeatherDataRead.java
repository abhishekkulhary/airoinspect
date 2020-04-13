package com.weather.air_o_inspect.Utils;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.MyApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilsWeatherDataRead {

    private ArrayList<Long> xValues = new ArrayList<>();

    public BarData generateFlyStatusBarData(ArrayList<BarEntry> flyStatusBarEntry,
            Map<String, ArrayList<BarEntry>> barEntries, Preferences preferences) {

        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);
        BarData barData = null;
        if (flyStatusBarEntry != null && !flyStatusBarEntry.isEmpty()) {
            ArrayList<Integer> colors = new ArrayList<>();
            ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
            ArrayList<Float> maxValue = new ArrayList<>();
            for (String label : MyApp.getCOLUMNS()) {
                if (!label.equals("time"))
                    maxValue.add(getThreshold(label, preferences));
            }
            for (int j = 0; j < flyStatusBarEntry.size(); j++) {
                boolean temp = true;
                int i = 0;
                for (String label : MyApp.getCOLUMNS()) {
                    if (!label.equals("time")) {
                        ArrayList<BarEntry> yValues = barEntries.get(label);
                        assert yValues != null;
                        if (yValues.get(j).getY() > maxValue.get(i)) {
                            temp = false;
                            break;
                        }
                        i++;
                    }
                }
                if (temp) {
                    colors.add(green);
                } else {
                    colors.add(red);
                }
            }
            // Here each dataset would be processed, temp, sunshine etc.
            BarDataSet barDataSet = new BarDataSet(flyStatusBarEntry, "Flying Status");
            barDataSet.setColors(colors);

            barDataSet.setDrawValues(false);
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            barDataSets.add(barDataSet);

            barData = new BarData(barDataSets);
        }
        return barData;
    }



    //TODO: 1. Remove the Line chart from the combined chart
    //TODO: 2. Add colors to the bars based on some conditions.
    public ArrayList<BarData> generateBarData(Map<String, ArrayList<BarEntry>> values, Preferences preferences) {
        ArrayList<BarData> bardata_arraylist = new ArrayList<>();
        if (values != null && !values.isEmpty()) {
            for (String label : MyApp.getCOLUMNS()) {
                if (!label.equals("time")) {
                    ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                    ArrayList<BarEntry> yValues = values.get(label);
                    Float maxValue = getThreshold(label, preferences);
                    assert yValues != null && maxValue != null;
                    ArrayList<Integer> colors = getColorsForBars(yValues, maxValue);
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
        }
        return bardata_arraylist;
    }

    private Float getThreshold(String label, Preferences preferences) {

        switch (label) {
            case "precipIntensity":
                if(preferences.getPrecipitationIntensitySwitch()) {
                    return preferences.getPrecipitationIntensityThresold();
                } else {
                    return 10000f;
                }
            case "precipProbability":
                if(preferences.getPrecipitationProbabilitySwitch()) {
                    return preferences.getPrecipitationProbabilityThresold();
                } else {
                    return 10000f;
                }
            case "temperature":
                if(preferences.getTemperatureSwitch()) {
                    return preferences.getTemperatureThresold();
                } else {
                    return 10000f;
                }
            case "pressure":
                if(preferences.getPressureSwitch()) {
                    return preferences.getPressureThresold();
                } else {
                    return 10000f;
                }
            case "windSpeed":
                if(preferences.getWindSpeedSwitch()) {
                    return preferences.getWindSpeedThresold();
                } else {
                    return 10000f;
                }
            case "windGust":
                if(preferences.getWindGustSwitch()) {
                    return preferences.getWindGustThresold();
                } else {
                    return 10000f;
                }
            case "cloudCover":
                if(preferences.getCloudCoverSwitch()) {
                    return preferences.getCloudCoverThresold();
                } else {
                    return 10000f;
                }
            case "visibility":
                if(preferences.getVisibilitySwitch()) {
                    return preferences.getVisibilityThresold();
                } else {
                    return 10000f;
                }
        }

        return null;
    }

    private ArrayList<Integer> getColorsForBars(ArrayList<BarEntry> yValues, Float maxValue) {
        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);
        ArrayList<Integer> colors = new ArrayList<>();
        for (BarEntry data : yValues) {
            if (data.getY() < maxValue) {
                colors.add(green);
            } else {
                colors.add(red);
            }
        }
        return colors;
    }

    public Map<String, ArrayList<BarEntry>> getMappedArrayListOfEntries(Map<String, ArrayList<Float>> yLabelValues) {
        Map<String, ArrayList<BarEntry>> mappedBarEntries = null;
        if (yLabelValues != null && !yLabelValues.isEmpty()) {
            mappedBarEntries = new HashMap<>();
            for (Map.Entry<String, ArrayList<Float>> entry : yLabelValues.entrySet()) {
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                int i = 0;
                for (Float val : entry.getValue()) {
                    barEntries.add(new BarEntry(i, val));
                    i++;
                }
                mappedBarEntries.put(entry.getKey(), barEntries);
            }
        }
        return mappedBarEntries;
    }

    public ArrayList<BarEntry> getFlyStatusBarEntries(Integer size) {

        ArrayList<Float> flyingstatus = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            flyingstatus.add(1f);

        }

        ArrayList<BarEntry> mappedBarEntries = new ArrayList<>();

        int i = 0;
        for (Float entry : flyingstatus) {
            mappedBarEntries.add(new BarEntry(i, entry));
            i++;
        }

        return mappedBarEntries;

    }



    public Map<String, ArrayList<Float>> getChartItems(List<WeatherUpdate> weatherUpdates, String date) {
        Log.i("getChartItems 1", "start");
        Map<String, ArrayList<Float>> yLabelValues = new HashMap<>();
        if (!weatherUpdates.isEmpty()) {
            xValues = new ArrayList<>();
            for (WeatherUpdate weatherUpdate : weatherUpdates) {

                int i = 0;
                if (weatherUpdate.getCurrentTime().equals(date)) {
                    for (String column : MyApp.getCOLUMNS()) {
                        ArrayList<Float> colValues = yLabelValues.get(column);
                        if (colValues == null && !column.equals("time")) {
                            colValues = new ArrayList<>();
                        }
                        switch (column) {
                            case "time":
                                xValues.add(weatherUpdate.getTime());
                                break;
                            case "precipIntensity":
                                colValues.add(weatherUpdate.getPrecipIntensity());
                                if (weatherUpdate.getPrecipIntensity() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getPrecipIntensity();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "precipProbability":
                                colValues.add(weatherUpdate.getPrecipProbability());
                                if (weatherUpdate.getPrecipProbability() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getPrecipProbability();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "temperature":
                                colValues.add(weatherUpdate.getTemperature());
                                if (weatherUpdate.getTemperature() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getTemperature();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "pressure":
                                colValues.add(weatherUpdate.getPressure());
                                if (weatherUpdate.getPressure() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getPressure();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "windSpeed":
                                colValues.add(weatherUpdate.getWindSpeed());
                                if (weatherUpdate.getWindSpeed() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getWindSpeed();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "windGust":
                                colValues.add(weatherUpdate.getWindGust());
                                if (weatherUpdate.getWindGust() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getWindGust();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "cloudCover":
                                colValues.add(weatherUpdate.getCloudCover());
                                if (weatherUpdate.getCloudCover() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getCloudCover();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                            case "visibility":
                                colValues.add(weatherUpdate.getVisibility());
                                if (weatherUpdate.getVisibility() >= MyApp.getColumnsMaxvalue()[i]) {
                                    MyApp.getColumnsMaxvalue()[i] = weatherUpdate.getVisibility();
                                }
                                i++;
                                yLabelValues.put(column, colValues);
                                break;
                        }
                    }
                }
            }
        }
        return yLabelValues;
    }

    public ArrayList<Long> getxValues() {
        return xValues;
    }
}
package com.weather.air_o_inspect.datareadutil;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.weather.air_o_inspect.MyApp;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UtilsWeatherDataRead {

    private ArrayList<String> xLabelValues = new ArrayList<>();
    private Context context;
    private StringBuffer currentDate = new StringBuffer(" ");
    private MyApp myApp;

    public UtilsWeatherDataRead(Context context) {
        super();
        this.context = context;
        this.myApp = new MyApp();
    }

    public Map<String, List<String>> getCurrentWeatherConditions(ArrayList<String[]> weatherData) {
        Map<String, List<String>> currentWeatherCondition = new HashMap<>();

        List<String> titleLine;
        if (!weatherData.isEmpty()) {
            titleLine = Arrays.asList(weatherData.remove(0));
            List<String> currentStatus = Arrays.asList(weatherData.remove(0));


            int indexTime = titleLine.indexOf(myApp.getxColumn());
            currentWeatherCondition.put("titles", titleLine);
            currentWeatherCondition.put("values", currentStatus);
            Log.i("current sttus", "" + currentWeatherCondition.get("titles"));
        }

        return currentWeatherCondition;
    }

    //TODO: 3. Separate the Data Retrivel from API and Chart creation.
    //TODO: 5. Important UI UPDATE Time and date show on x-axis(UtilsWeatherDataRead, ChartDataAdapter)
    public Map<String, ArrayList<Float>> getChartItems(ArrayList<String[]> weatherData) {
        Map<String, ArrayList<Float>> yLabelValues = new HashMap<>();
        List<String> titleLine;

        if (!weatherData.isEmpty()) {
            titleLine = Arrays.asList(weatherData.remove(0));

            int indexTime = titleLine.indexOf(myApp.getxColumn());
            boolean flag = true;
            for (String[] columns : myApp.getCOLUMNS()) {
                for (String column : columns) {
                    int index = titleLine.indexOf(column);
                    ArrayList<Float> colValues = new ArrayList<>();
                    for (int j = 0; j < weatherData.size(); j++) {
                        Log.i("indexTime", indexTime + "");
                        String tmpTime = myApp.getSimpleTimeFormat().format(new Timestamp(Long.parseLong(weatherData.get(j)[indexTime]) * 1000));
                        if (flag) {
                            String tmpDate = myApp.getSimpleDateFormat().format(new Timestamp(Long.parseLong(weatherData.get(j)[indexTime]) * 1000));
                            if (!currentDate.toString().contains(tmpDate)) {
                                if (j == 0) {
                                    currentDate = new StringBuffer(tmpDate);
                                } else {
                                    currentDate.append(", ").append(tmpDate);
                                }
                            }
                        }
                        if (tmpTime.matches("[0-2][0-9]:00:00:000")) {
                            if (flag) {
                                xLabelValues.add(myApp.getSimpleTimesFormat().format(new Timestamp(Long.parseLong(weatherData.get(j)[indexTime]) * 1000)));
                            }
                            colValues.add(Float.parseFloat(weatherData.get(j)[index]));
                        }
                    }
                    yLabelValues.put(column, colValues);
                    flag = false;
                }
            }
        }

        return yLabelValues;
    }

    public ArrayList<String[]> readWeatherData(String filename) {
        ArrayList<String[]> weatherData = new ArrayList<>();
        FileInputStream is = null;
        BufferedReader reader = null;
        try {
            File file = new File(context.getExternalFilesDir(null), filename);
            if (file.exists()) {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    // read, split data, and add to weather data
                    String[] strings = line.split(",");
                    weatherData.add(strings);
                }
            }
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

    //TODO: 1. Remove the Line chart from the combined chart
    //TODO: 2. Add colors to the bars based on some conditions.
    public ArrayList<BarData> generateBarData(Map<String, Object> values) {
        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);

        ArrayList<BarData> bardata_arraylist = new ArrayList<>();


        if (values != null && !values.isEmpty()) {
            for (String[] label_array : myApp.getCOLUMNS()) {
                ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                for (String s : label_array) {
                    List<Integer> colors = new ArrayList<>();
                    ArrayList<BarEntry> yValues = ((Map<String, ArrayList<BarEntry>>) Objects.requireNonNull(values.get("bar"))).get(s);
                    assert yValues != null;
                    for (BarEntry data : yValues) {
                        if (data.getY() > 2) {
                            colors.add(green);
                        } else {
                            colors.add(red);
                        }
                    }
                    Log.d("generateBarDaata", s);
                    // Here each dataset would be processed, temp, sunshine etc.
                    BarDataSet barDataSet = new BarDataSet(yValues, s);
                    barDataSet.setColors(colors);
                    // set.setValueTextColors(colors);
                    barDataSet.setDrawValues(false);
                    barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                    barDataSets.add(barDataSet);
                }
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
}
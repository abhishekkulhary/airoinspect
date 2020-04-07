package com.weather.air_o_inspect.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.weather.air_o_inspect.chartadapter.ChartDataAdapter;
import com.weather.air_o_inspect.viewholders.ChartsData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"CharsetObjectCanBeUsed", "unchecked"})
public class AsyncWeatherDataRetrieval extends AsyncTask<String, String, String> {

    private final String[][] COLUMNS = {{"precipIntensity"}, {"precipProbability"}, {"temperature"}, {"pressure"},
            {"windSpeed"}, {"windGust"}, {"cloudCover"}, {"visibility"}};
    private final String[] LABELS = {"Precipitation Intensity", "Precipitation Probability", "Temperature", "Pressure", "Wind Speed", "Wind Gust", "Cloud Cover", "Visibility"};
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.US);
    private final SimpleDateFormat simpleTimesFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    private ArrayList<String> xLabelValues = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String filename;
    private StringBuffer currentDate = new StringBuffer(" ");
    @SuppressLint("StaticFieldLeak")

    private RecyclerView lv;
    private ArrayList<BarData> mappedBarData;


    public AsyncWeatherDataRetrieval(Context context, String filename, RecyclerView lv) {
        super();
        this.context = context;
        this.filename = filename;
        this.lv = lv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        postAsyncTask();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {

        Map<String, ArrayList<Float>> yLabelValues = getChartItems();
        // mappedCombinedData = getBarEntrieswithColor(yLabelValues);
        Map<String, Object> mappedArrayEntries = getMappedArrayListOfEntries(yLabelValues);
        mappedBarData = generateBarData(mappedArrayEntries);

        return "Success";
    }

    private void postAsyncTask() {
        if (mappedBarData != null) {
            Log.i("postAsync","MappedBar Data not null");
            List<ChartsData> chartsDataList = new ArrayList<ChartsData>();
            for (int i = 0; i < mappedBarData.size(); i++) {

                chartsDataList.add(new ChartsData(mappedBarData.get(i), LABELS[i], "UNIT"));
            }
            Log.i("postAsync","Size of chartsdatalist " + chartsDataList.size());
            ChartDataAdapter cda = new ChartDataAdapter(context, chartsDataList);

            lv.setAdapter(cda);
        }
    }

    //TODO: 3. Separate the Data Retrivel from API and Chart creation.
    private Map<String, ArrayList<Float>> getChartItems() {
        ArrayList<String[]> weatherData = readWeatherData();
        Map<String, ArrayList<Float>> yLabelValues = new HashMap<>();
        List<String> titleLine;
        if (weatherData != null && !weatherData.isEmpty()) {
            titleLine = Arrays.asList(weatherData.remove(0));

            String xColumn = "time";
            int indexTime = titleLine.indexOf(xColumn);
            boolean flag = true;
            for (String[] columns : COLUMNS) {
                for (String column : columns) {
                    int index = titleLine.indexOf(column);
                    ArrayList<Float> colValues = new ArrayList<>();
                    for (int j = 0; j < weatherData.size(); j++) {
                        String tmpTime = simpleTimeFormat.format(new Timestamp(Long.parseLong(weatherData.get(j)[indexTime]) * 1000));
                        if (flag) {
                            String tmpDate = simpleDateFormat.format(new Timestamp(Long.parseLong(weatherData.get(j)[indexTime]) * 1000));
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
                                xLabelValues.add(simpleTimesFormat.format(new Timestamp(Long.parseLong(weatherData.get(j)[indexTime]) * 1000)));
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

    private ArrayList<String[]> readWeatherData() {
        ArrayList<String[]> weatherData = new ArrayList<>();
        FileInputStream is = null;
        BufferedReader reader = null;
        try {
            is = new FileInputStream(new File(context.getExternalFilesDir(null), filename));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                // read, split data, and add to weather data
                weatherData.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            Log.d("readWeatherData 1", "Error reading data file");
        } catch (IOException e) {
            Log.d("readWeatherData 2", "Error reading data file");
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
    private ArrayList<BarData> generateBarData(Map<String, Object> values) {
        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);

        ArrayList<BarData> bardata_arraylist = new ArrayList<>();


        if (values != null && !values.isEmpty()) {
            for (String[] label_array : COLUMNS) {
                ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                for (String s : label_array) {
                    List<Integer> colors = new ArrayList<>();
                    ArrayList<BarEntry> yValues = ((Map<String, ArrayList<BarEntry>>) Objects.requireNonNull(values.get("bar"))).get(s);
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

    private Map<String, Object> getMappedArrayListOfEntries(Map<String, ArrayList<Float>> yLabelValues) {
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
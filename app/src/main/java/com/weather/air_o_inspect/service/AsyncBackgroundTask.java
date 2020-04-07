package com.weather.air_o_inspect.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncBackgroundTask extends AsyncTask<String, String, String> {

    private String longLat;
    private String query;
    private String[] filename;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    private Utils utils;
    private String data;

    public AsyncBackgroundTask(String longLat, String query, String[] filename, Context context) {
        this.longLat = longLat;
        this.query = query;
        this.filename = filename;
        this.context = context;
        utils = new Utils();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (data != null)
            utils.saveCSVFile(context, data, filename);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        data = utils.getDataFromUrlWriteToCSV(longLat, query);
        return null;
    }


}
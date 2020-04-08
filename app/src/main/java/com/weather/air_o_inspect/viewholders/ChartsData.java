package com.weather.air_o_inspect.viewholders;

import com.github.mikephil.charting.data.BarData;

public class ChartsData {
    private BarData data;
    private String chart_name;
    private String unit_value;

    public ChartsData(BarData data, String chart_name, String unit_value) {
        this.data = data;
        this.chart_name = chart_name;
        this.unit_value = unit_value;
    }

    public BarData getData() {
        return data;
    }

    public void setData(BarData data) {
        this.data = data;
    }

    public String getChart_name() {
        return chart_name;
    }

    public void setChart_name(String chart_name) {
        this.chart_name = chart_name;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }
}
